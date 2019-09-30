package com.billywords.learning.repository;

import com.billywords.words.models.ExampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ExampleEntityRepository extends JpaRepository<ExampleEntity, Integer> {

}
