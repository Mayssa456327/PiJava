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
import entities.Comment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import utils.Myconnection;

public class CommentService implements CrudInterface<Comment> {

    private Connection cnx;

    public CommentService() {
        cnx = Myconnection.getInstance().getCnx();
    }

    @Override

    public void create(Comment comment) {
        if (comment == null || comment.getBlog() == null || comment.getBlog().getId() == null) {
            System.err.println("Error: Invalid comment data or blog ID is null.");
            return;
        }

        String query = "INSERT INTO Comment (content, date, likes, blog_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, comment.getContent());
            pst.setTimestamp(2, Timestamp.valueOf(comment.getDate()));
            pst.setInt(3, comment.getLikes());
            pst.setInt(4, comment.getBlog().getId());

            pst.executeUpdate();
            System.out.println("Commentaire ajouté avec succès");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'ajout du commentaire : " + ex.getMessage());
        }
    }




    @Override
    public void update(Comment comment) {
        if (comment != null) {
            String query = "UPDATE Comment SET content=?, date=?, likes=? WHERE id=?";
            try (PreparedStatement pst = cnx.prepareStatement(query)) {
                pst.setString(1, comment.getContent());

                // Convert LocalDateTime to Timestamp
                Timestamp timestamp = Timestamp.valueOf(comment.getDate());
                pst.setTimestamp(2, timestamp);

                pst.setInt(3, comment.getLikes());
                pst.setInt(4, comment.getId());

                int rowsUpdated = pst.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Commentaire mis à jour avec succès");
                } else {
                    System.out.println("Aucun commentaire trouvé avec l'ID : " + comment.getId());
                }
            } catch (SQLException ex) {
                System.err.println("Erreur lors de la mise à jour du commentaire : " + ex.getMessage());
            }
        } else {
            System.err.println("L'objet commentaire est null.");
        }
    }

    @Override
    public List<Blog> Rechreche(String recherche) {
        return null;
    }


    @Override
    public void delete(int id) {
        String query = "DELETE FROM Comment WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, id);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Comment supprimé avec succès");
            } else {
                System.out.println("Aucun Comment trouvé avec l'ID : " + id);
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression du blog : " + ex.getMessage());
        }
    }

    @Override
    public Comment getById(int id) {
        String query = "SELECT * FROM Comment WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Comment comment = new Comment();
                    comment.setId(rs.getInt("id"));
                    comment.setContent(rs.getString("content"));
                    Timestamp timestamp = rs.getTimestamp("date");
                    LocalDateTime dateTime = timestamp.toLocalDateTime();
                    comment.setDate(dateTime);
                    comment.setLikes(rs.getInt("likes"));

                    return comment;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération du commentaire : " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> commentList = new ArrayList<>();
        String query = "SELECT * FROM Comment";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Comment comment = new Comment();
                    comment.setId(rs.getInt("id"));
                    comment.setContent(rs.getString("content"));
                    Timestamp timestamp = rs.getTimestamp("date");
                    LocalDateTime dateTime = timestamp.toLocalDateTime();
                    comment.setDate(dateTime);
                    comment.setLikes(rs.getInt("likes"));

                    commentList.add(comment);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des commentaires : " + ex.getMessage());
        }
        return commentList;
    }
    public List<Comment> getcomments(int blogId) {
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM Comment WHERE blog_id = ? ORDER BY date DESC";

        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, blogId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Comment comment = new Comment();
                    comment.setId(rs.getInt("id"));
                    comment.setContent(rs.getString("content"));
                    comment.setDate(rs.getTimestamp("date").toLocalDateTime());
                    comment.setLikes(rs.getInt("likes"));
                    // Assuming there's a blog_id column in the Comment table
                    comment.setId(rs.getInt("blog_id"));

                    comments.add(comment);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving comments for blog with ID " + blogId + ": " + ex.getMessage());
        }
        return comments;
    }
    public List<Comment> getAllCommentsOrderedByLikes() {
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM Comment ORDER BY likes DESC";

        try (PreparedStatement pst = cnx.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getInt("id"));
                comment.setContent(rs.getString("content"));
                comment.setDate(rs.getTimestamp("date").toLocalDateTime());
                comment.setLikes(rs.getInt("likes"));

                comments.add(comment);
            }

        } catch (SQLException ex) {
            System.err.println("Error retrieving comments ordered by likes: " + ex.getMessage());
        }

        return comments;
    }

    public Comment getMostLikedComment() {
        String query = "SELECT * FROM Comment ORDER BY likes DESC";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Comment comment = new Comment();
                    comment.setId(rs.getInt("id"));
                    comment.setContent(rs.getString("content"));
                    comment.setDate(rs.getTimestamp("date").toLocalDateTime());
                    comment.setLikes(rs.getInt("likes"));

                    return comment;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving the most liked comment: " + ex.getMessage());
        }
        return null;
    }

    public List<Comment> getCommentsByBlogId(int blogId) {
        List<Comment> commentList = new ArrayList<>();
        String query = "SELECT * FROM Comment WHERE blog_id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, blogId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Comment comment = new Comment();
                    comment.setId(rs.getInt("id"));
                    comment.setContent(rs.getString("content"));
                    Timestamp timestamp = rs.getTimestamp("date");
                    LocalDateTime dateTime = timestamp.toLocalDateTime();
                    comment.setDate(dateTime);
                    comment.setLikes(rs.getInt("likes"));


                    commentList.add(comment);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des commentaires pour l'ID du blog : " + ex.getMessage());
        }
        return commentList;
    }

}
