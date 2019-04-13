package com.ttn.reap.controller;

import com.ttn.reap.entity.BadgeBalance;
import com.ttn.reap.entity.Item;
import com.ttn.reap.entity.User;
import com.ttn.reap.service.BadgeBalanceService;
import com.ttn.reap.service.ItemService;
import com.ttn.reap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

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
        } else {
            return redeem(new Item(), session);
        }
    }

    @PostMapping("/redeem")
    public ModelAndView redeem(@ModelAttribute("item") Item item, HttpSession session) {
        List<Item> itemList = itemService.findAll();
        ModelAndView modelAndView = new ModelAndView("redeem");
        long id = (long) session.getAttribute("userId");
        User user = userService.findUserId(id);
        BadgeBalance badge = badgeBalanceService.getBadgeById(id);
        boolean role = user.isAdmin();
        modelAndView.addObject("role", role);
        modelAndView.addObject("user", user);
        modelAndView.addObject("badge", badge);
        modelAndView.addObject("redeem", itemList);
        return modelAndView;
    }

    @RequestMapping(value = "items", method = RequestMethod.GET)
    @ResponseBody
    public Item fetchsipperAjax(@RequestParam String itemId) {
        return itemService.findById(Long.parseLong(itemId));
    }
}
