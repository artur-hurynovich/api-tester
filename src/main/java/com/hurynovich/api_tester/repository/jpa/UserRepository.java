package com.hurynovich.api_tester.repository.jpa;

import com.hurynovich.api_tester.model.persistence.entity.impl.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
