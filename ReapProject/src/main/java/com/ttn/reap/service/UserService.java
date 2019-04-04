package com.ttn.reap.service;

import com.ttn.reap.entity.Role;
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
        user.setRole(Role.USER);
        userRepository.save(user);
        System.out.println(user);
        System.out.println("user registeration successful!!!");
    }

    public void saveNewUser(User user)
    {
        user.setRole(Role.USER);
        user.setAvailPoints(0);
        user.setPoints(0);
        user.setRedeemedPoints(0);
        user.setToken(null);

        userRepository.save(user);
        System.out.println(user);
        System.out.println("user registeration successful!!!");
    }


    public User checkemailandpassword(String email,String password)
    {
        return userRepository.findByEmailAndPassword(email,password);
    }
    
    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email).orElse(null);
    }
    public User findUserByToken(String token){
        return userRepository.findUserByToken(token).orElse(null);
    }
}
