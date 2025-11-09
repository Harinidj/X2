package com.x2.service;

import com.x2.model.*;
import com.x2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class InteractionService {
    @Autowired private LikeRepository likeRepo;
    @Autowired private CommentRepository commentRepo;

    public boolean toggleLike(User user, Post post){
        Optional<Like> ex = likeRepo.findByUserIdAndPostId(user.getId(), post.getId());
        if(ex.isPresent()){
            likeRepo.delete(ex.get());
            return false;
        } else {
            likeRepo.save(new Like(user, post));
            return true;
        }
    }

    public long countLikes(Post post){ return likeRepo.countByPostId(post.getId()); }

    public Comment addComment(User user, Post post, String text){
        Comment c = new Comment();
        c.setUser(user);
        c.setPost(post);
        c.setText(text);
        return commentRepo.save(c);
    }
}
