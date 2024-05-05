package entities;

import java.util.Collection;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Blog {
    private Integer id;
    private String type;
    private LocalDateTime date;
    private String titre;
    private String content;
    private String image;
    private Collection<Comment> comments;

    public Blog() {
        this.comments = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        if (!this.comments.contains(comment)) {
            this.comments.add(comment);
            comment.setBlog(this);
        }
    }

    public void removeComment(Comment comment) {
        if (this.comments.remove(comment)) {
            comment.setBlog(null);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Blog :")
                //.append("\n   ID: ").append(id)
                .append("\n   Type: ").append(type)
                .append("\n   Date: ").append(date)
                .append("\n   Title: ").append(titre)
                .append("\n   Content: ").append(content)
                //.append("\n   Image: ").append(image)
               // .append("\n   Comments: ").append(comments)
                .append("\n");
        return stringBuilder.toString();
    }

    public Blog(Integer id) {
        this.id = id;
    }
}