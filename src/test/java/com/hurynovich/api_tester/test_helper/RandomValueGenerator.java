package com.hurynovich.api_tester.test_helper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomValueGenerator {

    private static final int DEFAULT_STRING_LENGTH = 10;

    private static final Random RANDOM = new Random();

    public static Long generateRandomPositiveLong() {
        return Math.abs(RANDOM.nextLong());
    }

    public static String generateRandomStringLettersOnly() {
        return RandomStringUtils.random(DEFAULT_STRING_LENGTH, true, false);
    }

    public static <E extends Enum<E>> E generateRandomEnumValue(final @NonNull Class<E> enumClass) {
        if (enumClass.isEnum()) {
            final E[] enumConstants = enumClass.getEnumConstants();

            return getRandomListElement(Arrays.asList(enumConstants));
        } else {
            throw new RuntimeException(enumClass + " is not a Enum");
        }
    }

    private static <E> E getRandomListElement(final List<E> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            final int randomIndex = RANDOM.nextInt(list.size());

            return list.get(randomIndex);
        } else {
            throw new RuntimeException("List has no elements");
        }
    }

}
