package com.x2.controller;

import com.x2.model.Post;
import com.x2.model.User;
import com.x2.repository.PostRepository;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class FeedController {

    private final PostRepository postRepo;

    @Value("${app.upload.dir}")
    private String uploadDir;

    public FeedController(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    @GetMapping("/feed")
    public String feed(Model model, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) return "redirect:/login";

        List<Post> posts = postRepo.findAllByOrderByTimestampDesc();
        model.addAttribute("posts", posts);
        model.addAttribute("username", loggedUser.getUsername());

        return "feed";
    }

    @PostMapping("/feed/addPost") // ✅ unique path
    public String addPost(@RequestParam("content") String content,
                          @RequestParam(value = "image", required = false) MultipartFile image,
                          HttpSession session) throws IOException {

        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) return "redirect:/login";

        Post post = new Post();
        post.setContent(content);
        post.setUser(loggedUser);

        if (image != null && !image.isEmpty()) {
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String ext = FilenameUtils.getExtension(image.getOriginalFilename());
            String filename = UUID.randomUUID().toString() + (ext.isEmpty() ? "" : "." + ext);
            image.transferTo(new File(dir, filename));

            post.setImagePath("/uploads/" + filename);
        }

        postRepo.save(post);
        return "redirect:/feed";
    }
}
