package com.ttn.reap.controller;

import com.ttn.reap.co.RecognizeCO;
import com.ttn.reap.entity.BadgeBalance;
import com.ttn.reap.entity.User;
import com.ttn.reap.enums.Badge;
import com.ttn.reap.enums.Role;
import com.ttn.reap.service.BadgeBalanceService;
import com.ttn.reap.service.BadgeTransactionService;
import com.ttn.reap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    private BadgeBalanceService badgeBalanceService;
    @Autowired
    private BadgeTransactionService badgeTransactionService;

    @GetMapping("/manage")
    public ModelAndView manageUsers(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("manageUser");
        long id = (Long) session.getAttribute("userId");
        User user = userService.findUserId(id);
        modelAndView.addObject("user", user);
        BadgeBalance badgeBalance = badgeBalanceService.getBadgeByUserId(user);
        boolean role = user.isAdmin();
        List<User> users = userService.findAllByIdIsNot(id);
        modelAndView.addObject("users", users);
        List<BadgeBalance> badgeBalances = new ArrayList<>();
        for (User u : users) {
            badgeBalances.add(badgeBalanceService.getBadgeByUserId(u));
        }
        long gold = badgeTransactionService.countByReceiverAndBadge(user, Badge.GOLD);
        long silver = badgeTransactionService.countByReceiverAndBadge(user, Badge.SILVER);
        long bronze = badgeTransactionService.countByReceiverAndBadge(user, Badge.BRONZE);
        modelAndView.addObject("gold", gold);
        modelAndView.addObject("silver", silver);
        modelAndView.addObject("bronze", bronze);
        modelAndView.addObject("badgeBalance", badgeBalance);
        modelAndView.addObject("badgeBalances", badgeBalances);
        modelAndView.addObject("recognizeco", new RecognizeCO());
        modelAndView.addObject("role", role);
        return modelAndView;
    }

    @PostMapping("/updateUserRole")
    @ResponseBody
    public String updateUserRole(@RequestParam("role") String role, @RequestParam("userId") String userId) {
        userService.updateUserRole(role,userId);
        return "redirect:/manage";
    }

    @PostMapping("/updateAdminRole")
    @ResponseBody
    public String updateAdminRole(@RequestParam("isAdmin") String isAdmin, @RequestParam("userId") String userId) {
        userService.updateAdminRole(isAdmin,userId);
        return "redirect:/manage";
    }

    @GetMapping("updateUserActive")
    @ResponseBody
    public User updateUserActive(@RequestParam("isActive") Boolean isActive, @RequestParam("userId") Long userId) {
        return userService.updateUserActive(isActive, userId);
    }

    @GetMapping("updateAvailPoints")
    @ResponseBody
    public User updateAvailPoints(@RequestParam("availPoints") Integer availPoints, @RequestParam("userId") Long userId) {
        return userService.updateAvailPoints(availPoints, userId);
    }

    @GetMapping("updateGoldCount")
    @ResponseBody
    public User updateGoldCount(@RequestParam("goldCount") Integer goldCount, @RequestParam("badgeBalance") BadgeBalance badgeBalance,@RequestParam("userId") Long userId) {
        return userService.updateGoldCount(goldCount, badgeBalance,userId);
    }
    @GetMapping("updateSilverCount")
    @ResponseBody
    public User updateSilverCount(@RequestParam("silverCount") Integer silverCount, @RequestParam("badgeBalance") BadgeBalance badgeBalance,@RequestParam("userId") Long userId) {
        return userService.updateSilverCount(silverCount, badgeBalance,userId);
    }
    @GetMapping("updateBronzeCount")
    @ResponseBody
    public User updateBronzeCount(@RequestParam("bronzeCount") Integer bronzeCount, @RequestParam("badgeBalance") BadgeBalance badgeBalance,@RequestParam("userId") Long userId) {
        return userService.updateBronzeCount(bronzeCount, badgeBalance,userId);
    }

}
