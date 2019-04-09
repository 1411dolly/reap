package com.ttn.reap.controller;

import com.ttn.reap.entity.User;
import com.ttn.reap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class AmUserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/sample")
    public ModelAndView modal() {
        ModelAndView modelAndView = new ModelAndView("sample");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/manage")
    public ModelAndView manageUser(@ModelAttribute("user") User user, Model model, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("manageUser");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/badges")
    public ModelAndView badges(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("badges");
        Long userId = (Long) session.getAttribute("userId");
//        Optional<User> optionalUser = userRepository.findById(userId);
//        User user=optionalUser.get();

        User user = userRepository.findById(userId);
        if (user != null) {
            modelAndView.addObject("user", user);
        } else {
            //do something if user is null
            System.out.println("user is null in badge mapping");
        }
        return modelAndView;
    }
}
