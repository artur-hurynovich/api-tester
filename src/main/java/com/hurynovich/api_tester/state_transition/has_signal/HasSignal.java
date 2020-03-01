package com.hurynovich.api_tester.state_transition.has_signal;

import com.hurynovich.api_tester.state_transition.signal.Signal;

public interface HasSignal {

    Signal getSignal();

    void setSignal(Signal signal);

}
