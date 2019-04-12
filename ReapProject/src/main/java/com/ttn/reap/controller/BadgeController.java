package com.ttn.reap.controller;

import com.ttn.reap.entity.BadgeTransaction;
import com.ttn.reap.entity.User;
import com.ttn.reap.enums.Badge;
import com.ttn.reap.repository.BadgeTransactionRepository;
import com.ttn.reap.service.BadgeBalanceService;
import com.ttn.reap.service.BadgeTransactionService;
import com.ttn.reap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BadgeController {
    @Autowired
    UserService userService;
    @Autowired
    BadgeTransactionRepository badgeTransactionRepository;
    @Autowired
    private BadgeBalanceService badgeBalanceService;
    @Autowired
    private BadgeTransactionService badgeTransactionService;

    @GetMapping("/badges")
    public ModelAndView badge(HttpSession session) {
        if (session.getAttribute("userId") == null) {
            ModelAndView modelAndView = new ModelAndView("login");
            return modelAndView;
        } else return badges(session);
    }

    @PostMapping("/badges")
    public ModelAndView badges(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("badges");
        if (session.getAttribute("userId") != null) {
            long id = (long) session.getAttribute("userId");
            User user = userService.findUserId(id);
            boolean role = user.isAdmin();
            long gold = badgeTransactionService.countByReceiverAndBadge(user, Badge.GOLD);
            long silver = badgeTransactionService.countByReceiverAndBadge(user, Badge.SILVER);
            long bronze = badgeTransactionService.countByReceiverAndBadge(user, Badge.BRONZE);
            List<BadgeTransaction> badgeTransactionListReceiver = badgeTransactionService.findAllByReceiverOrderByDateDesc(user);
            List<BadgeTransaction> badgeTransactionListSender = badgeTransactionService.findAllBySenderOrderByDateDesc(user);
            List<BadgeTransaction> badgeTransactionListSenderOrReceiver = badgeTransactionService.findAllBySenderOrReceiverOrderByDateDesc(user, user);
            long receivedCount = badgeTransactionService.countByReceiver(user);
            long sendCount = badgeTransactionService.countBySender(user);
            long allCount = receivedCount + sendCount;
            modelAndView.addObject("role", role);
            modelAndView.addObject("user", user);
            modelAndView.addObject("gold", gold);
            modelAndView.addObject("silver", silver);
            modelAndView.addObject("bronze", bronze);
            modelAndView.addObject("badgetransactionlistreceiver", badgeTransactionListReceiver);
            modelAndView.addObject("badgetransactionlistsender", badgeTransactionListSender);
            modelAndView.addObject("badgetransactionlistsenderorreceiver", badgeTransactionListSenderOrReceiver);
            modelAndView.addObject("receivedcount", receivedCount);
            modelAndView.addObject("sendercount", sendCount);
            modelAndView.addObject("allcount", allCount);
        } else {
            //do something if user is null
            System.out.println("user is null in badge mapping");
        }
        return modelAndView;
    }
}
