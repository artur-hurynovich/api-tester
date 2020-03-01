package com.hurynovich.api_tester.state_transition.signal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SignalName {

    private SignalName() {

    }

    public static final String RUN = "run";

    public static final String PAUSE = "pause";

    public static final String RESUME = "resume";

    public static final String STOP = "stop";

    private static final Set<String> ACTIVATE_SIGNAL_NAMES = initActivateSignalNames();

    public static boolean isActivateSignalName(final String signalName) {
        return ACTIVATE_SIGNAL_NAMES.contains(signalName);
    }

    private static Set<String> initActivateSignalNames() {
        return new HashSet<>(Arrays.asList(
                RUN,
                RESUME
        ));
    }

}
