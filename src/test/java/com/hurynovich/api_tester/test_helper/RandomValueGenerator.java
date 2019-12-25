package com.hurynovich.api_tester.test_helper;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomValueGenerator {

    private static final Random RANDOM = new Random();

    public static int generateRandomPositiveInt() {
        return RANDOM.nextInt(Integer.MAX_VALUE) + 1;
    }

    public static int generateRandomNegativeOrZeroInt() {
        return generateRandomPositiveInt() - Integer.MAX_VALUE;
    }

    public static String generateRandomStringLettersOnly(final int size) {
        return RandomStringUtils.random(size, true, false);
    }

    public static String generateRandomStringLettersOnly(final int minSize, final int maxSize) {
        final int size = minSize + RANDOM.nextInt(maxSize - minSize);

        return RandomStringUtils.random(size, true, false);
    }

    public static <E extends Enum<E>> E generateRandomEnumValue(final @NonNull Class<E> enumClass) {
        if (enumClass.isEnum()) {
            final E[] enumConstants = enumClass.getEnumConstants();

            return getRandomListElement(Arrays.asList(enumConstants));
        } else {
            throw new RuntimeException(enumClass + " is not a Enum");
        }
    }

    public static <E extends Enum<E>> E generateRandomEnumValueExcluding(final @NonNull Class<E> enumClass,
                                                                         final @NonNull List<E> excluded) {
        if (enumClass.isEnum()) {
            final E[] enumConstants = enumClass.getEnumConstants();

            if (enumConstants != null && enumConstants.length > 0) {
                final List<E> enumConstantsList = new ArrayList<>(Arrays.asList(enumConstants));
                enumConstantsList.removeAll(excluded);

                return getRandomListElement(enumConstantsList);
            } else {
                throw new RuntimeException(enumClass + " has no elements");
            }
        } else {
            throw new RuntimeException(enumClass + " is not a Enum");
        }
    }

    private static <E> E getRandomListElement(final List<E> list) {
        if (!CollectionUtils.isEmpty(list)) {
            final int randomIndex = RANDOM.nextInt(list.size());

            return list.get(randomIndex);
        } else {
            throw new RuntimeException("List has no elements");
        }
    }

}
