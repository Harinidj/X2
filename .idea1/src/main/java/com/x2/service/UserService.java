package com.x2.service;

import com.x2.model.User;
import com.x2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepository userRepo;

    public User register(User u){ return userRepo.save(u); }
    public Optional<User> findByEmail(String e){ return userRepo.findByEmail(e); }
    public Optional<User> findByUsername(String u){ return userRepo.findByUsername(u); }
    public Optional<User> login(String username, String password){ return userRepo.findByUsernameAndPassword(username,password); }
}
