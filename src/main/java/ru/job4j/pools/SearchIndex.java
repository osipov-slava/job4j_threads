package ru.job4j.pools;

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
        } else {
            int middle = (to + from) / 2;
            SearchIndex<T> leftSearchIndex = new SearchIndex<>(array, obj, from, middle);
            SearchIndex<T> rightSearchIndex = new SearchIndex<>(array, obj, middle, to);
            leftSearchIndex.fork();
            rightSearchIndex.fork();
            Integer index2 = rightSearchIndex.join();
            Integer index1 = leftSearchIndex.join();
            if (index1 >= 0) {
                return index1;
            } else if (index2 >= 0) {
                return index2;
            }
        }
        return -1;
    }

    private Integer lineSearchIndex() {
        for (int i = from; i < to; i++) {
            if (array[i].equals(obj)) {
                return i;
            }
        }
        return -1;
    }
}
