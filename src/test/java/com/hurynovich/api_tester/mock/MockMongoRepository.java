package com.hurynovich.api_tester.mock;

import com.hurynovich.api_tester.model.persistence.entity.AbstractEntity;

public class MockMongoRepository<D extends AbstractEntity<String>> extends GenericMockRepository<D, String> {

}
