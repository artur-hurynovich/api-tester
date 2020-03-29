package com.hurynovich.api_tester.mock;

import com.hurynovich.api_tester.model.persistence.document.AbstractDocument;

public class MockMongoRepository<D extends AbstractDocument<String>> extends GenericMockRepository<D, String> {

}
