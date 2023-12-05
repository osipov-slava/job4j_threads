package ru.job4j.pools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.job4j.pool.User;

import java.util.Arrays;

class SearchIndexTest {
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 9, 10, 11, 19})
    void whenParametrizedPositive(int position) {
        int count = 20;
        var arr = new String[count];
        Arrays.fill(arr, "other objects");
        arr[position] = "main object";
        Assertions.assertEquals(position, SearchIndex.search(arr, arr[position]));
    }

    @Test
    void whenOtherObjectAndNegative() {
        int count = 20;
        var arrUser = new User[count];
        Arrays.fill(arrUser, new User("User1", ""));
        Assertions.assertEquals(-1, SearchIndex.search(arrUser, new User("User2", "")));
    }

    @Test
    void whenLineSearch() {
        int count = 10;
        var arr = new String[count];
        Arrays.fill(arr, "other objects");
        arr[9] = "main object";
        Assertions.assertEquals(9, SearchIndex.search(arr, arr[9]));
    }
}
