package ru.effective_mobile;

import redis.clients.jedis.Jedis;
import ru.effective_mobile.exception.PassedNullException;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RedisMap implements Map<String, String> {

    @Override
    public int size() {
        try (Jedis jedis = ConnectorRedis.getResources()) {
            return (int) jedis.dbSize();
        }
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        keyIsNotNull(key);
        try (Jedis jedis = ConnectorRedis.getResources()) {
            return jedis.exists(key.toString());
        }
    }

    @Override
    public boolean containsValue(Object value) {
        valueIsNotNull(value);
        return values().contains(value.toString());
    }

    @Override
    public String get(Object key) {
        keyIsNotNull(key);
        try (Jedis jedis = ConnectorRedis.getResources()) {
            return jedis.get(key.toString());
        }
    }

    @Override
    public String put(String key, String value) {
        keyIsNotNull(key);
        valueIsNotNull(value);
        try (Jedis jedis = ConnectorRedis.getResources()) {
            jedis.set(key, value);
            return value;
        }
    }

    @Override
    public String remove(Object key) {
        String element = get(key);
        try (Jedis jedis = ConnectorRedis.getResources()) {
            jedis.del(key.toString());
            return element;
        }
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        try (Jedis jedis = ConnectorRedis.getResources()) {
            m.forEach(jedis::set);
        }
    }

    @Override
    public void clear() {
        try (Jedis jedis = ConnectorRedis.getResources()) {
            jedis.flushAll();
        }
    }

    @Override
    public Set<String> keySet() {
        try (Jedis jedis = ConnectorRedis.getResources()) {
            return jedis.keys("*");
        }
    }

    @Override
    public Collection<String> values() {
        return keySet()
                .stream()
                .map(this::get)
                .collect(Collectors.toList());
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return keySet()
                .stream()
                .map(key -> new AbstractMap.SimpleEntry<>(key, get(key)))
                .collect(Collectors.toSet());
    }

    private void keyIsNotNull(Object key) {
        if (key == null) throw new PassedNullException();
    }

    private void valueIsNotNull(Object value) {
        if (value == null) throw new PassedNullException();
    }
}