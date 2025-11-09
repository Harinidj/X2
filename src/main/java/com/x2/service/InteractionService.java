package com.x2.service;

import com.x2.model.Comment;
import com.x2.model.Like;
import com.x2.model.Post;
import com.x2.model.User;
import com.x2.repository.CommentRepository;
import com.x2.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class InteractionService {

    @Autowired
    private LikeRepository likeRepo;

    @Autowired
    private CommentRepository commentRepo;

    // --- Like functionality ---

    public boolean toggleLike(User user, Post post) {
        Optional<Like> existingLike = likeRepo.findByUserAndPost(user, post);

        if (existingLike.isPresent()) {
            // Unlike
            likeRepo.delete(existingLike.get());
            return false;
        } else {
            // Like
            likeRepo.save(new Like(user, post));
            return true;
        }
    }

    public boolean isLiked(User user, Post post) {
        return likeRepo.findByUserAndPost(user, post).isPresent();
    }

    public long countLikes(Post post) {
        return likeRepo.countByPost(post);
    }

    // --- Comment functionality ---

    /**
     * Counts the total number of comments associated with a specific Post.
     * This method is required by PostController to display comment counts on the feed.
     */
    public long countComments(Post post) {
        return commentRepo.countByPost(post);
    }

    /**
     * Creates a new Comment entity and persists it.
     */
    public Comment addComment(User user, Post post, String content) {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setContent(content);
        comment.setCreatedAt(new Date());

        return commentRepo.save(comment);
    }
}