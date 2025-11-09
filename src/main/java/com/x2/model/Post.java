package com.x2.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Lob
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();

    // --- FIX: Add the missing constructor used by PostController ---
    /**
     * Custom constructor used for creating a new Post from the feed form.
     */
    public Post(User user, String content) {
        this.user = user;
        this.content = content;
        // createdAt is automatically set by the field default
    }
}