package com.x2.service;

import com.x2.model.Follow;
import com.x2.model.User;
import com.x2.repository.FollowRepository;
import com.x2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; // <-- FIX: This resolves the 'cannot find symbol: Service' error

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired private UserRepository userRepo;
    @Autowired private FollowRepository followRepo;

    // --- Authentication & Retrieval ---

    public Optional<User> findById(Long id){
        return userRepo.findById(id);
    }
    public Optional<User> findByUsername(String username){
        return userRepo.findByUsername(username);
    }
    public Optional<User> findByEmail(String email){
        return userRepo.findByEmail(email);
    }
    public User register(User user){
        return userRepo.save(user);
    }
    public Optional<User> login(String username, String password){
        return userRepo.findByUsernameAndPassword(username, password);
    }

    // --- Follow Functionality ---

    public boolean toggleFollow(User follower, User following) {
        Optional<Follow> existingFollow = followRepo.findByFollowerAndFollowing(follower, following);

        if (existingFollow.isPresent()) {
            // Unfollow
            followRepo.delete(existingFollow.get());
            return false;
        } else {
            // Follow
            followRepo.save(new Follow(follower, following));
            return true;
        }
    }

    public boolean isFollowing(User follower, User following) {
        return followRepo.findByFollowerAndFollowing(follower, following).isPresent();
    }

    // --- Suggestion Logic ---

    /**
     * Finds a list of users the loggedUser is NOT following (excluding themselves).
     */
    public List<User> getSuggestedUsers(User loggedUser) {
        // 1. Get all users the logged user is currently following
        List<User> following = followRepo.findByFollower(loggedUser).stream()
                .map(Follow::getFollowing)
                .collect(Collectors.toList());

        following.add(loggedUser); // Exclude the logged user themselves

        // 2. Find all users not in the 'following' list, limited to 5 suggestions
        return userRepo.findAll().stream()
                .filter(user -> !following.contains(user))
                .limit(5)
                .collect(Collectors.toList());
    }

    // --- Follower/Following Counts (Required by ProfileController) ---

    /**
     * Counts the number of users following the given user (Followers).
     */
    public long countFollowers(User user) {
        return followRepo.countByFollowing(user);
    }

    /**
     * Counts the number of users the given user is following.
     */
    public long countFollowing(User user) {
        return followRepo.countByFollower(user);
    }
}