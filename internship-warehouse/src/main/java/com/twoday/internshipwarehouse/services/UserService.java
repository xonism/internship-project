package com.twoday.internshipwarehouse.services;

import com.twoday.internshipwarehouse.models.User;
import com.twoday.internshipwarehouse.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
