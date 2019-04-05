package com.ttn.reap.bootstrap;

import com.ttn.reap.entity.*;
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
        //populating user
        Attachment attachment1=new Attachment("z.jpg","image/jpeg","resources/static/upload", new Date());
        Attachment attachment2=new Attachment("z.jpg","image/jpeg","resources/static/upload", new Date());
        Attachment attachment3=new Attachment("z.jpg","image/jpeg","resources/uploads", new Date());
        Attachment attachment4=new Attachment("z.jpg","image/jpeg","resources/uploads", new Date());
        User user1=new User("1411dolly@gmail.com","dolly","singh","Dolly Singh",0,0,"12345",0,null, Role.USER,false,true,attachment1);
        User user2=new User("amarjeet@gmail.com","amarjeet","malik","Amarjeet Malik",0,0,"12345",0,null, Role.USER,false,true,attachment2);
        User user3=new User("aditya@gmail.com","aditya","singh","Aditya Singh",0,0,"12345",0,null, Role.USER,false,true,attachment3);
        User user4=new User("dharmendra@gmail.com","dharmendra","saini","Dharmendra Saini",0,0,"12345",0,null, Role.USER,false,true,attachment4);
        BadgeBalance badgeBalance1=new BadgeBalance(user1,2,2,1);
        BadgeBalance badgeBalance2=new BadgeBalance(user2,3,1,1);
        BadgeBalance badgeBalance3=new BadgeBalance(user3,2,2,1);
        BadgeBalance badgeBalance4=new BadgeBalance(user4,3,2,0);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        badgeBalanceRepository.save(badgeBalance1);
        badgeBalanceRepository.save(badgeBalance2);
        badgeBalanceRepository.save(badgeBalance3);
        badgeBalanceRepository.save(badgeBalance4);

        BadgeTransaction badgeTransaction1=new BadgeTransaction(user1,user2,new Date(),"reason1",Badge.GOLD);
        BadgeTransaction badgeTransaction2=new BadgeTransaction(user2,user3,new Date(),"reason2",Badge.SILVER);
        BadgeTransaction badgeTransaction3=new BadgeTransaction(user4,user1,new Date(),"reason3",Badge.BRONZE);
        BadgeTransaction badgeTransaction4=new BadgeTransaction(user3,user4,new Date(),"reason4",Badge.GOLD);



        badgeTransactionRepository.save(badgeTransaction1);
        badgeTransactionRepository.save(badgeTransaction2);
        badgeTransactionRepository.save(badgeTransaction3);
        badgeTransactionRepository.save(badgeTransaction4);


    }
}
