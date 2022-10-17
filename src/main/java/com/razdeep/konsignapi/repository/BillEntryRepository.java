package com.razdeep.konsignapi.repository;

import com.razdeep.konsignapi.entity.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillEntryRepository extends JpaRepository<BillEntity, String> {

    @Query(value = "select billEntity from BillEntity billEntity where billEntity.buyerEntity.buyerId = ?1")
    List<BillEntity> findAllBillsByBuyerId(String buyerId);
}
