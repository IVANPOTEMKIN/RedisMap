package ru.effective_mobile;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.effective_mobile.exception.PassedNullException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RedisMapTest {

    private final RedisMap redisMap = new RedisMap();

    @BeforeEach
    void setUp() {
        redisMap.put("k1", "v1");
        redisMap.put("k2", "v2");
        redisMap.put("k3", "v3");
    }

    @AfterEach
    void cleanUp() {
        redisMap.clear();
    }

    @Test
    void testSize() {
        assertEquals(3, redisMap.size());
    }

    @Test
    void testIsEmpty() {
        assertFalse(redisMap.isEmpty());
    }

    @Test
    void testContainsKey() {
        assertTrue(redisMap.containsKey("k1"));
        assertFalse(redisMap.containsKey("k4"));
    }

    @Test
    void testContainsKey_getPassedNullException() {
        assertThrows(PassedNullException.class,
                () -> redisMap.containsKey(null));
    }

    @Test
    void testContainsValue() {
        assertTrue(redisMap.containsValue("v1"));
        assertFalse(redisMap.containsValue("v4"));
    }

    @Test
    void testContainsValue_getPassedNullException() {
        assertThrows(PassedNullException.class,
                () -> redisMap.containsValue(null));
    }

    @Test
    void testGet() {
        String expected = "v1";
        String actual = redisMap.get("k1");

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void testGet_null() {
        assertNull(redisMap.get("k4"));
    }

    @Test
    void testGet_getPassedNullException() {
        assertThrows(PassedNullException.class,
                () -> redisMap.get(null));
    }

    @Test
    void testPut() {
        String expected = "v4";
        String actual = redisMap.put("k4", "v4");

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void testPut_getPassedNullException() {
        assertThrows(PassedNullException.class,
                () -> redisMap.put(null, "v4"));
        assertThrows(PassedNullException.class,
                () -> redisMap.put("k4", null));
    }

    @Test
    void testRemove() {
        String expected = "v1";
        String actual = redisMap.remove("k1");

        assertNotNull(actual);
        assertEquals(expected, actual);
        assertEquals(2, redisMap.size());
        assertFalse(redisMap.containsKey("k1"));
        assertFalse(redisMap.containsValue("v1"));
    }

    @Test
    void testKeySet() {
        Set<String> expected = Set.of("k1", "k2", "k3");
        Set<String> actual = redisMap.keySet();
        assertEquals(expected, actual);
    }

    @Test
    void testValues() {
        Collection<String> expected = List.of("v1", "v2", "v3");
        Collection<String> actual = redisMap.values();
        assertEquals(expected, actual);
    }

    @Test
    void testEntrySet() {
        Set<Map.Entry<String, String>> expected = Set.of(
                new AbstractMap.SimpleEntry<>("k1", "v1"),
                new AbstractMap.SimpleEntry<>("k2", "v2"),
                new AbstractMap.SimpleEntry<>("k3", "v3"));

        Set<Map.Entry<String, String>> actual = redisMap.entrySet();
        assertEquals(expected, actual);
    }
}