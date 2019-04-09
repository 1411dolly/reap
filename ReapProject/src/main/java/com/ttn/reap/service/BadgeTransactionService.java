package com.ttn.reap.service;

import com.ttn.reap.entity.BadgeTransaction;
import com.ttn.reap.entity.User;
import com.ttn.reap.enums.Badge;
import com.ttn.reap.repository.BadgeTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BadgeTransactionService {
    @Autowired
    BadgeTransactionRepository badgeTransactionRepository;
    
    public List<BadgeTransaction> findAllByOrderByDateDesc(){
        return badgeTransactionRepository.findAllByOrderByDateDesc();
    }
    
    public List<BadgeTransaction> findAllByDateBetween(Date start, Date end){
        return badgeTransactionRepository.findAllByDateBetween(start,end);
    }

    public BadgeTransaction findBadgeTransactionId(long id) {
        return badgeTransactionRepository.findById(id).orElse(null);}

    public Integer countByRecieverAndBadge(User user, Badge badge)
    {
        return badgeTransactionRepository.countByRecieverAndBadge(user,badge);
    }

}
