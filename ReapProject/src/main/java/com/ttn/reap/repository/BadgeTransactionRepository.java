package com.ttn.reap.repository;

import com.ttn.reap.entity.BadgeTransaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface BadgeTransactionRepository extends CrudRepository<BadgeTransaction,Long> {
    List<BadgeTransaction> findAll();
    
    List<BadgeTransaction> findAllByDateBetween(Date start, Date end);
}
