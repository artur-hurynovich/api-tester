package com.hurynovich.api_tester.model.persistence.document.impl;

import com.hurynovich.api_tester.model.persistence.document.AbstractDocument;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "apte_execution_logs")
public class ExecutionLogDocument extends AbstractDocument {

    private LocalDateTime dateTime;

    private Long userId;

    private String requestContainerId;

    private List<ExecutionLogEntryDocument> entries;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(final LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public String getRequestContainerId() {
        return requestContainerId;
    }

    public void setRequestContainerId(final String requestContainerId) {
        this.requestContainerId = requestContainerId;
    }

    public List<ExecutionLogEntryDocument> getEntries() {
        return entries;
    }

    public void setEntries(final List<ExecutionLogEntryDocument> entries) {
        this.entries = entries;
    }
}