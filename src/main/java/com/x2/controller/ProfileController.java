package com.x2.controller;

import com.x2.model.User;
import com.x2.model.Post;
import com.x2.service.UserService;
import com.x2.service.PostService;
import com.x2.service.InteractionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ProfileController {

    private final UserService userService;
    private final PostService postService;
    private final InteractionService interactionService;

    @Autowired
    public ProfileController(UserService userService, PostService postService, InteractionService interactionService) {
        this.userService = userService;
        this.postService = postService;
        this.interactionService = interactionService;
    }

    /**
     * Maps to GET /profile/{username}
     * Displays the profile page for the specified user.
     */
    @GetMapping("/profile/{username}")
    public String viewProfile(@PathVariable String username, Model model, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/login"; // Must be logged in to view profiles
        }

        // 1. Find the user whose profile is being viewed
        Optional<User> profileUserOpt = userService.findByUsername(username);

        if (profileUserOpt.isEmpty()) {
            // Handle case where the username does not exist
            model.addAttribute("error", "User not found.");
            // You can create an error page or redirect to feed
            return "redirect:/posts/feed";
        }

        User profileUser = profileUserOpt.get();
        model.addAttribute("profileUser", profileUser);
        model.addAttribute("loggedUser", loggedUser);

        // 2. Fetch posts by this user
        List<Post> posts = postService.findByUser(profileUser);
        model.addAttribute("posts", posts);

        // 3. Check follow status (if viewing another user's profile)
        if (!loggedUser.getId().equals(profileUser.getId())) {
            boolean isFollowing = userService.isFollowing(loggedUser, profileUser);
            model.addAttribute("isFollowing", isFollowing);
        }

        // 4. Populate interaction maps (Like/Comment counts for the profile's posts)
        Map<Long, Boolean> isLikedMap = new HashMap<>();
        Map<Long, Long> likeCountMap = new HashMap<>();
        Map<Long, Long> commentCountMap = new HashMap<>();

        for (Post post : posts) {
            isLikedMap.put(post.getId(), interactionService.isLiked(loggedUser, post));
            likeCountMap.put(post.getId(), interactionService.countLikes(post));
            commentCountMap.put(post.getId(), interactionService.countComments(post));
        }

        model.addAttribute("isLikedMap", isLikedMap);
        model.addAttribute("likeCountMap", likeCountMap);
        model.addAttribute("commentCountMap", commentCountMap);

        // 5. Fetch Follower/Following Counts (Assuming your UserService/FollowRepository supports these)
        // If not implemented yet, you will need to add methods like countFollowers/countFollowing
        // For now, these are placeholder values or methods you must implement:
        long followerCount = userService.countFollowers(profileUser); // Must be implemented in UserService
        long followingCount = userService.countFollowing(profileUser); // Must be implemented in UserService
        model.addAttribute("followerCount", followerCount);
        model.addAttribute("followingCount", followingCount);

        return "profile/profile";
    }
}