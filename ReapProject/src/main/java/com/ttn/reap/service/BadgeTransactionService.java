package com.ttn.reap.service;

import com.ttn.reap.entity.BadgeTransaction;
import com.ttn.reap.entity.User;
import com.ttn.reap.enums.Badge;
import com.ttn.reap.repository.BadgeTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Service
public class BadgeTransactionService {
    @Autowired
    BadgeTransactionRepository badgeTransactionRepository;
    @Autowired
    BadgeBalanceService badgeBalanceService;
    @Autowired
    EmailService emailService;

    public List<BadgeTransaction> findAllByDateBetweenOrderByDateDesc(Date start,Date end) {
        return badgeTransactionRepository.findAllByDateBetweenOrderByDateDesc(start,end);
    }

    public List<BadgeTransaction> findAllByDateBetween(Date start, Date end) {
        return badgeTransactionRepository.findAllByDateBetween(start, end);
    }

    public BadgeTransaction findBadgeTransactionId(long id) {
        return badgeTransactionRepository.findById(id).orElse(null);
    }

    public Integer countByRecieverAndBadge(User user, Badge badge) {
        return badgeTransactionRepository.countByRecieverAndBadge(user, badge);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveNewTranscation(User sender, User receiver, Date date, String reason, Badge badge){
        badgeTransactionRepository.save(new BadgeTransaction(sender,receiver,date,reason,badge));
        badgeBalanceService.substractBadgeBalance(sender,receiver,badge);
    }

    public List<BadgeTransaction> findAllByRecieverOrderByDateDesc(User user) {
        return badgeTransactionRepository.findAllByRecieverOrderByDateDesc(user);
    }

    public List<BadgeTransaction> findAllBySenderOrderByDateDesc(User user) {
        return badgeTransactionRepository.findAllBySenderOrderByDateDesc(user);
    }

    public List<BadgeTransaction> findAllBySenderOrRecieverOrderByDateDesc(User reciever,User sender){
        return badgeTransactionRepository.findAllBySenderOrRecieverOrderByDateDesc(sender,reciever);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void revokeNewTranscation(long id,String optradio, String others, String fromMail){
        BadgeTransaction badgeTransaction = badgeTransactionRepository.findById(id).get();
        User sender = badgeTransaction.getSender();
        User receiver = badgeTransaction.getReciever();
        Badge badge = badgeTransaction.getBadge();
        badgeTransactionRepository.delete(id);
        badgeBalanceService.addBadgeBalance(sender,receiver,badge);
    }
}
