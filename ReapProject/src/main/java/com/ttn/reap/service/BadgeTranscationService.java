package com.ttn.reap.service;

import com.ttn.reap.entity.BadgeTransaction;
import com.ttn.reap.repository.BadgeTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BadgeTranscationService {
    @Autowired
    BadgeTransactionRepository badgeTransactionRepository;
    
    public List<BadgeTransaction> findAll(){
        return badgeTransactionRepository.findAll();
    }
    
    public List<BadgeTransaction> findAllByDateBetween(Date start, Date end){
        return badgeTransactionRepository.findAllByDateBetween(start,end);
    }
}
