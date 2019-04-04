package com.ttn.reap.controller;

import com.ttn.reap.entity.Attachment;
import com.ttn.reap.entity.Role;
import com.ttn.reap.entity.User;
import com.ttn.reap.service.EmailService;
import com.ttn.reap.service.FileStorageService;
import com.ttn.reap.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Controller
public class UserController {
    
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    UserService userService;
    
    @Autowired
    private EmailService emailService;
    
    @Value("${spring.mail.username}")
    String fromMail;

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
        user.setRole(Collections.singletonList(Role.USER));
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

    @GetMapping("user")
    String user(HttpSession httpSession){

        System.out.println(httpSession.isNew());

        System.out.println(httpSession.getAttribute("userId")+"---------------------");
        if(!httpSession.getId().isEmpty())
        {
            int id= (int) httpSession.getAttribute("userId");
            System.out.println("id::"+id);
            return "ReapHomePage";
        }
        return "invalid";
    }


//check for ADMIN and redirect to admin dashboard.....now user dashboard......
    @PostMapping("user")
    String user(@ModelAttribute("user") User user, Model model, HttpSession session)
    {
        User checkuser=userService.checkemailandpassword(user.getEmail(),user.getPassword());
        if (checkuser==null)
        {  model.addAttribute("valid","Enter valid username and password!!!");
            return "login";
        }
        else {
            session.setAttribute("userId",checkuser.getId());
            model.addAttribute("user",checkuser);
            return "ReapHomePage";}
    }


    @GetMapping("logout")
    private String logout(HttpSession httpSession)
    {
        httpSession.invalidate();
        return "redirect:/signup";
    }

    @GetMapping("dashboard")
    String dashboard()
    {return "ReapHomePage";}

    @GetMapping("badges")
    String badges()
    {return  "Badges";
    }
    
    @GetMapping("forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }
    
    @GetMapping("forgotSubmit")
    public String forgotSubmit(@RequestParam String email, HttpServletRequest request) {
        User user = userService.findUserByEmail(email);
        user.setToken(UUID.randomUUID().toString());
        userService.save(user);
        
        String appUrl = request.getScheme() + "://" + request.getServerName();
        
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(fromMail);
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Forgot Password Link");
        mailMessage.setText("To reset your password, click the link below: \n" + appUrl + ":8080/reset?token=" + user.getToken());
        emailService.sendEmail(mailMessage);
        return "signup";
    }
    
    @GetMapping("/reset")
    public String resetPassByToken(@RequestParam("token") String token,Model model) {
        model.addAttribute("token",token);
        return "resetPassword";
    }
    
    @PostMapping("resetPassword")
    public String resetPassword(@RequestParam Map<String, String> requestParamas){
        System.out.println(requestParamas.get("token"));
        User user = userService.findUserByToken(requestParamas.get("token"));
        user.setPassword(requestParamas.get("password"));
        user.setToken(null);
        userService.save(user);
        return "signup";
    }
}

