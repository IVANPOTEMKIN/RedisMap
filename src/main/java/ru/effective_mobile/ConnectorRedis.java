package ru.effective_mobile;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import ru.effective_mobile.exception.ClassInstantiationException;

public final class ConnectorRedis {

    private static volatile JedisPool instance;

    private ConnectorRedis() {
        throw new ClassInstantiationException();
    }

    public static JedisPool getInstance() {
        if (instance == null) {
            synchronized (ConnectorRedis.class) {
                if (instance == null) {
                    instance = new JedisPool("localhost", 6379);
                }
            }
        }
        return instance;
    }

    public static Jedis getResources() {
        return getInstance().getResource();
    }
}