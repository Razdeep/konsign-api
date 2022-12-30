package com.razdeep.konsignapi.repository;

import com.razdeep.konsignapi.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, String> {

    List<SupplierEntity> findAllSupplierBySupplierName(String supplierName);
}
