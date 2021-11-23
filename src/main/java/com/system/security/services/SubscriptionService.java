package com.system.security.services;

import com.system.models.Subscription;
import com.system.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Subscription get(String type){
      return subscriptionRepository.findSubscriptionsByType(type);
    }
}
