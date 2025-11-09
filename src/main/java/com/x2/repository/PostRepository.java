package com.x2.repository;

import com.x2.model.Post;
import com.x2.model.User; // <--- NEW IMPORT: Required for the new method signature
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreatedAtDesc();

    /**
     * Retrieves all Posts created by a specific User, ordered by creation time descending.
     * **NEW DECLARATION**
     */
    List<Post> findByUserOrderByCreatedAtDesc(User user);
}