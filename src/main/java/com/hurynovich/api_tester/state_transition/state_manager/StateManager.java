package com.hurynovich.api_tester.state_transition.state_manager;

import com.hurynovich.api_tester.state_transition.has_signal.HasSignal;
import com.hurynovich.api_tester.state_transition.has_state.HasState;
import com.hurynovich.api_tester.state_transition.state.State;

public interface StateManager {

    State getInitState();

    void processTransition(HasState hasState, HasSignal hasSignal);

    void processTransition(HasState hasState, String toStateName);

}
