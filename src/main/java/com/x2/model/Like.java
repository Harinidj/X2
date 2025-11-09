package com.x2.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
// Enforce unique constraint: a user can only like a post once
@Table(name = "likes", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","post_id"}))
public class Like {
    // getters & setters
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    public Like(){}
    public Like(User user, Post post){ this.user = user; this.post = post; }

    public void setId(Long id){ this.id = id; }

    public void setUser(User user){ this.user = user; }

    public void setPost(Post post){ this.post = post; }
}