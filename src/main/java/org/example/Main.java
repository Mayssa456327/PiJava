package org.example;

import entities.Blog;
import entities.Comment;

import java.time.LocalDateTime;

import services.BlogService;
import services.CommentService;
public class Main {
    public static void main(String[] args) {

        BlogService blogService = new BlogService();

//        Blog blogToAdd = new Blog(blogId);
//        blogToAdd.setType("article");
//        blogToAdd.setDate(LocalDateTime.now());
//        blogToAdd.setTitre("aaaa");
//        blogToAdd.setContent("aaaaaa");
//        blogToAdd.setImage("/home/fichier.jpg");
//
//        // Création du service BlogService


//        // Ajout du blog
        //   blogService.create(blogToAdd);
//


//        // Récupération de tous les blogs et affichage
//        System.out.println("\nListe des blogs après ajout : ");
//        for (Blog blog : blogService.getAll()) {
//            System.out.println(blog);
//        }
//


//        // update du blog

//
//    Blog firstBlog = blogService.getById(2);
//
//// Check if the blog exists
//    if (firstBlog != null) {
//        // Update the titre of the blog
//        firstBlog.setTitre("Nouveau titre");
//
//        // Update the blog in the database
//        blogService.update(firstBlog);
//    } else {
//        System.out.println("Aucun blog trouvé avec l'ID spécifié.");
//    }
//


//        // Affichage des blogs après mise à jour
//        System.out.println("\nListe des blogs après mise à jour : ");
//        for (Blog blog : blogService.getAll()) {
//            System.out.println(blog);
//        }
//


//        // Suppression du premier blog
//        int blogIdToDelete = 2; // Remplacez 1 par l'ID du blog à supprimer
//        blogService.delete(blogIdToDelete);

//        // Affichage des blogs après suppression
//        System.out.println("\nListe des blogs après suppression : ");
//        for (Blog blog : blogService.getAll()) {
//            System.out.println(blog);
//        }
//


/***********************************************************************comment test**************************************************************************************************/
        CommentService commentService = new CommentService();


        // Create a new Comment
//    Comment comment = new Comment();
//    comment.setContent("Content of the Comment");
//    LocalDateTime commentDate = LocalDateTime.now(); // Get current date
//    if (commentDate != null) {
//        comment.setDate(commentDate);
//    } else {
//        System.err.println("Error: Comment date is null.");
//        return;
//    }
//    comment.setLikes(5);
//
// Blog blog =blogService.getById(4) ;
//    comment.setBlog(blog);

//
//    // Add the comment to the database
        // commentService.create(comment);
//
//

//     System.out.println("\nListe des commentaires après ajout : ");
//        for (Comment comment : commentService.getAll()) {
//            System.out.println(comment);
//        }
//
//    mise ajour
//Comment commentToUpdate = commentService.getById(8);
//    commentToUpdate.setContent("Nouveau contenu du commentaire");
//        commentService.update(commentToUpdate);

//
        // delete
        // commentService.delete(13);

//


//search
//     int idToSearch = 1; // Replace with the desired ID
//
//        // Retrieve the blog by ID
//        Blog blog = blogService.getById(idToSearch);
//
//        // Check if the blog is found
//        if (blog != null) {
//            System.out.println("Blog found with ID " + idToSearch + ":");
//            System.out.println(blog);
//        } else {
//            System.out.println("No blog found with ID " + idToSearch);
//        }
//

/*************************************************************************************most commented  blogs**************/

//        int limit = 5;
//
//        List<Blog> topCommentedBlogs = blogService.getTopCommentedBlogs(limit);
//
//        // Print the details of the top commented blogs
//        if (topCommentedBlogs.isEmpty()) {
//            System.out.println("No top commented blogs found.");
//        } else {
//            System.out.println("Top Commented Blogs:");
//            for (Blog blog : topCommentedBlogs) {
//                System.out.println("ID: " + blog.getId());
//                System.out.println("Type: " + blog.getType());
//                System.out.println("Date: " + blog.getDate());
//                System.out.println("Titre: " + blog.getTitre());
//                System.out.println("Content: " + blog.getContent());
//                System.out.println("Image: " + blog.getImage());
//                System.out.println();
//            }
//        }
//    }
/*************************************************************************************top liked commentaire**************/
        Comment mostLikedComment = commentService.getMostLikedComment();

        if (mostLikedComment != null) {
            System.out.println("Most Liked Comment:");
            System.out.println("ID: " + mostLikedComment.getId());
            System.out.println("Content: " + mostLikedComment.getContent());
            System.out.println("Date: " + mostLikedComment.getDate());
            System.out.println("Likes: " + mostLikedComment.getLikes());

        } else {
            System.out.println("No most liked comment found.");
        }


    }
    }
