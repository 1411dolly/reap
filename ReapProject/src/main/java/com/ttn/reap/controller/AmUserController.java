package com.ttn.reap.controller;

import com.ttn.reap.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class AmUserController {
    @GetMapping("/sample")
    public ModelAndView modal() {
        ModelAndView modelAndView = new ModelAndView("sample");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    /*@GetMapping("/manage")
    public ModelAndView manageUser(){
        ModelAndView modelAndView = new ModelAndView("manageUser");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }*/

    @PostMapping("/manage")
    public ModelAndView manageUser(@ModelAttribute("user") User user, Model model, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("manageUser");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    //
//    @GetMapping("/badges2")
//    public ModelAndView badges2() {
//        ModelAndView modelAndView = new ModelAndView("badges");
//        modelAndView.addObject("user", new User());
//        return modelAndView;
//    }
    @PostMapping("/badges")
    public ModelAndView badges(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("badges");
        User user = (User) session.getAttribute("user");
        System.out.println("session:" + session.getAttribute("user"));
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
