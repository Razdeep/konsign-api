package com.razdeep.konsignapi.service;

import com.razdeep.konsignapi.entity.BuyerEntity;
import com.razdeep.konsignapi.model.Buyer;
import com.razdeep.konsignapi.repository.BuyerRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuyerService {

    private final BuyerRepository buyerRepository;

    private final CommonService commonService;

    @Autowired
    public BuyerService(BuyerRepository buyerRepository, CommonService commonService) {
        this.buyerRepository = buyerRepository;
        this.commonService = commonService;
    }

    public List<Buyer> getBuyers() {
        List<Buyer> result = new ArrayList<>();
        buyerRepository.findAll().forEach((buyerEntity) -> result.add(new Buyer(buyerEntity)));
        return result;
    }

    private boolean isBuyerIdTaken(String buyerId) {
        return buyerRepository.findById(buyerId).isPresent();
    }


    public boolean addBuyer(Buyer buyer) {

        if (!buyerRepository.findAllBuyerByBuyerName(buyer.getBuyerName()).isEmpty()) {
            return false;
        }

        if (buyer.getBuyerId().isEmpty()) {
            if (buyer.getBuyerName().isEmpty()) {
                return false;
            }
            val baseCandidateBuyerId = commonService.generateInitials(buyer.getBuyerName());
            String candidateBuyerId = baseCandidateBuyerId;
            int attempt = 2;
            while (isBuyerIdTaken(candidateBuyerId)) {
                candidateBuyerId = baseCandidateBuyerId + attempt++;
            }
            buyer.setBuyerId(candidateBuyerId);
        }
        buyerRepository.save(new BuyerEntity(buyer));
        return true;
    }

    public boolean deleteBuyer(String buyerId) {
        boolean wasPresent = buyerRepository.findById(buyerId).isPresent();
        if (wasPresent) {
            buyerRepository.deleteById(buyerId);
        }
        return wasPresent;
    }

    public BuyerEntity getBuyerByBuyerName(String buyerName) {
        val resultList = buyerRepository.findAllBuyerByBuyerName(buyerName);
        return resultList == null || resultList.isEmpty() ? null : resultList.get(0);
    }
}
