package com.x2.service;

import com.x2.model.Post;
import com.x2.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired private PostRepository postRepo;

    public Post save(Post p){ return postRepo.save(p); }
    public List<Post> all(){ return postRepo.findAllByOrderByTimestampDesc(); }
    public List<Post> byUser(Long userId){ return postRepo.findByUserIdOrderByTimestampDesc(userId); }
    public Optional<Post> find(Long id){ return postRepo.findById(id); }
}
