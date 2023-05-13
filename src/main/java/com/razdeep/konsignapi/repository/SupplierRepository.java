package com.razdeep.konsignapi.repository;

import com.razdeep.konsignapi.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, String> {

    List<SupplierEntity> findAllSupplierBySupplierNameAndAgencyId(String supplierName, String agencyId);

    List<SupplierEntity> findAllByAgencyId(String supplierName);

    Optional<SupplierEntity> findSupplierBySupplierIdAndAgencyId(String supplierId, String agencyId);
}
