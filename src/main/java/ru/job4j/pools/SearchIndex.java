package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SearchIndex<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T obj;
    private final int from;
    private final int to;

    public SearchIndex(T[] array, T obj, int from, int to) {
        this.array = array;
        this.obj = obj;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return lineSearchIndex();
        }
        int middle = (to + from) / 2;
        SearchIndex<T> leftSearchIndex = new SearchIndex<>(array, obj, from, middle);
        SearchIndex<T> rightSearchIndex = new SearchIndex<>(array, obj, middle + 1, to);
        leftSearchIndex.fork();
        rightSearchIndex.fork();
        return Math.max(rightSearchIndex.join(), leftSearchIndex.join());
    }

    private Integer lineSearchIndex() {
        for (int i = from; i <= to; i++) {
            if (array[i].equals(obj)) {
                return i;
            }
        }
        return -1;
    }

    public static Integer search(Object[] array, Object obj) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new SearchIndex<>(array, obj, 0, array.length - 1));
    }
}
