package com.ttn.reap.bootstrap;

import com.ttn.reap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

public class Bootstrap {
    @Autowired
    UserRepository userRepository;

    @EventListener(ContextRefreshedEvent.class)
    void setup() {
//        User admin=new User("1411dolly@gmail.com","dolly","singh","12345",0, Collections.singletonList(Role.USER),false,true,null);
//        userRepository.save(admin);
    }
}
