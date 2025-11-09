package com.x2.repository;

import com.x2.model.Like;
import com.x2.model.Post;
import com.x2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    // Finds a specific Like record by User and Post objects. Used for the toggle logic.
    Optional<Like> findByUserAndPost(User user, Post post);

    // Counts the total number of Likes for a specific Post object.
    long countByPost(Post post);

    // Counts the total number of Likes for a specific Post ID.
    long countByPostId(Long postId);
}