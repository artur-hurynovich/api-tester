package com.hurynovich.api_tester.model.execution;

import com.hurynovich.api_tester.model.dto.impl.RequestDTO;
import com.hurynovich.api_tester.state_transition.has_state.HasState;
import com.hurynovich.api_tester.state_transition.state.State;

import java.util.List;

public class ExecutionState implements HasState {

    private State state;

    private List<RequestDTO> requests;

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void setState(final State state) {
        this.state = state;
    }

    public List<RequestDTO> getRequests() {
        return requests;
    }

    public void setRequests(final List<RequestDTO> requests) {
        this.requests = requests;
    }

}
