package com.ttn.reap.repository;

import com.ttn.reap.entity.BadgeBalance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeBalanceRepository extends CrudRepository<BadgeBalance,Long> {
}
