package com.razdeep.konsignapi.repository;

import com.razdeep.konsignapi.entity.BuyerEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface BuyerRepository extends JpaRepository<BuyerEntity, String> {
    BuyerEntity findBuyerByBuyerName(@NonNull String buyerName);
}
