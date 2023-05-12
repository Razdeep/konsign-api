package com.razdeep.konsignapi.repository;

import com.razdeep.konsignapi.entity.BuyerEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuyerRepository extends JpaRepository<BuyerEntity, String> {
    List<BuyerEntity> findAllBuyerByBuyerNameAndAgencyId(@NonNull String buyerName, @NonNull String agencyId);

    List<BuyerEntity> findAllByAgencyId(@NonNull String agencyId);

    Optional<BuyerEntity> findByIdAndAgencyId(@NonNull String buyerId, @NonNull String agencyId);
}
