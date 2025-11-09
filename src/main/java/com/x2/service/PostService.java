package com.x2.service;

import com.x2.model.Post;
import com.x2.model.User; // <--- NEW IMPORT: Required for findByUser
import com.x2.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    /**
     * Finds a Post by its ID.
     */
    public Optional<Post> findById(Long postId) {
        return postRepository.findById(postId);
    }

    /**
     * Retrieves all Posts, ordered by the creation time in descending order (for the feed).
     */
    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    /**
     * Retrieves all Posts created by a specific User, ordered by creation time descending.
     * **THIS FIXES THE ERROR in ProfileController.java**
     */
    public List<Post> findByUser(User user) {
        return postRepository.findByUserOrderByCreatedAtDesc(user);
    }

    /**
     * Saves a new or existing Post entity.
     */
    public Post save(Post post) {
        return postRepository.save(post);
    }
}