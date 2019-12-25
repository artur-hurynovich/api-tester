package com.hurynovich.api_tester.service.execution_helper.impl;

import com.hurynovich.api_tester.cache.Cache;
import com.hurynovich.api_tester.cache.cache_key.impl.ExecutionStateCacheKey;
import com.hurynovich.api_tester.model.execution.ExecutionState;
import com.hurynovich.api_tester.service.execution_helper.ExecutionHelper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ExecutionHelperImplTest {

    private static final Cache<ExecutionStateCacheKey, ExecutionState> EXECUTION_STATE_CACHE = Mockito.mock(Cache.class);

    private static final ExecutionHelper EXECUTION_HELPER = new ExecutionHelperImpl(EXECUTION_STATE_CACHE);

    @Test
    public void resolveValidExecutionSignalTypesTest() {

    }

    @Test
    public void resolveTransitionToExecutionStateTypeTest() {

    }

}
