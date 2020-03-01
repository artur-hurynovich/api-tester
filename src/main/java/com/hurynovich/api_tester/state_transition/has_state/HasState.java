package com.hurynovich.api_tester.state_transition.has_state;

import com.hurynovich.api_tester.state_transition.state.State;

public interface HasState {

    State getState();

    void setState(State state);

}
