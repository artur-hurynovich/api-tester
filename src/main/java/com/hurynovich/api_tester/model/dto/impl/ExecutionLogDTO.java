package com.hurynovich.api_tester.model.dto.impl;

import com.hurynovich.api_tester.model.dto.AbstractDTO;

import java.time.LocalDate;
import java.util.List;

public class ExecutionLogDTO extends AbstractDTO {

    private LocalDate date;

    private List<ExecutionLogEntryDTO> entries;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public List<ExecutionLogEntryDTO> getEntries() {
        return entries;
    }

    public void setEntries(final List<ExecutionLogEntryDTO> entries) {
        this.entries = entries;
    }

}
