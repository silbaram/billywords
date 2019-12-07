package com.billywords.user.repository;

import com.billywords.user.models.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersEntityRepository extends JpaRepository<UsersEntity, Integer> {
    Optional< UsersEntity> findByEmail(String email);
}
