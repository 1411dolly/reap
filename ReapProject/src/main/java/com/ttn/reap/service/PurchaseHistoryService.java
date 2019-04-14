package com.ttn.reap.service;

import com.ttn.reap.entity.PurchaseHistory;
import com.ttn.reap.repository.PurchaseHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseHistoryService {
    @Autowired
    PurchaseHistoryRepository purchaseHistoryRepository;
    
    public PurchaseHistory save(PurchaseHistory purchaseHistory)
    {
       return purchaseHistoryRepository.save(purchaseHistory);
    }

}
