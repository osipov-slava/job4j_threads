package ru.job4j.pools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.job4j.pool.User;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

class SearchIndexTest {
    @Test
    void whenParametrizedPositiveAndNegative() {
        int count = 999;
        var arr = new String[count];
        Arrays.fill(arr, "other objects");
        arr[count / 3] = "main object";
        var searchIndex = new SearchIndex<>(arr, arr[count / 3], 0, count);
        var forkJoinPool = new ForkJoinPool();
        Assertions.assertEquals(count / 3, forkJoinPool.invoke(searchIndex));

        var arrUser = new User[count];
        Arrays.fill(arrUser, new User("User1", ""));
        var searchIndexUser = new SearchIndex<>(arrUser, new User("User2", ""), 0, count);
        Assertions.assertEquals(-1, forkJoinPool.invoke(searchIndexUser));
    }

    @Test
    void whenLineSearch() {
        int count = 9;
        var arr = new String[count];
        Arrays.fill(arr, "other objects");
        arr[8] = "main object";
        var searchIndex = new SearchIndex<>(arr, arr[8], 0, count);
        var forkJoinPool = new ForkJoinPool();
        Assertions.assertEquals(8, forkJoinPool.invoke(searchIndex));
    }
}
