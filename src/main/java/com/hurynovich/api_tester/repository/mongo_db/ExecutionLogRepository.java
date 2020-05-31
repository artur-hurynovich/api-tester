package com.hurynovich.api_tester.repository.mongo_db;

import com.hurynovich.api_tester.model.persistence.document.impl.ExecutionLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExecutionLogRepository extends MongoRepository<ExecutionLogDocument, String> {

}
