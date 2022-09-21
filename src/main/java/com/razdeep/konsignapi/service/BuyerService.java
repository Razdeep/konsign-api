package com.razdeep.konsignapi.service;

import com.razdeep.konsignapi.entity.BuyerEntity;
import com.razdeep.konsignapi.model.Buyer;
import com.razdeep.konsignapi.repository.BuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuyerService {

    @Autowired
    private BuyerRepository buyerRepository;

    public List<Buyer> getBuyers() {
        List<Buyer> result = new ArrayList<>();
        buyerRepository.findAll().forEach((buyerEntity) -> {
            result.add(new Buyer(buyerEntity));
        });
        return result;
    }

    public boolean addBuyer(Buyer buyer) {
        if (buyer.getBuyerId().isEmpty()) {
            return false;
        }
        buyerRepository.save(new BuyerEntity(buyer));
        return true;
    }

    public boolean deleteBuyer(String buyerId) {
        boolean wasPresent = buyerRepository.findById(buyerId).isPresent();
        buyerRepository.deleteById(buyerId);
        return wasPresent;
    }
}
