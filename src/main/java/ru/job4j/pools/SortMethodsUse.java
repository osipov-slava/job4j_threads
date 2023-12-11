package ru.job4j.pools;

public class SortMethodsUse {
    public static void main(String[] args) {
        int count = 10000000;
        var intArr = new int[count];
        for (int i = 0; i < count; i++) {
            intArr[i] = (int) (Math.random() * 10000);
        }
        long start = System.currentTimeMillis();
        MergeSort.sort(intArr);
        System.out.println("MergeSort.sort() ms: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        ParallelMergeSort.sort(intArr);
        System.out.println("ParallelMergeSort ms: " + (System.currentTimeMillis() - start));
    }
}
