package com.razdeep.konsignapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bill")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillEntity {
    @Id
    private String billNo;

    @ManyToOne
    @JoinColumn(name = "fk_supplier_id", nullable = false)
    private SupplierEntity supplierEntity;

    @ManyToOne
    @JoinColumn(name = "fk_buyer_id", nullable = false)
    private BuyerEntity buyerEntity;

    private String billDate;

    @ManyToOne
    @JoinColumn(name = "fk_transport_id", nullable = false)
    private TransportEntity transportEntity;

    private String lrDate;
    private Float billAmount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "billEntry")
    private List<LrPmEntity> lrPmEntityList;
}
