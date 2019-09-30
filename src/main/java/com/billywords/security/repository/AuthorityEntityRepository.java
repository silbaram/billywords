package com.billywords.security.repository;

import com.billywords.security.models.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityEntityRepository extends JpaRepository<AuthorityEntity, Integer> {
    AuthorityEntity findByAuthorityName(String authorityName);
}
