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
    BadgeBalanceRepository badgeBalance1lanceRepository;
    @Autowired
    BadgeTransactionRepository badgeTransactionRepository;
    @EventListener(ContextRefreshedEvent.class)
    void setup() {
<<<<<<< HEAD
        //populating user
        Attachment attachment1=new Attachment("/upload/1.jpeg","image/jpeg", new Date());
        Attachment attachment2=new Attachment("/upload/2.jpeg","image/jpeg", new Date());
        Attachment attachment3=new Attachment("/upload/3.jpeg","image/jpeg", new Date());
        Attachment attachment4=new Attachment("/upload/4.jpeg","image/jpeg", new Date());
        User user1=new User("1411dolly@gmail.com","dolly","singh",0,0,"12345",0,null, Role.USER,false,true,attachment1);
        User user2=new User("amarjeet@gmail.com","amarjeet","malik",0,0,"12345",0,null, Role.USER,false,true,attachment2);
        User user3=new User("aditya@gmail.com","aditya","singh",0,0,"12345",0,null, Role.USER,false,true,attachment3);
        User user4=new User("dharmendra@gmail.com","dharmendra","saini",0,0,"12345",0,null, Role.USER,false,true,attachment4);
        BadgeBalance badgeBalance1=new BadgeBalance(user1,2,1,1);
        BadgeBalance badgeBalance2=new BadgeBalance(user2,3,0,0);
        BadgeBalance badgeBalance3=new BadgeBalance(user3,0,2,1);
        BadgeBalance badgeBalance4=new BadgeBalance(user4,3,1,0);
=======
        User user1=new User("1411dolly@gmail.com","dolly","singh",0,0,"12345",null, Role.USER,false,true,"/upload/1.jpeg");
        User user2=new User("amarjeet@gmail.com","amarjeet","malik",0,0,"12345",null, Role.USER,false,true,"/upload/2.jpeg");
        User user3=new User("aditya@gmail.com","aditya","singh",0,0,"12345",null, Role.USER,false,true,"/upload/3.jpeg");
        User user4=new User("dharmendra@gmail.com","dharmendra","saini",0,0,"12345",null, Role.USER,true,true,"/upload/4.jpeg");
        BadgeBalance badgeBalance1=new BadgeBalance(user1,1,2,3);
        BadgeBalance badgeBalance2=new BadgeBalance(user2,1,2,3);
        BadgeBalance badgeBalance3=new BadgeBalance(user3,1,2,3);
        BadgeBalance badgeBalance4=new BadgeBalance(user4,1,2,3);
>>>>>>> 2b614a0ae2911e001b389bd6ab6ce1c1a95050ec

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
<<<<<<< HEAD


=======
>>>>>>> 2b614a0ae2911e001b389bd6ab6ce1c1a95050ec

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
<<<<<<< HEAD

=======
>>>>>>> 2b614a0ae2911e001b389bd6ab6ce1c1a95050ec

    }
}
