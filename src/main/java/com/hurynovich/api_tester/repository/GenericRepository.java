package com.hurynovich.api_tester.repository;

import com.hurynovich.api_tester.model.persistence.PersistentObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.io.Serializable;

public interface GenericRepository<T extends PersistentObject<I>, I extends Serializable>
        extends CrudRepository<T, I>, QueryByExampleExecutor<T> {

}
