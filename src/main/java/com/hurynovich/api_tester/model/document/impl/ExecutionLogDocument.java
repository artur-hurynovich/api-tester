package com.hurynovich.api_tester.model.document.impl;

import com.hurynovich.api_tester.model.document.AbstractDocument;
import com.hurynovich.api_tester.model.dto.ExecutionLogEntryDTO;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "apte_execution_logs")
public class ExecutionLogDocument extends AbstractDocument {

    private LocalDateTime dateTime;

    private Long userId;

    private Long requestChainId;

    private List<ExecutionLogEntryDTO> entries;

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

    public Long getRequestChainId() {
        return requestChainId;
    }

    public void setRequestChainId(final Long requestChainId) {
        this.requestChainId = requestChainId;
    }

    public List<ExecutionLogEntryDTO> getEntries() {
        return entries;
    }

    public void setEntries(final List<ExecutionLogEntryDTO> entries) {
        this.entries = entries;
    }
}
