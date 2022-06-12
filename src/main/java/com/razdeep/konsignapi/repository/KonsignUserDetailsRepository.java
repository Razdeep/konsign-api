package com.razdeep.konsignapi.repository;

import com.razdeep.konsignapi.model.KonsignUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KonsignUserDetailsRepository extends JpaRepository<KonsignUserDetails, Integer> {
    Optional<KonsignUserDetails> findKonsignUserDetailsByUsername(String username);
}
