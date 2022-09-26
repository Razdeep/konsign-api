package com.razdeep.konsignapi.repository;

import com.razdeep.konsignapi.entity.TransportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportRepository extends JpaRepository<TransportEntity, String> {

    TransportEntity findTransportByTransportName(String transportName);

}
