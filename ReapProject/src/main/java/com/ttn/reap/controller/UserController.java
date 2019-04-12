package com.ttn.reap.controller;

import com.ttn.reap.co.RecognizeCO;
import com.ttn.reap.dto.UserDto;
import com.ttn.reap.entity.BadgeBalance;
import com.ttn.reap.entity.BadgeTransaction;
import com.ttn.reap.entity.User;
import com.ttn.reap.enums.Badge;
import com.ttn.reap.enums.Role;
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


    @GetMapping("/")
    String main(HttpSession session, Model model) {
        return sessionCheck(session, model);
    }

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
        System.out.println("registered!!!" + user);
        return "login";
    }

    @GetMapping("register")
    String checkRegister() {
        return "redirect:/";
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
        if (checkuser == null) {
            model.addAttribute("valid", "Enter valid username and password!!!");
            return "login";
        } else {
            session.setAttribute("userId", checkuser.getId());
            return dashboardData(checkuser, model);
        }
    }

    private String dashboardData(User checkuser, Model model) {
        BadgeBalance badge = badgeBalanceService.getBadgeById(checkuser.getId());
        int gold = badgeTransactionService.countByRecieverAndBadge(checkuser, Badge.GOLD);
        int silver = badgeTransactionService.countByRecieverAndBadge(checkuser, Badge.SILVER);
        int bronze = badgeTransactionService.countByRecieverAndBadge(checkuser, Badge.BRONZE);
        List<BadgeBalance> badgeBalanceList = badgeBalanceService.getbalancecount().subList(0, 3);
        List<BadgeTransaction> badgeTransactionList = badgeTransactionService.findAllByOrderByDateDesc().subList(0, 3);
        boolean role = checkuser.isAdmin();
        model.addAttribute("badgelist", badgeBalanceList);
        model.addAttribute("badgetransactionlist", badgeTransactionList);
        model.addAttribute("user", checkuser);
        model.addAttribute("badge", badge);
        model.addAttribute("role", role);
        model.addAttribute("gold", gold);
        model.addAttribute("silver", silver);
        model.addAttribute("bronze", bronze);
        model.addAttribute("recognizeco", new RecognizeCO());
        return "dashboard";
    }

    @GetMapping("user")
    String user(HttpSession session, Model model) {
        return sessionCheck(session, model);
    }

    String sessionCheck(HttpSession session, Model model) {
        if (session.getAttribute("userId") != null) {
            User user = userService.findUserId((long) session.getAttribute("userId"));
            return dashboardData(user, model);
        } else {
            return "redirect:/login";
        }
    }

    private boolean getRoleofUser(User checkuser) {
        if (checkuser.getRole().equals(Role.USER))
            return true;
        else
            return false;
    }

    @GetMapping("logout")
    private String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/login";
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
    public List<BadgeTransaction> getdata() {
        List<BadgeTransaction> badgeTransactionList = badgeTransactionService.findAllByOrderByDateDesc();
        return badgeTransactionList;
    }

    @PostMapping("/redeem")
    public ModelAndView redeem(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("redeem");
        long id = (long) session.getAttribute("userId");
        User user = userService.findUserId(id);
        BadgeBalance badge = badgeBalanceService.getBadgeById(id);
        modelAndView.addObject("user", user);
        modelAndView.addObject("badge", badge);
        boolean role = user.isAdmin();
        modelAndView.addObject("role", role);
        return modelAndView;
    }

    @GetMapping("/sample")
    public ModelAndView modal() {
        ModelAndView modelAndView = new ModelAndView("sample");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/manage")
    public ModelAndView manageUser(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("manageUser");
        long id = (Long) session.getAttribute("userId");
        User user = userService.findUserId(id);
        modelAndView.addObject("user", user);
        BadgeBalance badge = badgeBalanceService.getBadgeById(id);
        modelAndView.addObject("badge",badge);
        List<User> users = userService.findAll();
        modelAndView.addObject("users", users);
        List<Long> userIds = new ArrayList<>(users.size());
        for (User u : users) {
            userIds.add(user.getId());
        }
        //do something
        modelAndView.addObject("badgeBalanceService",badgeBalanceService);
        modelAndView.addObject("recognizeco",new RecognizeCO());
        boolean role = user.isAdmin();
        modelAndView.addObject("role", role);
        return modelAndView;
    }

    @GetMapping("getUserListActive")
    @ResponseBody
    public List<UserDto> getUserListActive(@RequestParam String term, @RequestParam String user_id) {
        return userService.simulateSearchResult(term, Long.parseLong(user_id));
    }

    @PostMapping("recognize")
    public String recognizeNewer(@ModelAttribute RecognizeCO recognizeCO) {
        int s = recognizeCO.getReceiver_email().indexOf("(");
        int e = recognizeCO.getReceiver_email().indexOf(")");
        recognizeCO.setReceiver_email(recognizeCO.getReceiver_email().substring(s + 1, e));
        User sender = userService.findUserByEmail(recognizeCO.getSender_email());
        User receiver = userService.findUserByEmail(recognizeCO.getReceiver_email());
        Badge badge;
        if (recognizeCO.getBadge_val().equalsIgnoreCase("gold")) {
            badge = Badge.GOLD;
        } else if (recognizeCO.getBadge_val().equalsIgnoreCase("silver")) {
            badge = Badge.SILVER;
        } else {
            badge = Badge.BRONZE;
        }
        badgeTransactionService.saveNewTranscation(sender, receiver, new Date(), recognizeCO.getMessage_val(), badge);
        return "redirect:/user";
    }

}