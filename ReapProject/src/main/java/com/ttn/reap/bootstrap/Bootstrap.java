package com.ttn.reap.bootstrap;

import com.ttn.reap.entity.*;
import com.ttn.reap.enums.Badge;
import com.ttn.reap.enums.Role;
import com.ttn.reap.repository.BadgeBalanceRepository;
import com.ttn.reap.repository.BadgeTransactionRepository;
import com.ttn.reap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Bootstrap {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BadgeBalanceRepository badgeBalanceRepository;
    @Autowired
    BadgeTransactionRepository badgeTransactionRepository;
    @EventListener(ContextRefreshedEvent.class)
    void setup() {
        User user1=new User("1411dolly@gmail.com","dolly","singh",0,0,"12345",null, Role.USER,false,true,"/upload/1.jpeg");
        User user2=new User("amarjeet@gmail.com","amarjeet","malik",0,0,"12345",null, Role.USER,false,true,"/upload/2.jpeg");
        User user3=new User("aditya@gmail.com","aditya","singh",0,0,"12345",null, Role.USER,false,true,"/upload/3.jpeg");
        User user4=new User("dharmendra@gmail.com","dharmendra","saini",0,0,"12345",null, Role.USER,false,true,"/upload/4.jpeg");
        BadgeBalance badgeBalance1=new BadgeBalance(user1,1,2,3);
        BadgeBalance badgeBalance2=new BadgeBalance(user2,1,2,3);
        BadgeBalance badgeBalance3=new BadgeBalance(user3,1,2,3);
        BadgeBalance badgeBalance4=new BadgeBalance(user4,1,2,3);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        badgeBalanceRepository.save(badgeBalance1);
        badgeBalanceRepository.save(badgeBalance2);
        badgeBalanceRepository.save(badgeBalance3);
        badgeBalanceRepository.save(badgeBalance4);

        BadgeTransaction badgeTransaction1=new BadgeTransaction(user1,user2,new Date("4/8/19"),"reason1", Badge.GOLD);
        BadgeTransaction badgeTransaction2=new BadgeTransaction(user2,user3,new Date("7/6/19"),"reason2",Badge.SILVER);
        BadgeTransaction badgeTransaction3=new BadgeTransaction(user4,user1,new Date("6/4/19"),"reason3",Badge.BRONZE);
        BadgeTransaction badgeTransaction4=new BadgeTransaction(user3,user4,new Date(),"reason4",Badge.GOLD);
        BadgeTransaction badgeTransaction5=new BadgeTransaction(user1,user4,new Date(),"reason5",Badge.SILVER);
        BadgeTransaction badgeTransaction6=new BadgeTransaction(user2,user3,new Date(),"reason6",Badge.BRONZE);
        BadgeTransaction badgeTransaction7=new BadgeTransaction(user3,user2,new Date(),"reason7",Badge.GOLD);
        BadgeTransaction badgeTransaction8=new BadgeTransaction(user4,user1,new Date(),"reason8",Badge.SILVER);
        BadgeTransaction badgeTransaction9=new BadgeTransaction(user2,user1,new Date(),"reason9",Badge.SILVER);
        BadgeTransaction badgeTransaction10=new BadgeTransaction(user3,user4,new Date(),"reason10",Badge.GOLD);

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

    }
}
