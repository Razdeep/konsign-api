package com.razdeep.konsignapi.repository;

import com.razdeep.konsignapi.entity.TransportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportRepository extends JpaRepository<TransportEntity, String> {

    List<TransportEntity> findAllTransportByTransportName(String transportName);

}
