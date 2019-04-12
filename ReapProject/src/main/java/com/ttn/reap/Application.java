package com.ttn.reap;

import com.ttn.reap.property.FileStorageProperties;
import com.ttn.reap.repository.UserRepository;
import com.ttn.reap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

@EnableConfigurationProperties({
        FileStorageProperties.class
})
@SpringBootApplication
@EnableJpaRepositories("com.ttn.reap.repository")
@EnableAsync
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
        System.out.println("welcome to reap!!!");
    }
}
