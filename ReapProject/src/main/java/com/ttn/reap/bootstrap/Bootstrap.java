package com.ttn.reap.bootstrap;

import com.ttn.reap.entity.BadgeBalance;
import com.ttn.reap.entity.BadgeTransaction;
import com.ttn.reap.entity.Item;
import com.ttn.reap.entity.User;
import com.ttn.reap.enums.Badge;
import com.ttn.reap.enums.Role;
import com.ttn.reap.repository.BadgeBalanceRepository;
import com.ttn.reap.repository.BadgeTransactionRepository;
import com.ttn.reap.repository.ItemRepository;
import com.ttn.reap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Bootstrap {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BadgeBalanceRepository badgeBalanceRepository;
    @Autowired
    BadgeTransactionRepository badgeTransactionRepository;
    @Autowired
    ItemRepository itemRepository;
    @EventListener(ContextRefreshedEvent.class)
    void setup() {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date1;
        Date date2;
        Date date3;
        Date date4;
        Date date5;
        Date date6;
        Date date7;
        Date date8;
        Date date9;
        Date date10;

        User user1 = new User("1411dolly@gmail.com", "dolly", "singh", 0, 0, "12345", null, Role.USER, true, true, "/upload/1.jpeg");
        User user2 = new User("amarjeet@gmail.com", "amarjeet", "malik", 0, 0, "12345", null, Role.USER, false, true, "/upload/2.jpeg");
        User user3 = new User("aditya@gmail.com", "aditya", "singh", 0, 0, "12345", null, Role.USER, false, true, "/upload/3.jpeg");
        User user4 = new User("dharmendra@gmail.com", "dharmendra", "saini", 0, 0, "12345", null, Role.USER, false, true, "/upload/4.jpeg");
        BadgeBalance badgeBalance1 = new BadgeBalance(user1, 1, 2, 3);
        BadgeBalance badgeBalance2 = new BadgeBalance(user2, 1, 2, 3);
        BadgeBalance badgeBalance3 = new BadgeBalance(user3, 1, 2, 3);
        BadgeBalance badgeBalance4 = new BadgeBalance(user4, 1, 2, 3);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        badgeBalanceRepository.save(badgeBalance1);
        badgeBalanceRepository.save(badgeBalance2);
        badgeBalanceRepository.save(badgeBalance3);
        badgeBalanceRepository.save(badgeBalance4);
        try {
            date1 = format.parse("2019/01/10");
            date2 = format.parse("2018/02/09");
            date3 = format.parse("2017/03/08");
            date4 = format.parse("2016/04/07");
            date5 = format.parse("2019/05/06");
            date6 = format.parse("2019/06/05");
            date7 = format.parse("2019/07/04");
            date8 = format.parse("2015/08/03");
            date9 = format.parse("2019/09/02");
            date10 = format.parse("2019/10/01");

            BadgeTransaction badgeTransaction1 = new BadgeTransaction(user1, user2, date1, "reason1", Badge.GOLD);
            BadgeTransaction badgeTransaction2 = new BadgeTransaction(user2, user3, date2, "reason2", Badge.SILVER);
            BadgeTransaction badgeTransaction3 = new BadgeTransaction(user4, user1, date3, "reason3", Badge.BRONZE);
            BadgeTransaction badgeTransaction4 = new BadgeTransaction(user3, user4, date4, "reason4", Badge.GOLD);
            BadgeTransaction badgeTransaction5 = new BadgeTransaction(user1, user4, date5, "reason5", Badge.SILVER);
            BadgeTransaction badgeTransaction6 = new BadgeTransaction(user2, user3, date6, "reason6", Badge.BRONZE);
            BadgeTransaction badgeTransaction7 = new BadgeTransaction(user3, user2, date7, "reason7", Badge.GOLD);
            BadgeTransaction badgeTransaction8 = new BadgeTransaction(user4, user1, date8, "reason8", Badge.SILVER);
            BadgeTransaction badgeTransaction9 = new BadgeTransaction(user2, user1, date9, "reason9", Badge.SILVER);
            BadgeTransaction badgeTransaction10 = new BadgeTransaction(user3, user4, date10, "reason10", Badge.GOLD);

            badgeTransactionRepository.save(badgeTransaction1);
            badgeTransactionRepository.save(badgeTransaction2);
            badgeTransactionRepository.save(badgeTransaction3);
            badgeTransactionRepository.save(badgeTransaction4);
            badgeTransactionRepository.save(badgeTransaction5);
            badgeTransactionRepository.save(badgeTransaction6);
            badgeTransactionRepository.save(badgeTransaction7);
            badgeTransactionRepository.save(badgeTransaction8);
            badgeTransactionRepository.save(badgeTransaction9);
            badgeTransactionRepository.save(badgeTransaction10);

            Item item1=new Item("SIPPER",10,"/items/sipper.jpeg",4);
            Item item2=new Item("BAG",20,"/items/back_bag.jpeg",4);
            Item item3=new Item("DIARY",30,"/items/boardroom_diary.jpeg",4);
            Item item4=new Item("VISITING CARD HOLDER",40,"/items/visiting_card_holder.jpeg",4);
            Item item5=new Item("WIRELESS MOUSE",50,"/items/wireless_mouse.jpeg",4);
            Item item6=new Item("COFFEE CUP",60,"/items/coffee_cup.jpeg",4);
            Item item7=new Item("KEYCHAIN",70,"/items/keychain.jpg",4);
            Item item8=new Item("MOUSE PAD",80,"/items/mouse_pad.jpg",4);
            Item item9=new Item("NOTE CARD",90,"/items/note_card.jpg",4);
            Item item10=new Item("TTN TSHIRT",100,"/items/ttn_tshirt.jpeg",4);
            itemRepository.save(item1);
            itemRepository.save(item2);
            itemRepository.save(item3);
            itemRepository.save(item4);
            itemRepository.save(item5);
            itemRepository.save(item6);
            itemRepository.save(item7);
            itemRepository.save(item8);
            itemRepository.save(item9);
            itemRepository.save(item10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}