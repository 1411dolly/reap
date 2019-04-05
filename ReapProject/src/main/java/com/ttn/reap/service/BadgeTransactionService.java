package com.ttn.reap.service;

import com.ttn.reap.entity.BadgeTransaction;
import com.ttn.reap.entity.User;
import com.ttn.reap.repository.BadgeTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BadgeTransactionService {
    @Autowired
    BadgeTransactionRepository badgeTransactionRepository;

    public BadgeTransaction findBadgeTransactionId(long id)
    {return badgeTransactionRepository.findById(id).orElse(null);}

}
