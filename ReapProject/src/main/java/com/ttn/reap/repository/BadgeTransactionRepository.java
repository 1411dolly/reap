package com.ttn.reap.repository;

import com.ttn.reap.entity.BadgeTransaction;
import com.ttn.reap.entity.User;
import com.ttn.reap.enums.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Repository
public interface BadgeTransactionRepository extends JpaRepository<BadgeTransaction,Long> {
    Optional<BadgeTransaction> findById(long id);
    List<BadgeTransaction> findAll();
    List<BadgeTransaction> findAllByDateBetween(Date start, Date end);
    List<BadgeTransaction> findAllByOrderByDateDesc();
    Integer countByRecieverAndBadge(@Param("reciever")User reciever, @Param("badge")Badge badge);

    List<BadgeTransaction> findAllByRecieverOrderByDateDesc(@Param("reciever")User reciever);
    List<BadgeTransaction> findAllBySenderOrderByDateDesc(@Param("sender")User reciever);
    List<BadgeTransaction> findAllBySenderOrRecieverOrderByDateDesc(@Param("sender")User sender,@Param("reciever")User reciever);
    
}
