package com.razdeep.konsignapi.repository;

import com.razdeep.konsignapi.entity.CollectionVoucherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionVoucherRepository extends JpaRepository<CollectionVoucherEntity, String> {
}
