package com.ttn.reap.controller;

import com.ttn.reap.entity.BadgeTransaction;
import com.ttn.reap.entity.User;
import com.ttn.reap.enums.Badge;
import com.ttn.reap.service.BadgeBalanceService;
import com.ttn.reap.service.BadgeTransactionService;
import com.ttn.reap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BadgeController {
    @Autowired
    UserService userService;

    @Autowired
    private BadgeBalanceService badgeBalanceService;

    @Autowired
    private BadgeTransactionService badgeTransactionService;

    @PostMapping("/badges")
    public ModelAndView badges(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("badges");
        long id = (long) session.getAttribute("userId");
        User user = userService.findUserId(id);
        int gold = badgeTransactionService.countByRecieverAndBadge(user, Badge.GOLD);
        int silver = badgeTransactionService.countByRecieverAndBadge(user, Badge.SILVER);
        int bronze = badgeTransactionService.countByRecieverAndBadge(user, Badge.BRONZE);
        List<BadgeTransaction> badgeTransactionListReciever=badgeTransactionService.findAllByRecieverOrderByDateDesc(user);
        List<BadgeTransaction> badgeTransactionListSender=badgeTransactionService.findAllBySenderOrderByDateDesc(user);
        List<BadgeTransaction> badgeTransactionListSenderOrReciever=badgeTransactionService.findAllBySenderOrRecieverOrderByDateDesc(user,user);
        boolean role = user.isAdmin();
        modelAndView.addObject("role", role);
        if (user != null) {
            modelAndView.addObject("user", user);
            modelAndView.addObject("gold", gold);
            modelAndView.addObject("silver", silver);
            modelAndView.addObject("bronze", bronze);
            modelAndView.addObject("badgetransactionlistreciever",badgeTransactionListReciever);
            modelAndView.addObject("badgetransactionlistsender",badgeTransactionListSender);
            modelAndView.addObject("badgetransactionlistsenderorreciever",badgeTransactionListSenderOrReciever);
        } else {
            //do something if user is null
            System.out.println("user is null in badge mapping");
        }
        return modelAndView;
    }



}
