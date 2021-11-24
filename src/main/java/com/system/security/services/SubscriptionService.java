package com.system.security.services;

import com.system.models.Subscription;
import com.system.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Subscription get(String type){
      return subscriptionRepository.findSubscriptionsByType(type);
    }

    public List<Subscription> getAll(){
        return subscriptionRepository.findAll();
    }
    public Subscription getSubById(Long subscriptionId){
        Optional<Subscription> subs=subscriptionRepository.findById(subscriptionId);
        Subscription s=null;
        if (subs.isPresent()){
            s=subs.get();
        }
        return s;
    }
    public Subscription updateSubs(Subscription subscription){
        Optional<Subscription> subs=subscriptionRepository.findById(subscription.getSubscriptionId());
        Subscription sub=subs.get();

       sub.setType(subscription.getType());
        sub.setMembershipFee(subscription.getMembershipFee());

        sub.setNoOfBooks(subscription.getNoOfBooks());
        sub.setDurationBooks(subscription.getDurationBooks());
        sub.setChargesBooks(subscription.getChargesBooks());

        sub.setNoOfMovies(subscription.getNoOfMovies());
        sub.setDurationMovies(subscription.getDurationMovies());
        sub.setChargesMovies(subscription.getChargesMovies());

        return subscriptionRepository.save(sub);
    }
    public Subscription saveSubs(Subscription subscription){
        return subscriptionRepository.save(subscription);
    }
}
