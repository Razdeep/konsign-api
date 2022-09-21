package com.razdeep.konsignapi.entity;

import com.razdeep.konsignapi.model.Buyer;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "buyer")
@Data
public class BuyerEntity {
    @Id
    private String buyerId;

    @NonNull
    private String buyerName;

    public BuyerEntity(Buyer buyer) {
        buyerId = buyer.getBuyerId();
        buyerName = buyer.getBuyerName();
    }

    public BuyerEntity() {

    }
}
