package com.hurynovich.api_tester.repository;

import com.hurynovich.api_tester.model.entity.impl.NameValueElementEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestElementRepository extends JpaRepository<NameValueElementEntity, Long> {

}
