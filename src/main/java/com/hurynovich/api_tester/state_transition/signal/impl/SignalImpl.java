package com.hurynovich.api_tester.state_transition.signal.impl;

import com.hurynovich.api_tester.state_transition.signal.Signal;
import org.springframework.lang.NonNull;

public class SignalImpl implements Signal {

    private final String name;

    public SignalImpl(final @NonNull String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
