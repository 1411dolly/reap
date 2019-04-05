package com.ttn.reap.repository;

import com.ttn.reap.entity.BadgeTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BadgeTransactionRepository extends CrudRepository<BadgeTransaction,Long> {
    Optional<BadgeTransaction> findById(long id);
}
