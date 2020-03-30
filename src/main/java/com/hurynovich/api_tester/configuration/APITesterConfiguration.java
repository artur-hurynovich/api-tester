package com.hurynovich.api_tester.configuration;

import com.hurynovich.api_tester.state_transition.signal.SignalName;
import com.hurynovich.api_tester.state_transition.state.State;
import com.hurynovich.api_tester.state_transition.state.StateName;
import com.hurynovich.api_tester.state_transition.state.impl.StateImpl;
import com.hurynovich.api_tester.state_transition.state_transition_descriptor.impl.StateTransitionDescriptorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class APITesterConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Set<State> availableStates() {
        final Set<State> availableStates = new HashSet<>();

        availableStates.add(
                new StateImpl(StateName.INIT, Collections.singleton(
                        new StateTransitionDescriptorImpl(SignalName.RUN, StateName.PENDING_RUNNING))));

        availableStates.add(
                new StateImpl(StateName.PENDING_RUNNING, Arrays.asList(
                        new StateTransitionDescriptorImpl(SignalName.RUN, StateName.RUNNING),
                        new StateTransitionDescriptorImpl(SignalName.RESUME, StateName.RUNNING))));

        availableStates.add(
                new StateImpl(StateName.RUNNING, Arrays.asList(
                        new StateTransitionDescriptorImpl(SignalName.PAUSE, StateName.PENDING_PAUSED),
                        new StateTransitionDescriptorImpl(SignalName.STOP, StateName.PENDING_STOPPED))));

        availableStates.add(
                new StateImpl(StateName.PENDING_PAUSED, Collections.singleton(
                        new StateTransitionDescriptorImpl(SignalName.PAUSE, StateName.PAUSED))));

        availableStates.add(
                new StateImpl(StateName.PAUSED, Arrays.asList(
                        new StateTransitionDescriptorImpl(SignalName.RESUME, StateName.PENDING_RUNNING),
                        new StateTransitionDescriptorImpl(SignalName.STOP, StateName.PENDING_STOPPED))));

        availableStates.add(
                new StateImpl(StateName.PENDING_STOPPED, Collections.singleton(
                        new StateTransitionDescriptorImpl(SignalName.STOP, StateName.STOPPED))));

        availableStates.add(
                new StateImpl(StateName.STOPPED, Collections.emptyList()));

        availableStates.add(
                new StateImpl(StateName.FINISHED, Collections.emptyList()));

        availableStates.add(
                new StateImpl(StateName.ERROR, Collections.emptyList()));

        return availableStates;
    }

}
