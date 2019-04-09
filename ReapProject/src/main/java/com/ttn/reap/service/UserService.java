package com.ttn.reap.service;

import com.ttn.reap.enums.Role;
import com.ttn.reap.entity.User;
import com.ttn.reap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BadgeBalanceService badgeBalanceService;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(User user) {
        user.setRole(Role.USER);
        userRepository.save(user);
        badgeBalanceService.setBadgeCount(user);
    }

    public User checkemailandpassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElse(null);
    }

    public User findUserByToken(String token) {
        return userRepository.findUserByToken(token).orElse(null);
    }

    public User findUserId(long id) {
        return userRepository.findById(id);
//        return userRepository.findById(id).orElse(null);
    }
}
