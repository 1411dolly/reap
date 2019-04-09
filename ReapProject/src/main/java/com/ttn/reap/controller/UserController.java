package com.ttn.reap.controller;

import com.ttn.reap.entity.*;
import com.ttn.reap.service.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Value("${spring.mail.username}")
    String fromMail;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private BadgeBalanceService badgeBalanceService;
    @Autowired
    private BadgeTransactionService badgeTransactionService;

    @GetMapping("signup")
    ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("signup");
        modelAndView.addObject("user", new User());
//        modelAndView.addObject("badgebalance",new BadgeBalance());
        return modelAndView;
    }

    @PostMapping("register")
    String submit(Model model, @ModelAttribute("user") User user, @RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        System.out.println(user.toString());
//        String newfileName = fileName;
//        System.out.println("filename::"+fileName);
        Attachment attach = new Attachment( "/upload/"+fileName, file.getContentType(), new Date());
        System.out.println(attach.toString());
        fileStorageService.insert(attach);
        user.setAttachment(attach);
        try {
            System.out.println(user);
            userService.save(user);
            badgeBalanceService.setBadgeCount(user);

        } catch (Exception e) {
//            eroor page lagao
            //check id doesnt exist or duplicate id .......this is kaam chalau code
            model.addAttribute("err", "Only unique email allowed!!!");
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


    //check for ADMIN and redirect to admin dashboard.....now user dashboard......
    @PostMapping("user")
    String user(@ModelAttribute("user") User user, Model model, HttpSession session) {
        User checkuser = userService.checkemailandpassword(user.getEmail(), user.getPassword());
        BadgeBalance badge=badgeBalanceService.getBadgeById(checkuser.getId());
        List<BadgeBalance> badgeBalanceList=badgeBalanceService.getbalancecount().subList(0,3);
        List<BadgeTransaction> badgeTransactionList=badgeTransactionService.findAllByOrderByDateDesc().subList(0,3);
        boolean role =getRoleofUser(checkuser);
        if (checkuser == null) {
            model.addAttribute("valid", "Enter valid username and password!!!");
            return "login";
        } else {
            session.setAttribute("userId", checkuser.getId());
            System.out.println(session.getAttribute("userId"));
            model.addAttribute("badgelist",badgeBalanceList);
            model.addAttribute("badgetransactionlist",badgeTransactionList);
            model.addAttribute("user", checkuser);
            model.addAttribute("badge",badge);
            model.addAttribute("role",role);
            return "dashboard";
        }
    }


    private boolean getRoleofUser(User checkuser) {
        if(checkuser.getRole().equals(Role.USER))
            return true;
        else return false;
    }


    @GetMapping("logout")
    private String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/signup";
    }

    @GetMapping("forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }

    @GetMapping("forgotSubmit")
    public String forgotSubmit(@RequestParam String email, HttpServletRequest request, Model model) {
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
        model.addAttribute("user", new User());
        return "resetPassword";
    }

    @PostMapping("resetPassword")
    public String resetPassword(@RequestParam Map<String, String> requestParamas, Model model) {
        System.out.println(requestParamas.get("token"));
        User user = userService.findUserByToken(requestParamas.get("token"));
        user.setPassword(requestParamas.get("password"));
        user.setToken(null);
        userService.save(user);
        model.addAttribute("user", new User());
        return "signup";
    }

    @GetMapping("data")
    @ResponseBody
    public List<BadgeTransaction> getdata()
    {
        List<BadgeTransaction> badgeTransactionList=badgeTransactionService.findAllByOrderByDateDesc();
        return  badgeTransactionList;
    }

    @PostMapping("/badges")
    public ModelAndView badges(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("badges");
        long id= (long) session.getAttribute("userId");
        System.out.println("session::" + id);
        User user=userService.findUserId(id);
        BadgeBalance badge=badgeBalanceService.getBadgeById(id);
        modelAndView.addObject("user", user);
        modelAndView.addObject("badge", badge);
        return modelAndView;
    }

     @PostMapping("/redeem")
        public ModelAndView redeem(HttpSession session) {
            ModelAndView modelAndView = new ModelAndView("redeem");
            long id= (long) session.getAttribute("userId");
            System.out.println("session::" + id);
            User user=userService.findUserId(id);
            BadgeBalance badge=badgeBalanceService.getBadgeById(id);
            modelAndView.addObject("user", user);
            modelAndView.addObject("badge", badge);
            return modelAndView;
        }

}