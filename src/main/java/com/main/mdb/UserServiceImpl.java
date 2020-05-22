package com.main.mdb;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public abstract class UserServiceImpl implements UserRepository {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User save(User registration){
        User User = new User();
        User.setEmail(registration.getEmail());
        User.setPassword(passwordEncoder.encode(registration.getPassword()));
        return userRepository.save(User);
    }
}