package com.razdeep.konsignapi.service;

import com.razdeep.konsignapi.entity.SupplierEntity;
import com.razdeep.konsignapi.model.Supplier;
import com.razdeep.konsignapi.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<Supplier> getSuppliers() {
        List<Supplier> result = new ArrayList<>();
        supplierRepository.findAll().forEach((supplierEntity) -> {
            result.add(new Supplier(supplierEntity));
        });
        return result;
    }

    public boolean addSupplier(Supplier supplier) {
        if (supplier.getSupplierId().isEmpty()) {
            return false;
        }
        supplierRepository.save(new SupplierEntity(supplier));
        return true;
    }

    public boolean deleteSupplier(String supplierId) {
        boolean wasPresent = supplierRepository.findById(supplierId).isPresent();
        supplierRepository.deleteById(supplierId);
        return wasPresent;
    }
}
