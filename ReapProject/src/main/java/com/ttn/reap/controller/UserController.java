package com.ttn.reap.controller;

import com.ttn.reap.entity.Attachment;
import com.ttn.reap.entity.User;
import com.ttn.reap.service.FileStorageService;
import com.ttn.reap.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    String submit(Model model,@ModelAttribute("user") User user,@RequestParam("file") MultipartFile file)
    {
        String fileName = fileStorageService.storeFile(file);
        String newfileName =fileName;
        Attachment attach = new Attachment(newfileName,file.getContentType(),"resources/uploads",LocalDate.now());
        fileStorageService.insert(attach);
        user.setAttachment(attach);
        try{userService.save(user);}
        catch (Exception e)
        {
            System.out.println("error occured");
            model.addAttribute("err","Only unique email allowed!!!");
            return "signup";
        }
        System.out.println(user);
        return "login";
    }

    @GetMapping("login")
    ModelAndView login()
    {
        ModelAndView modelAndView=new ModelAndView("login");
        modelAndView.addObject("user",new User());
        return modelAndView;
    }

    @PostMapping("user")
    String user(@ModelAttribute("user") User user,Model model)
    {
        User checkuser=userService.checkemailandpassword(user.getEmail(),user.getPassword());
        if (checkuser==null)
        {  model.addAttribute("valid","Enter valid username and password!!!");
            return "login";
        }
        else return "ReapHomePage";
    }
}

