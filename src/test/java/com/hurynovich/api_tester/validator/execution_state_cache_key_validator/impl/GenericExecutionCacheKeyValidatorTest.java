package com.hurynovich.api_tester.validator.execution_state_cache_key_validator.impl;

import com.hurynovich.api_tester.cache.cache_key.impl.GenericExecutionCacheKey;
import com.hurynovich.api_tester.model.dto.impl.RequestChainDTO;
import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.test_helper.ExecutionTestHelper;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
import com.hurynovich.api_tester.validator.Validator;
import com.hurynovich.api_tester.validator.execution_state_cache_key_validator.ExecutionStateCacheKeyValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith({MockitoExtension.class})
public class GenericExecutionCacheKeyValidatorTest {

    @Mock
    private DTOService<UserDTO, Long> userService;

    @Mock
    private DTOService<RequestChainDTO, Long> requestChainService;

    private Validator<GenericExecutionCacheKey> keyValidator;

    @BeforeEach
    public void init() {
        keyValidator = new ExecutionStateCacheKeyValidator(userService, requestChainService);
    }

    @Test
    public void successValidationTest() {
        final GenericExecutionCacheKey key = ExecutionTestHelper.buildExecutionStateCacheKey();

        Mockito.when(userService.existsById(key.getUserId())).thenReturn(true);
        Mockito.when(requestChainService.existsById(key.getRequestChainId())).thenReturn(true);

        final ValidationResult validationResult = keyValidator.validate(key);

        Assertions.assertEquals(ValidationResultType.VALID, validationResult.getType());
        Assertions.assertTrue(validationResult.getDescriptions().isEmpty());
    }

    @Test
    public void userIdNullFailureValidationTest() {
        final GenericExecutionCacheKey key = ExecutionTestHelper.buildExecutionStateCacheKey();

        key.setUserId(null);

        Mockito.when(requestChainService.existsById(key.getRequestChainId())).thenReturn(true);

        final ValidationResult validationResult = keyValidator.validate(key);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'userId' can't be null", descriptions.get(0));
    }

    @Test
    public void userIdZeroOrNegativeFailureValidationTest() {
        final GenericExecutionCacheKey key = ExecutionTestHelper.buildExecutionStateCacheKey();

        key.setUserId((long) RandomValueGenerator.generateRandomNegativeOrZeroInt());

        Mockito.when(requestChainService.existsById(key.getRequestChainId())).thenReturn(true);

        final ValidationResult validationResult = keyValidator.validate(key);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'userId' can't be negative or zero", descriptions.get(0));
    }

    @Test
    public void userIdNonExistentFailureValidationTest() {
        final GenericExecutionCacheKey key = ExecutionTestHelper.buildExecutionStateCacheKey();

        final Long userId = key.getUserId();
        Mockito.when(userService.existsById(userId)).thenReturn(false);
        Mockito.when(requestChainService.existsById(key.getRequestChainId())).thenReturn(true);

        final ValidationResult validationResult = keyValidator.validate(key);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("No UserDTO found for userId = " + userId, descriptions.get(0));
    }

    @Test
    public void requestChainIdNullFailureValidationTest() {
        final GenericExecutionCacheKey key = ExecutionTestHelper.buildExecutionStateCacheKey();

        key.setRequestChainId(null);

        Mockito.when(userService.existsById(key.getUserId())).thenReturn(true);

        final ValidationResult validationResult = keyValidator.validate(key);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'requestChainId' can't be null", descriptions.get(0));
    }

    @Test
    public void requestChainIdZeroOrNegativeFailureValidationTest() {
        final GenericExecutionCacheKey key = ExecutionTestHelper.buildExecutionStateCacheKey();

        key.setRequestChainId((long) RandomValueGenerator.generateRandomNegativeOrZeroInt());

        Mockito.when(userService.existsById(key.getUserId())).thenReturn(true);

        final ValidationResult validationResult = keyValidator.validate(key);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'requestChainId' can't be negative or zero", descriptions.get(0));
    }

    @Test
    public void requestChainIdNonExistentFailureValidationTest() {
        final GenericExecutionCacheKey key = ExecutionTestHelper.buildExecutionStateCacheKey();

        final Long requestChainId = key.getRequestChainId();
        Mockito.when(userService.existsById(key.getUserId())).thenReturn(true);
        Mockito.when(requestChainService.existsById(requestChainId)).thenReturn(false);

        final ValidationResult validationResult = keyValidator.validate(key);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("No RequestChainDTO found for requestChainId = " + requestChainId, descriptions.get(0));
    }

}
