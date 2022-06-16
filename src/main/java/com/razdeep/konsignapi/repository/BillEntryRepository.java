package com.razdeep.konsignapi.repository;

import com.razdeep.konsignapi.entity.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillEntryRepository extends JpaRepository<BillEntity, String> {

}
