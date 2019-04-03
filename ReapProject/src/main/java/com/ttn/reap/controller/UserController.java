package com.ttn.reap.controller;

import com.ttn.reap.entity.Attachment;
import com.ttn.reap.entity.User;
import com.ttn.reap.service.FileStorageService;
import com.ttn.reap.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(AttachmentController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    UserService userService;

    @GetMapping("signup")
    ModelAndView register()
    {
        ModelAndView modelAndView=new ModelAndView("signup");
        modelAndView.addObject("user",new User());
        return modelAndView;
    }

    @PostMapping("register")
    @ResponseBody
    User submit(@ModelAttribute("user") User user,@RequestParam("file") MultipartFile file)
    {
        String fileName = fileStorageService.storeFile(file);
        String newfileName =fileName;
        Attachment attach = new Attachment(newfileName,file.getContentType(),"resources/uploads",LocalDate.now());
        fileStorageService.insert(attach);
        user.setAttachment(attach);
        userService.save(user);
        System.out.println(user);
        return user;
    }


    @GetMapping("login")
    String login()
    {
        return "login";
    }
}

