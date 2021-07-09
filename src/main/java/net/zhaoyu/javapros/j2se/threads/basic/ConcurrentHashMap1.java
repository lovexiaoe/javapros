package net.zhaoyu.javapros.j2se.threads.basic;


import java.io.ObjectStreamField;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;


public class ConcurrentHashMap1<K,V> extends AbstractMap<K,V> implements ConcurrentMap<K,V>, Serializable {

    /* ---------- 字段 ---------- */

    
    private static final int MAXIMUM_CAPACITY = 1 << 30;



    /* ---------- 构造方法 ---------- */
    public ConcurrentHashMap1() {
    }

    public ConcurrentHashMap1(int initialCapacity){
        if (initialCapacity < 0) {
            throw new IllegalArgumentException();
        }


    }



    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public boolean remove(Object key, Object value) {
        return false;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return false;
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return null;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {

    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {

    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return null;
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return null;
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return null;
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return null;
    }

    public V putIfAbsent(K key, V value) {
        return null;
    }

    public V replace(K key, V value) {
        return null;
    }

}
