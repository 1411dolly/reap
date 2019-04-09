package com.ttn.reap.controller;

import com.ttn.reap.entity.BadgeBalance;
import com.ttn.reap.entity.BadgeTransaction;
import com.ttn.reap.entity.User;
import com.ttn.reap.enums.Role;
import com.ttn.reap.repository.UserRepository;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

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
        return modelAndView;
    }

    @PostMapping("register")
    String submit(Model model, @ModelAttribute("user") User user, @RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        user.setFileName("/upload/" + fileName);
        try {
            userService.save(user);
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
        System.out.print("user_dashboard::" + user.toString());
        User checkuser = userService.checkemailandpassword(user.getEmail(), user.getPassword());
        BadgeBalance badge = badgeBalanceService.getBadgeById(checkuser.getId());
        List<BadgeBalance> badgeBalanceList = badgeBalanceService.getbalancecount().subList(0, 3);
        List<BadgeTransaction> badgeTransactionList = badgeTransactionService.findAllByOrderByDateDesc().subList(0, 3);
        boolean role = getRoleofUser(checkuser);
        if (checkuser == null) {
            model.addAttribute("valid", "Enter valid username and password!!!");
            return "login";
        } else {
            session.setAttribute("userId", checkuser.getId());
            model.addAttribute("badgelist", badgeBalanceList);
            model.addAttribute("badgetransactionlist", badgeTransactionList);
            model.addAttribute("user", checkuser);
            model.addAttribute("badge", badge);
            model.addAttribute("role", role);
            return "dashboard";
        }
    }


    private boolean getRoleofUser(User checkuser) {
        if (checkuser.getRole().equals(Role.USER))
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
    public List<BadgeTransaction> getdata() {
        List<BadgeTransaction> badgeTransactionList = badgeTransactionService.findAllByOrderByDateDesc();
        return badgeTransactionList;
    }

    @PostMapping("/badges")
    public ModelAndView badges(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("badges");
        long id = (long) session.getAttribute("userId");
        User user = userService.findUserId(id);
        BadgeBalance badge = badgeBalanceService.getBadgeById(id);
        if (user != null)
            modelAndView.addObject("user", user);
        else {
            //do something if user is null
            System.out.println("user is null in badge mapping");
        }
        modelAndView.addObject("badge", badge);
        return modelAndView;
    }

    @PostMapping("/redeem")
    public ModelAndView redeem(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("redeem");
        long id = (long) session.getAttribute("userId");
        User user = userService.findUserId(id);
        BadgeBalance badge = badgeBalanceService.getBadgeById(id);
        modelAndView.addObject("user", user);
        modelAndView.addObject("badge", badge);
        return modelAndView;
    }

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

}