package com.hurynovich.api_tester.utils;

public class ObjectUtils {

    private ObjectUtils() {

    }

    public static class EqualsChecker {

        private boolean result = true;

        private EqualsChecker() {

        }

        public static EqualsChecker getInstance() {
            return new EqualsChecker();
        }

        public EqualsChecker with(final Object o1, final Object o2) {
            boolean equal;
            if (o1 == null) {
                equal = (o2 == null);
            } else {
                equal = (o1.equals(o2));
            }

            if (!equal) {
                this.result = false;
            }

            return this;
        }

        public boolean check() {
            return result;
        }

    }

    public static class HashCodeCalculator {

        private static final int SEED = 31;

        private int result = 13;

        private HashCodeCalculator() {

        }

        public static HashCodeCalculator getInstance() {
            return new HashCodeCalculator();
        }

        public HashCodeCalculator with(final Object o) {
            final int hashCode;
            if (o == null) {
                hashCode = 0;
            } else {
                hashCode = o.hashCode();
            }

            result = result * SEED + hashCode;

            return this;
        }

        public int calculate() {
            return result;
        }

    }

}
