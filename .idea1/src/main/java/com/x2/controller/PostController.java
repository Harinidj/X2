package com.x2.controller;

import com.x2.model.Post;
import com.x2.model.User;
import com.x2.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Value("${app.upload.dir}")
    private String uploadDir;

    // Show all posts (feed-like page)
    @GetMapping("/feed")
    public String feed(Model model, HttpSession session) {
        User logged = (User) session.getAttribute("loggedUser");
        if (logged == null) return "redirect:/login";

        model.addAttribute("posts", postService.all()); // ✅ use your existing all() method
        model.addAttribute("username", logged.getUsername());

        return "feed"; // your Thymeleaf page name
    }

    // Show posts by logged-in user only
    @GetMapping("/myPosts")
    public String myPosts(Model model, HttpSession session) {
        User logged = (User) session.getAttribute("loggedUser");
        if (logged == null) return "redirect:/login";

        model.addAttribute("posts", postService.byUser(logged.getId())); // ✅ use byUser()
        model.addAttribute("username", logged.getUsername());

        return "myPosts"; // page for personal posts
    }

    // Handle new post submission
    @PostMapping("/add")
    public String addPost(@RequestParam("content") String content,
                          @RequestParam(value="image", required=false) MultipartFile image,
                          HttpSession session) throws IOException {

        User logged = (User) session.getAttribute("loggedUser");
        if (logged == null) return "redirect:/login";

        Post post = new Post();
        post.setContent(content);
        post.setUser(logged);

        // Handle image upload
        if (image != null && !image.isEmpty()) {
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String ext = FilenameUtils.getExtension(image.getOriginalFilename());
            String filename = UUID.randomUUID() + (ext.isEmpty() ? "" : "." + ext);
            File dest = new File(dir, filename);
            image.transferTo(dest);

            post.setImagePath("/uploads/" + filename);
        }

        postService.save(post); // ✅ use save() method

        return "redirect:/posts/feed";
    }
}
