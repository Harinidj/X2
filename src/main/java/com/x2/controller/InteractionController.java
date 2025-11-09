package com.x2.controller;

import com.x2.model.Post;
import com.x2.model.User;
import com.x2.service.InteractionService;
import com.x2.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/interactions")
public class InteractionController {

    private final PostService postService;
    private final InteractionService interactionService;

    @Autowired
    public InteractionController(PostService postService, InteractionService interactionService) {
        this.postService = postService;
        this.interactionService = interactionService;
    }

    /**
     * Endpoint to handle liking and unliking a post.
     * Maps to the JavaScript fetch call in feed.html.
     * URL: /api/interactions/like/{postId}
     */
    @PostMapping("/like/{postId}")
    public ResponseEntity<Map<String, Object>> toggleLike(@PathVariable Long postId, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        Map<String, Object> response = new HashMap<>();

        if (loggedUser == null) {
            response.put("error", "User not logged in.");
            return ResponseEntity.status(401).body(response);
        }

        Optional<Post> postOpt = postService.findById(postId);
        if (postOpt.isEmpty()) {
            response.put("error", "Post not found.");
            return ResponseEntity.notFound().build();
        }

        Post post = postOpt.get();
        // Toggle the like status
        boolean isNowLiked = interactionService.toggleLike(loggedUser, post);
        long likeCount = interactionService.countLikes(post);

        response.put("ok", true);
        response.put("liked", isNowLiked);
        response.put("likeCount", likeCount);

        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to handle submitting a comment on a post.
     * URL: /api/interactions/comment/{postId}
     */
    @PostMapping("/comment/{postId}")
    public ResponseEntity<Map<String, Object>> addComment(@PathVariable Long postId,
                                                          @RequestParam String content,
                                                          HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        Map<String, Object> response = new HashMap<>();

        if (loggedUser == null) {
            response.put("error", "User not logged in.");
            return ResponseEntity.status(401).body(response);
        }

        Optional<Post> postOpt = postService.findById(postId);
        if (postOpt.isEmpty()) {
            response.put("error", "Post not found.");
            return ResponseEntity.notFound().build();
        }

        if (content == null || content.trim().isEmpty()) {
            response.put("error", "Comment content cannot be empty.");
            return ResponseEntity.badRequest().body(response);
        }

        Post post = postOpt.get();
        interactionService.addComment(loggedUser, post, content);
        long commentCount = interactionService.countComments(post);

        response.put("ok", true);
        response.put("commentCount", commentCount);
        // In a real app, you would return the HTML fragment of the new comment

        // For now, we redirect the user back to the feed for simplicity after success
        // This is a simple fix for the non-working form, but better to use AJAX in production
        return ResponseEntity.ok(response);
    }
}