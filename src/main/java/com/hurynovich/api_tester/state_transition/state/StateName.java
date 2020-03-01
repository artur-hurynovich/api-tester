package com.hurynovich.api_tester.state_transition.state;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StateName {

    private StateName() {

    }

    public static final String PENDING_RUNNING = "pending_running";

    public static final String RUNNING = "running";

    public static final String PENDING_PAUSED = "pending_paused";

    public static final String PAUSED = "paused";

    public static final String PENDING_STOPPED = "pending_stopped";

    public static final String STOPPED = "stopped";

    public static final String FINISHED = "finished";

    public static final String ERROR = "error";

    private static final Set<String> PENDING_STATE_NAMES = initPendingStateNames();

    public static boolean isPendingStateName(final String stateName) {
        return PENDING_STATE_NAMES.contains(stateName);
    }

    private static Set<String> initPendingStateNames() {
        return new HashSet<>(Arrays.asList(
                PENDING_RUNNING,
                PENDING_PAUSED,
                PENDING_STOPPED
        ));
    }

}
