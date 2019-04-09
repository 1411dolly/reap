package com.ttn.reap.repository;

import com.ttn.reap.entity.BadgeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
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
}
