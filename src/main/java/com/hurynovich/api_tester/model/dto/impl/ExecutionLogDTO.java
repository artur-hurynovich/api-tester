package com.hurynovich.api_tester.model.dto.impl;

import com.hurynovich.api_tester.model.dto.AbstractDTO;

import java.time.LocalDateTime;
import java.util.List;

public class ExecutionLogDTO extends AbstractDTO<String> {

    private LocalDateTime dateTime;

    private Long userId;

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

    public List<ExecutionLogEntryDTO> getEntries() {
        return entries;
    }

    public void setEntries(final List<ExecutionLogEntryDTO> entries) {
        this.entries = entries;
    }

}
