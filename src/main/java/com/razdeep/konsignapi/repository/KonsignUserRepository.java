package com.razdeep.konsignapi.repository;

import com.razdeep.konsignapi.model.KonsignUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KonsignUserRepository extends JpaRepository<KonsignUser, Integer> {
    Optional<KonsignUser> findKonsignUserByUsername(String username);
}
