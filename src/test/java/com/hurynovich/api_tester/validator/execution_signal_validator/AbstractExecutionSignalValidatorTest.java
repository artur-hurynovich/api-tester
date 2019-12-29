package com.hurynovich.api_tester.validator.execution_signal_validator;

import com.hurynovich.api_tester.model.dto.impl.RequestChainDTO;
import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.enumeration.ExecutionSignalType;
import com.hurynovich.api_tester.model.enumeration.ValidationResultType;
import com.hurynovich.api_tester.model.execution.ExecutionSignal;
import com.hurynovich.api_tester.model.validation.ValidationResult;
import com.hurynovich.api_tester.service.dto_service.DTOService;
import com.hurynovich.api_tester.test_helper.ExecutionTestHelper;
import com.hurynovich.api_tester.test_helper.RandomValueGenerator;
import com.hurynovich.api_tester.validator.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AbstractExecutionSignalValidatorTest {

    @Mock
    private DTOService<UserDTO, Long> USER_SERVICE;

    @Mock
    private DTOService<RequestChainDTO, Long> REQUEST_CHAIN_SERVICE;

    private Validator<ExecutionSignal> SIGNAL_VALIDATOR;

    @BeforeEach
    public void init() {
        SIGNAL_VALIDATOR =
                new AbstractExecutionSignalValidator(USER_SERVICE, REQUEST_CHAIN_SERVICE) {
                    @Override
                    protected void processNotNullSignalTypeValidation(ExecutionSignal signal, ValidationResult validationResult) {

                    }
                };
    }

    @Test
    public void successValidationTest() {
        final ExecutionSignalType type = RandomValueGenerator.generateRandomEnumValue(ExecutionSignalType.class);
        final ExecutionSignal signal = ExecutionTestHelper.buildExecutionSignal(type);

        Mockito.when(USER_SERVICE.existsById(signal.getKey().getUserId())).thenReturn(true);
        Mockito.when(REQUEST_CHAIN_SERVICE.existsById(signal.getKey().getRequestChainId())).thenReturn(true);

        final ValidationResult validationResult = SIGNAL_VALIDATOR.validate(signal);

        Assertions.assertEquals(ValidationResultType.VALID, validationResult.getType());
        Assertions.assertTrue(validationResult.getDescriptions().isEmpty());
    }

    @Test
    public void keyIsNullFailureValidationTest() {
        final ExecutionSignalType type = RandomValueGenerator.generateRandomEnumValue(ExecutionSignalType.class);
        final ExecutionSignal signal = ExecutionTestHelper.buildExecutionSignal(type);

        signal.setKey(null);

        final ValidationResult validationResult = SIGNAL_VALIDATOR.validate(signal);

        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());

        Assertions.assertEquals("'key' can't be null", descriptions.get(0));
    }

    @Test
    public void userIdNullFailureValidationTest() {
        final ExecutionSignalType type = RandomValueGenerator.generateRandomEnumValue(ExecutionSignalType.class);
        final ExecutionSignal signal = ExecutionTestHelper.buildExecutionSignal(type);

        signal.getKey().setUserId(null);

        Mockito.when(REQUEST_CHAIN_SERVICE.existsById(signal.getKey().getRequestChainId())).thenReturn(true);

        final ValidationResult validationResult = SIGNAL_VALIDATOR.validate(signal);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'userId' can't be null", descriptions.get(0));
    }

    @Test
    public void userIdZeroOrNegativeFailureValidationTest() {
        final ExecutionSignalType type = RandomValueGenerator.generateRandomEnumValue(ExecutionSignalType.class);
        final ExecutionSignal signal = ExecutionTestHelper.buildExecutionSignal(type);

        signal.getKey().setUserId((long) RandomValueGenerator.generateRandomNegativeOrZeroInt());

        Mockito.when(REQUEST_CHAIN_SERVICE.existsById(signal.getKey().getRequestChainId())).thenReturn(true);

        final ValidationResult validationResult = SIGNAL_VALIDATOR.validate(signal);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'userId' can't be negative or zero", descriptions.get(0));
    }

    @Test
    public void userIdNonExistentFailureValidationTest() {
        final ExecutionSignalType type = RandomValueGenerator.generateRandomEnumValue(ExecutionSignalType.class);
        final ExecutionSignal signal = ExecutionTestHelper.buildExecutionSignal(type);

        final Long userId = signal.getKey().getUserId();
        Mockito.when(USER_SERVICE.existsById(userId)).thenReturn(false);
        Mockito.when(REQUEST_CHAIN_SERVICE.existsById(signal.getKey().getRequestChainId())).thenReturn(true);

        final ValidationResult validationResult = SIGNAL_VALIDATOR.validate(signal);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("No UserDTO found for userId = " + userId, descriptions.get(0));
    }

    @Test
    public void requestChainIdNullFailureValidationTest() {
        final ExecutionSignalType type = RandomValueGenerator.generateRandomEnumValue(ExecutionSignalType.class);
        final ExecutionSignal signal = ExecutionTestHelper.buildExecutionSignal(type);

        signal.getKey().setRequestChainId(null);

        Mockito.when(USER_SERVICE.existsById(signal.getKey().getUserId())).thenReturn(true);

        final ValidationResult validationResult = SIGNAL_VALIDATOR.validate(signal);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'requestChainId' can't be null", descriptions.get(0));
    }

    @Test
    public void requestChainIdZeroOrNegativeFailureValidationTest() {
        final ExecutionSignalType type = RandomValueGenerator.generateRandomEnumValue(ExecutionSignalType.class);
        final ExecutionSignal signal = ExecutionTestHelper.buildExecutionSignal(type);

        signal.getKey().setRequestChainId((long) RandomValueGenerator.generateRandomNegativeOrZeroInt());

        Mockito.when(USER_SERVICE.existsById(signal.getKey().getUserId())).thenReturn(true);

        final ValidationResult validationResult = SIGNAL_VALIDATOR.validate(signal);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("'requestChainId' can't be negative or zero", descriptions.get(0));
    }

    @Test
    public void requestChainIdNonExistentFailureValidationTest() {
        final ExecutionSignalType type = RandomValueGenerator.generateRandomEnumValue(ExecutionSignalType.class);
        final ExecutionSignal signal = ExecutionTestHelper.buildExecutionSignal(type);

        final Long requestChainId = signal.getKey().getRequestChainId();
        Mockito.when(USER_SERVICE.existsById(signal.getKey().getUserId())).thenReturn(true);
        Mockito.when(REQUEST_CHAIN_SERVICE.existsById(requestChainId)).thenReturn(false);

        final ValidationResult validationResult = SIGNAL_VALIDATOR.validate(signal);
        Assertions.assertEquals(ValidationResultType.NON_VALID, validationResult.getType());

        final List<String> descriptions = validationResult.getDescriptions();
        Assertions.assertEquals(1, descriptions.size());
        Assertions.assertEquals("No RequestChainDTO found for requestChainId = " + requestChainId, descriptions.get(0));
    }

}
