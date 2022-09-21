package com.razdeep.konsignapi.model;

import com.razdeep.konsignapi.entity.BuyerEntity;
import lombok.Data;

@Data
public class Buyer {
    String buyerId;
    String buyerName;

    public Buyer(BuyerEntity buyerEntity) {
        buyerId = buyerEntity.getBuyerId();
        buyerName = buyerEntity.getBuyerName();
    }

    public Buyer() {}
}
