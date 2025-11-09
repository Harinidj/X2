package com.x2.controller;

import com.x2.model.User;
import com.x2.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class FollowController {

    private final UserService userService;

    @Autowired
    public FollowController(UserService userService) {
        this.userService = userService;
    }

    // API endpoint for following/unfollowing
    @PostMapping("/follow")
    public ResponseEntity<Map<String, Object>> toggleFollow(@RequestParam Long followingId, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        Map<String, Object> response = new HashMap<>();

        if (loggedUser == null) {
            response.put("error", "User not logged in.");
            // 401 Unauthorized
            return ResponseEntity.status(401).body(response);
        }

        Optional<User> followingUserOpt = userService.findById(followingId);

        if (followingUserOpt.isEmpty()) {
            response.put("error", "User to follow not found.");
            return ResponseEntity.notFound().build();
        }

        User followingUser = followingUserOpt.get();

        // Toggle the follow status via the service
        boolean isNowFollowing = userService.toggleFollow(loggedUser, followingUser);

        response.put("ok", true);
        response.put("following", isNowFollowing);
        response.put("followingId", followingId);

        return ResponseEntity.ok(response);
    }
}