package com.x2.controller;

import java.util.Optional;
import com.x2.model.*;
import com.x2.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class InteractionController {
    @Autowired private PostService postService;
    @Autowired private InteractionService interactionService;

    @PostMapping("/like")
    public Map<String,Object> like(@RequestParam Long postId, HttpSession session){
        Map<String,Object> r = new HashMap<>();
        User u = (User) session.getAttribute("loggedUser");
        if(u==null){ r.put("error","not_logged_in"); return r; }
        Optional<Post> postOpt = postService.find(postId);
        if(!postOpt.isPresent()){ r.put("error","no_post"); return r; }
        boolean nowLiked = interactionService.toggleLike(u, postOpt.get());
        long count = interactionService.countLikes(postOpt.get());
        r.put("liked", nowLiked);
        r.put("count", count);
        return r;
    }

    @PostMapping("/comment")
    public Map<String,Object> comment(@RequestParam Long postId, @RequestParam String text, HttpSession session){
        Map<String,Object> r = new HashMap<>();
        User u = (User) session.getAttribute("loggedUser");
        if(u==null){ r.put("error","not_logged_in"); return r; }
        Optional<Post> pOpt = postService.find(postId);
        if(!pOpt.isPresent()){ r.put("error","no_post"); return r; }
        Comment c = interactionService.addComment(u, pOpt.get(), text);
        r.put("ok", true);
        r.put("commentUser", u.getUsername());
        r.put("text", c.getText());
        r.put("time", c.getTimestamp().toString());
        return r;
    }
}
