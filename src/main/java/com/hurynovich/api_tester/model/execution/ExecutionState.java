package com.hurynovich.api_tester.model.execution;

import com.hurynovich.api_tester.state_transition.has_state.HasState;
import com.hurynovich.api_tester.state_transition.state.State;

import java.util.List;

public class ExecutionState implements HasState {

    private State state;

    private List<IndexedRequestDTOWrapper> requests;

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void setState(final State state) {
        this.state = state;
    }

    public List<IndexedRequestDTOWrapper> getRequests() {
        return requests;
    }

    public void setRequests(final List<IndexedRequestDTOWrapper> requests) {
        this.requests = requests;
    }

}
