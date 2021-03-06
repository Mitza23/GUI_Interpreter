package com.gui.gui.Domain.ADT;

import com.gui.gui.Domain.Clonable;

import java.util.Map;

public interface MyIDictionary<K, V> extends Clonable<MyIDictionary<K, V>> {
    V lookup(K key);

    void update(K key, V value);

    void put(K key, V value);

    boolean isDefined(K key);

    void remove(K key);

    Map<K, V> getContent();

//    MyIDictionary<K, V> clone();
}
