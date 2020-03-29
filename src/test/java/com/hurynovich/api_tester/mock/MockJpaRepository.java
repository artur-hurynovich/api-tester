package com.hurynovich.api_tester.mock;

import com.hurynovich.api_tester.model.persistence.entity.AbstractEntity;

public class MockJpaRepository<D extends AbstractEntity<Long>> extends GenericMockRepository<D, Long> {

}
