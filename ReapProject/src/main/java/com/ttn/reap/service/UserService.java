package com.ttn.reap.service;

import com.ttn.reap.entity.User;
import com.ttn.reap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void save(User user)
    {
        userRepository.save(user);
        System.out.println(user);
        System.out.println("user registeration successful!!!");
    }


    public User checkemailandpassword(String email,String password)
    {
        return userRepository.findByEmailAndPassword(email,password);
    }
}
