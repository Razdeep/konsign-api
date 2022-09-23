package com.razdeep.konsignapi.entity;

import com.razdeep.konsignapi.model.Buyer;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "buyer")
@Data
public class BuyerEntity {
    @Id
    private String buyerId;

    @NonNull
    private String buyerName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "buyerEntity")
    private List<BillEntity> billEntities;

    public BuyerEntity(Buyer buyer) {
        buyerId = buyer.getBuyerId();
        buyerName = buyer.getBuyerName();
    }

    public BuyerEntity() {

    }
}
