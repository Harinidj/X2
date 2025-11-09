package com.x2.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 700)
    private String text;

    private LocalDateTime timestamp = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    public Comment(){}

    // getters & setters
    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }
    public String getText(){ return text; }
    public void setText(String text){ this.text = text; }
    public LocalDateTime getTimestamp(){ return timestamp; }
    public void setTimestamp(LocalDateTime timestamp){ this.timestamp = timestamp; }
    public User getUser(){ return user; }
    public void setUser(User user){ this.user = user; }
    public Post getPost(){ return post; }
    public void setPost(Post post){ this.post = post; }
}
