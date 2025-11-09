package com.x2.repository;

import com.x2.model.Follow;
import com.x2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    // Check if one user is already following another
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);

    // Get all Follow records where the logged user is the follower (for feed generation)
    List<Follow> findByFollower(User follower);

    // Get all Follow records where the user is the following (followers list)
    List<Follow> findByFollowing(User following);

    // Count the number of followers for a user
    long countByFollowing(User user);

    // Count the number of users a user is following
    long countByFollower(User user);
}