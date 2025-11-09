package com.x2.controller;

import com.x2.model.Post;
import com.x2.model.User;
import com.x2.service.InteractionService;
import com.x2.service.PostService;
import com.x2.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final InteractionService interactionService;
    private final UserService userService;

    // Use constructor injection for all services
    @Autowired
    public PostController(PostService postService, InteractionService interactionService, UserService userService) {
        this.postService = postService;
        this.interactionService = interactionService;
        this.userService = userService;
    }

    /**
     * Maps to GET /posts/feed. Loads all required data for the timeline.
     * FIX: Populates the model with posts and interaction data via Maps.
     */
    @GetMapping("/feed")
    public String viewFeed(Model model, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/login"; // Redirect unauthenticated users
        }
        model.addAttribute("loggedUser", loggedUser);

        // Fetch Posts (Assuming postService.findAll() is implemented)
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);

        // Prepare the Maps (The Fix for the Thymeleaf Error)
        Map<Long, Boolean> isLikedMap = new HashMap<>();
        Map<Long, Long> likeCountMap = new HashMap<>();
        Map<Long, Long> commentCountMap = new HashMap<>();
        Map<Long, Boolean> isFollowingMap = new HashMap<>();

        for (Post post : posts) {
            // Populate Maps using the Post ID as the key
            isLikedMap.put(post.getId(), interactionService.isLiked(loggedUser, post));
            likeCountMap.put(post.getId(), interactionService.countLikes(post));
            commentCountMap.put(post.getId(), interactionService.countComments(post));

            // Populate Follow Map
            if (!loggedUser.getId().equals(post.getUser().getId())) {
                isFollowingMap.put(post.getUser().getId(), userService.isFollowing(loggedUser, post.getUser()));
            }
        }

        // Add Maps to the model
        model.addAttribute("isLikedMap", isLikedMap);
        model.addAttribute("likeCountMap", likeCountMap);
        model.addAttribute("commentCountMap", commentCountMap);
        model.addAttribute("isFollowingMap", isFollowingMap);

        // Optional: Suggested users for the sidebar
        model.addAttribute("suggestedUsers", userService.getSuggestedUsers(loggedUser));

        return "posts/feed";
    }

    /**
     * Endpoint to handle post creation.
     */
    @PostMapping("/create")
    public String createPost(@RequestParam String content, HttpSession session, Model model) {
        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        if (content == null || content.trim().isEmpty()) {
            model.addAttribute("error", "Post content cannot be empty.");
            return viewFeed(model, session);
        }

        // Assuming Post has a constructor or setters to facilitate this
        Post newPost = new Post(loggedUser, content);
        postService.save(newPost);

        return "redirect:/posts/feed";
    }
}