package com.razdeep.konsignapi.service;

import com.razdeep.konsignapi.entity.SupplierEntity;
import com.razdeep.konsignapi.model.Supplier;
import com.razdeep.konsignapi.repository.SupplierRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    private final CommonService commonService;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository, CommonService commonService) {
        this.supplierRepository = supplierRepository;
        this.commonService = commonService;
    }

    public boolean isSupplierIdTaken(String supplierId) {
        return supplierRepository.findById(supplierId).isPresent();
    }

    public List<Supplier> getSuppliers() {
        List<Supplier> result = new ArrayList<>();
        supplierRepository.findAll().forEach((supplierEntity) -> {
            result.add(new Supplier(supplierEntity));
        });
        return result;
    }

    public boolean addSupplier(Supplier supplier) {
        if (supplier.getSupplierId().isEmpty()) {
            if (supplier.getSupplierName().isEmpty()) {
                return false;
            }
            val baseCandidateSupplierId = commonService.generateInitials(supplier.getSupplierName());
            String candidateSupplierId = baseCandidateSupplierId;
            int attempt = 2;
            while (isSupplierIdTaken(candidateSupplierId)) {
                candidateSupplierId = baseCandidateSupplierId + Integer.toString(attempt++);
            }
            supplier.setSupplierId(candidateSupplierId);
        }
        supplierRepository.save(new SupplierEntity(supplier));
        return true;
    }

    public boolean deleteSupplier(String supplierId) {
        boolean wasPresent = supplierRepository.findById(supplierId).isPresent();
        if (wasPresent) {
            supplierRepository.deleteById(supplierId);
        }
        return wasPresent;
    }

    public SupplierEntity getSupplierBySupplierName(String supplierName) {
        return supplierRepository.findAllSupplierBySupplierName(supplierName).get(0);
    }
}
