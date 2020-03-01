package com.hurynovich.api_tester.state_transition.state;

import java.util.List;

public interface State {

    String getName();

    List<String> getValidSignalNames();

    String processTransition(String signalName);

}
