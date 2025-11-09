package com.x2.repository;

import com.x2.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByTimestampDesc();
    List<Post> findByUserIdOrderByTimestampDesc(Long userId);
}
