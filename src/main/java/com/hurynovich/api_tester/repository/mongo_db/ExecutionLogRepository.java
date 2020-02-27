package com.hurynovich.api_tester.repository.mongo_db;

import com.hurynovich.api_tester.model.document.impl.ExecutionLogDocument;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionLogRepository extends MongoRepository<ExecutionLogDocument, String> {

}
