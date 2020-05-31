package com.hurynovich.api_tester.repository.mongo_db;

import com.hurynovich.api_tester.model.persistence.document.impl.RequestContainerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequestContainerRepository extends MongoRepository<RequestContainerDocument, String> {

}
