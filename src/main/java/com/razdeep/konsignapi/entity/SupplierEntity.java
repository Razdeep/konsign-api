package com.razdeep.konsignapi.entity;

import com.razdeep.konsignapi.model.Supplier;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "supplier")
@Data
public class SupplierEntity {
    @Id
    private String supplierId;

    @NonNull
    private String supplierName;

    public SupplierEntity(Supplier supplier) {
        supplierId = supplier.getSupplierId();
        supplierName = supplier.getSupplierName();
    }

    public SupplierEntity() {

    }
}
