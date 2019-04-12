package com.ttn.reap.controller;

import com.ttn.reap.entity.BadgeBalance;
import com.ttn.reap.entity.Item;
import com.ttn.reap.entity.User;
import com.ttn.reap.service.BadgeBalanceService;
import com.ttn.reap.service.ItemService;
import com.ttn.reap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class RedeemController {

    @Autowired
    UserService userService;
    @Autowired
    BadgeBalanceService badgeBalanceService;
    @Autowired
    ItemService itemService;

    @GetMapping("/redeem")
    public ModelAndView badge(HttpSession session) {
        if (session.getAttribute("userId") == null) {
            ModelAndView modelAndView = new ModelAndView("login");
            return modelAndView;
        } else return redeem(session);
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

    @RequestMapping(value = "itemsipper", method = RequestMethod.GET)
    @ResponseBody
    public Item fetchsipperAjax(Model model) {
        Item itemsipper = new Item("SIPPER", 10, "/items/sipper.jpeg", 4);
        model.addAttribute("itemsipper", itemsipper);
        System.out.println("itemsipper aja");
        return itemsipper;
    }


}
