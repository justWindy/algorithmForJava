package org.ray.algorithm.array;

public class ArrayUtils {

    public static double maxValue(double[] array) {
        if (array != null && array.length > 0) {
            double max = array[0];
            for (int i = 1; i < array.length; i++) {
                if (array[i] > max) {
                    max = array[i];
                }
            }

            return max;
        }

        return Double.NaN;
    }

    public static double averageValue(double[] array) {
        if (array != null & array.length > 0) {
            int length = array.length;
            double sum = 0.0;
            for (int i = 0; i < length; i++) {
                sum += array[i];
            }

            return sum / length;
        }

        return Double.NaN;
    }

}
