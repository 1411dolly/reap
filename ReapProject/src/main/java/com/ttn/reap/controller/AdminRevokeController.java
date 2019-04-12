package com.ttn.reap.controller;

import com.ttn.reap.service.BadgeBalanceService;
import com.ttn.reap.service.BadgeTransactionService;
import com.ttn.reap.service.EmailService;
import com.ttn.reap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminRevokeController {
    @Autowired
    BadgeTransactionService badgeTransactionService;
    @Autowired
    BadgeBalanceService badgeBalanceService;
    @Autowired
    UserService userService;
    @Autowired
    EmailService emailService;
    @Value("${spring.mail.username}")
    String fromMail;

    @PostMapping("/revokeTxn")
    public String revokeTransaction(@RequestParam String optradio, @RequestParam String others, @RequestParam String txnId) {
        emailService.revokeMailSend(fromMail, Long.parseLong(txnId), optradio, others);
        badgeTransactionService.revokeNewTranscation(Long.parseLong(txnId), optradio, others, fromMail);
        return "redirect:/user";
    }
}
