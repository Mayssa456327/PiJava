/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

/**
 *
 * @author hassa
 */
import entities.Blog;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import utils.Myconnection;

public class BlogService implements CrudInterface<Blog> {

    private Connection cnx;

    public BlogService() {
        cnx = Myconnection.getInstance().getCnx();
    }
    @Override
    public void create(Blog blog) {
        String query = "INSERT INTO Blog (type, date, titre, content, image) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, blog.getType());

            // Convert LocalDateTime to Timestamp
            Timestamp timestamp = Timestamp.valueOf(blog.getDate());
            pst.setTimestamp(2, timestamp);

            pst.setString(3, blog.getTitre());
            pst.setString(4, blog.getContent());
            pst.setString(5, blog.getImage());

            pst.executeUpdate();
            System.out.println("Blog ajouté avec succès");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'ajout du blog : " + ex.getMessage());
        }
    }

    @Override
    public void update(Blog blog) {
        if (blog != null) {
            String query = "UPDATE Blog SET type=?, date=?, titre=?, content=?, image=? WHERE id=?";
            try (PreparedStatement pst = cnx.prepareStatement(query)) {
                pst.setString(1, blog.getType());

                // Convert LocalDateTime to Timestamp
                Timestamp timestamp = Timestamp.valueOf(blog.getDate());
                pst.setTimestamp(2, timestamp);

                pst.setString(3, blog.getTitre());
                pst.setString(4, blog.getContent());
                pst.setString(5, blog.getImage());
                pst.setInt(6, blog.getId());

                int rowsUpdated = pst.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Blog mis à jour avec succès");
                } else {
                    System.out.println("Aucun blog trouvé avec l'ID : " + blog.getId());
                }
            } catch (SQLException ex) {
                System.err.println("Erreur lors de la mise à jour du blog : " + ex.getMessage());
            }
        } else {
            System.err.println("L'objet blog est null.");
        }
    }
    @Override
    public List<Blog> Rechreche(String recherche) {
        List<Blog> blogs = new ArrayList<>();
        String sql = "SELECT * FROM `blog` WHERE `titre` LIKE '%" + recherche + "%' OR `content`LIKE '%" + recherche + "%'";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Blog blog = new Blog();
                blog.setId(rs.getInt("id"));
                blog.setType(rs.getString("type"));
                blog.setDate(rs.getTimestamp("date").toLocalDateTime());
                blog.setTitre(rs.getString("titre"));
                blog.setContent(rs.getString("content"));
                blog.setImage(rs.getString("image"));
                blogs.add(blog);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return blogs;
    }

    public List<Blog> searchBlogs(String input) {
        List<Blog> searchResults = new ArrayList<>();
        String query = "SELECT * FROM Blog WHERE type LIKE ? OR date LIKE ? OR titre LIKE ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            String searchTerm = "%" + input + "%";
            pst.setString(1, searchTerm);
            pst.setString(2, searchTerm);
            pst.setString(3, searchTerm);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Blog blog = new Blog();
                    blog.setId(rs.getInt("id"));
                    blog.setType(rs.getString("type"));
                    blog.setDate(rs.getTimestamp("date").toLocalDateTime());
                    blog.setTitre(rs.getString("titre"));
                    blog.setContent(rs.getString("content"));
                    blog.setImage(rs.getString("image"));
                    searchResults.add(blog);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error searching blogs: " + ex.getMessage());
        }
        return searchResults;
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM Blog WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, id);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Blog supprimé avec succès");
            } else {
                System.out.println("Aucun blog trouvé avec l'ID : " + id);
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression du blog : " + ex.getMessage());
        }
    }



    @Override
    public Blog getById(int id) {
        String query = "SELECT * FROM Blog WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Blog blog = new Blog();
                    blog.setId(rs.getInt("id"));
                    blog.setType(rs.getString("type"));
                    Timestamp timestamp = rs.getTimestamp("date");
                    LocalDateTime dateTime = timestamp.toLocalDateTime();
                    blog.setDate(dateTime);
                    blog.setTitre(rs.getString("titre"));
                    blog.setContent(rs.getString("content"));
                    blog.setImage(rs.getString("image"));

                    return blog;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération du blog : " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Blog> getAll() {
        List<Blog> blogList = new ArrayList<>();
        String query = "SELECT * FROM Blog";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Blog blog = new Blog();
                    blog.setId(rs.getInt("id"));
                    blog.setType(rs.getString("type"));

                    // Convert Timestamp to LocalDateTime
                    Timestamp timestamp = rs.getTimestamp("date");
                    LocalDateTime dateTime = timestamp.toLocalDateTime();
                    blog.setDate(dateTime);

                    blog.setTitre(rs.getString("titre"));
                    blog.setContent(rs.getString("content"));
                    blog.setImage(rs.getString("image"));

                    blogList.add(blog);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des blogs : " + ex.getMessage());
        }
        return blogList;
    }
    public List<Blog> getTopCommentedBlogs() {
        List<Blog> topCommentedBlogs = new ArrayList<>();
        String query = "SELECT b.* " +
                "FROM Blog b " +
                "JOIN (SELECT blog_id, COUNT(*) AS comment_count " +
                "      FROM Comment " +
                "      GROUP BY blog_id " +
                "      ORDER BY comment_count DESC) AS top_comments " +
                "ON b.id = top_comments.blog_id";

        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Blog blog = new Blog();
                    blog.setId(rs.getInt("id"));
                    blog.setType(rs.getString("type"));
                    blog.setDate(rs.getTimestamp("date").toLocalDateTime());
                    blog.setTitre(rs.getString("titre"));
                    blog.setContent(rs.getString("content"));
                    blog.setImage(rs.getString("image"));
                    topCommentedBlogs.add(blog);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving top commented blogs: " + ex.getMessage());
        }

        return topCommentedBlogs;
    }

}