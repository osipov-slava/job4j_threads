package ru.job4j.pools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static ru.job4j.pools.RowColSum.asyncSum;
import static ru.job4j.pools.RowColSum.sum;

class RowColSumTest {
    private static final int SIZE = 5;

    @Test
    void whenSum() {
        Assertions.assertArrayEquals(getSumsArrExpected(SIZE), sum(getMatrix(SIZE)));
    }

    @Test
    void whenAsyncSum() throws Exception {
        Assertions.assertArrayEquals(getSumsArrExpected(SIZE), asyncSum(getMatrix(SIZE)));
    }

    private static int[][] getMatrix(int n) {
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(matrix[i], 1);
        }
        return matrix;
    }

    private static Sums[] getSumsArrExpected(int n) {
        Sums[] expected = new Sums[n];
        for (int i = 0; i < n; i++) {
            expected[i] = new Sums(n, n);
        }
        return expected;
    }
}
