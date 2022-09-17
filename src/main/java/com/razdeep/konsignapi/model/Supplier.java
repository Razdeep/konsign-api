package com.razdeep.konsignapi.model;

import com.razdeep.konsignapi.entity.SupplierEntity;
import lombok.Data;

@Data
public class Supplier {
    String supplierId;
    String supplierName;

    public Supplier(SupplierEntity supplierEntity) {
        supplierId = supplierEntity.getSupplierId();
        supplierName = supplierEntity.getSupplierName();
    }

    public Supplier() {}
}
