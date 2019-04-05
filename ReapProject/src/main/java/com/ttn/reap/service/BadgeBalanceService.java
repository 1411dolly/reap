package com.ttn.reap.service;

import com.ttn.reap.entity.BadgeBalance;
import com.ttn.reap.entity.User;
import com.ttn.reap.repository.BadgeBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BadgeBalanceService {
    @Autowired
    BadgeBalanceRepository badgeBalanceRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public BadgeBalance setBadgeCount(User user) {
        BadgeBalance badgeBalance=new BadgeBalance(user,3,2,1);
        badgeBalanceRepository.save(badgeBalance);
        return badgeBalance;
    }

}
