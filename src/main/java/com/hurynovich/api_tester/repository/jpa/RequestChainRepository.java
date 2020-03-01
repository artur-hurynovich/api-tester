package com.hurynovich.api_tester.repository.jpa;

import com.hurynovich.api_tester.model.entity.impl.RequestChainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestChainRepository extends JpaRepository<RequestChainEntity, Long> {

}
