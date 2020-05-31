package com.hurynovich.api_tester.repository.mongo_db;

import com.hurynovich.api_tester.model.persistence.document.impl.ExecutionLogDocument;
import com.hurynovich.api_tester.repository.GenericRepository;

public interface ExecutionLogRepository extends GenericRepository<ExecutionLogDocument, String> {

}
