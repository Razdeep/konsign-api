package com.razdeep.konsignapi.repository;

import com.razdeep.konsignapi.entity.BuyerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerRepository extends JpaRepository<BuyerEntity, String> {

}
