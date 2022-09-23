package com.razdeep.konsignapi.entity;

import com.razdeep.konsignapi.model.Supplier;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "supplier")
@Data
public class SupplierEntity {
    @Id
    private String supplierId;

    @NonNull
    private String supplierName;

    @OneToMany(mappedBy = "supplierEntity")
    private List<BillEntity> billEntities;

    public SupplierEntity(Supplier supplier) {
        supplierId = supplier.getSupplierId();
        supplierName = supplier.getSupplierName();
    }

    public SupplierEntity() {

    }
}
