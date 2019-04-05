package com.ttn.reap.controller;

import com.ttn.reap.entity.*;
import com.ttn.reap.expections.UserNotFoundException;
import com.ttn.reap.service.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private BadgeBalanceService badgeBalanceService;
    @Autowired
    private BadgeTransactionService badgeTransactionService;
//    @Autowired

    @Value("${spring.mail.username}")
    String fromMail;


    @GetMapping("signup")
    ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("signup");
        modelAndView.addObject("user", new User());
//        modelAndView.addObject("badgebalance",new BadgeBalance());
        return modelAndView;
    }

    @PostMapping("register")
    String submit(Model model, @ModelAttribute("user") User user,@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        System.out.println(user.toString());
//        String newfileName = fileName;
        System.out.println(fileName);
        Attachment attach = new Attachment(fileName, file.getContentType(), "resources/static/upload", new Date());
        System.out.println(attach.toString());
        fileStorageService.insert(attach);
        user.setAttachment(attach);
        try {
            userService.save(user);
            badgeBalanceService.setBadgeCount(user);

        } catch (Exception e) {
//            eroor page lagao
            //check id doesnt exist or duplicate id .......this is kaam chalau code
            model.addAttribute("err", "Only unique email allowed!!!");
            throw new UserNotFoundException("user of this id doesnt exist !!!!!!!");
        }
        System.out.println(user);
        return "login";
    }

    @GetMapping("login")
    ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @GetMapping("user")
    String user(HttpSession httpSession) {
        if(!httpSession.getId().isEmpty())
        {
            int id= (int) httpSession.getAttribute("userId");
            System.out.println("id::"+id);
            return "dashboard";
        }
        return "invalid";
    }


    //check for ADMIN and redirect to admin dashboard.....now user dashboard......
    @PostMapping("user")
    String user(@ModelAttribute("user") User user, Model model, HttpSession session) {
        User checkuser = userService.checkemailandpassword(user.getEmail(), user.getPassword());
        if (checkuser == null) {
            model.addAttribute("valid", "Enter valid username and password!!!");
            return "login";
        }
        else {
            session.setAttribute("userId",checkuser.getId());
            System.out.println("session id::"+session.getAttribute("userId"));
            Attachment attachmentUser=fileStorageService.findAttachmentById(checkuser.getId());
            System.out.println(attachmentUser.toString());
            String userPic=attachmentUser.getFile_path().substring(17)+"/"+attachmentUser.getFileName();
            model.addAttribute("user",checkuser);
            model.addAttribute("userpic",userPic);
            return "dashboard";}
    }


    @GetMapping("logout")
    private String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/signup";
    }

    @GetMapping("dashboard")
    ModelAndView dashboard(HttpSession session)
    {
        System.out.println("session id::"+session.getAttribute("userId")+"role::"+session.getAttribute("role"));
        ModelAndView modelAndView=new ModelAndView("dashboard");
        return modelAndView;
    }

    @GetMapping("badges")
    String badges() {
        return "Badges";
    }

    @GetMapping("forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }

    @GetMapping("forgotSubmit")
    public String forgotSubmit(@RequestParam String email, HttpServletRequest request,Model model) {
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
        model.addAttribute(new User());
        return "signup";
    }

    @GetMapping("/reset")
    public String resetPassByToken(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        model.addAttribute("user",new User());
        return "resetPassword";
    }

    @PostMapping("resetPassword")
    public String resetPassword(@RequestParam Map<String, String> requestParamas,Model model) {
        System.out.println(requestParamas.get("token"));
        User user = userService.findUserByToken(requestParamas.get("token"));
        user.setPassword(requestParamas.get("password"));
        user.setToken(null);
        userService.save(user);
        model.addAttribute("user",new User());
        return "signup";
    }

}

