package com.x2.repository;

import com.x2.model.Comment;
import com.x2.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Retrieves all comments for a given post ID, ordered by creation time.
    List<Comment> findByPostIdOrderByCreatedAtAsc(Long postId);

    // Counts the total number of comments associated with a specific Post entity.
    long countByPost(Post post);
}