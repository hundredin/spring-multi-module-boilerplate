package com.spring.boilerplate.common.util;

import org.hashids.Hashids;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class Hasher {
    AtomicLong counter = new AtomicLong();
    Hashids hashids;

    public Hasher(int length) {
        hashids = new Hashids(UUID.randomUUID().toString(), length);
    }

    public String get() {
        return hashids.encode(counter.getAndIncrement());
    }

    public static String getHashId() {
        Hashids hashids = new Hashids(UUID.randomUUID().toString(), 8);
        AtomicLong counter = new AtomicLong();
        return hashids.encode(counter.getAndIncrement());
    }
}
