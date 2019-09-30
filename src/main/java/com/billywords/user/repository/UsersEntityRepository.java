package com.billywords.user.repository;

import com.billywords.user.models.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersEntityRepository extends JpaRepository<UsersEntity, Integer> {
    UsersEntity findByName(String name);
    UsersEntity findByEmail(String email);
}
