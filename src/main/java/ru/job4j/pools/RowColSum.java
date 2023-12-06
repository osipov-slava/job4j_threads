package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;

public class RowColSum {
    public static Sums[] sum(int[][] matrix) {
        Sums[] sumsArr = new Sums[matrix.length];
        for (int rowCol = 0; rowCol < matrix.length; rowCol++) {
            Sums sum = new Sums();
            for (int i = 0; i < matrix.length; i++) {
                sum.setColSum(sum.getColSum() + matrix[rowCol][i]);
                sum.setRowSum(sum.getRowSum() + matrix[i][rowCol]);
            }
            sumsArr[rowCol] = sum;
        }
        return sumsArr;
    }

    public static Sums[] asyncSum(int[][] matrix) throws Exception {
        Sums[] sumsArr = new Sums[matrix.length];
        for (int rowCol = 0; rowCol < matrix.length; rowCol++) {
            sumsArr[rowCol] = getTask(matrix, rowCol).get();
        }
        return sumsArr;
    }

    public static CompletableFuture<Sums> getTask(int[][] matrix, int rowCol) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sum = new Sums();
            for (int i = 0; i < matrix.length; i++) {
                sum.setColSum(sum.getColSum() + matrix[rowCol][i]);
                sum.setRowSum(sum.getRowSum() + matrix[i][rowCol]);
            }
            return sum;
        });
    }
}
