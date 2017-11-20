package org.ray.algorithm.array;

public class ArrayUtils {

    public static double maxOfArray(double[] array) {
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

    public static double averageOfArray(double[] array) {
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

    public static double[] copyArray(double[] array) {
        if (array != null && array.length > 0) {
            int length = array.length;
            double[] copy = new double[length];
            for (int i = 0; i < length; i++) {
                copy[i] = array[i];
            }

            return copy;
        }

        return null;
    }

    public static void reverseArray(double[] array) {
        if (array != null && array.length > 0) {
            int length = array.length;
            for (int i = 0; i < length / 2; i++) {
                double temp = array[i];
                array[i] = array[length - 1 - i];
                array[length - 1 - i] = temp;
            }
        }
    }

    public static double[][] matrixMultiply(double[][] arrayA, double[][] arrayB) {
        int length = arrayA.length;
        double[][] arrayC = new double[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                for (int k = 0; k < length; k++) {
                    arrayC[i][j] += arrayA[i][k] * arrayB[k][j];
                }
            }
        }

        return arrayC;
    }



}
