package com.ttn.reap;

import com.ttn.reap.property.FileStorageProperties;
import com.ttn.reap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.security.MessageDigest;

@EnableConfigurationProperties({
        FileStorageProperties.class
})
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
        System.out.println("welcome to reap!!!");

    }
}
