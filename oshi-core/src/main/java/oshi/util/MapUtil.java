/**
 * OSHI (https://github.com/oshi/oshi)
 *
 * Copyright (c) 2010 - 2019 The OSHI Project Team:
 * https://github.com/oshi/oshi/graphs/contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package oshi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Allow Java 8 features on Java 7 HashMaps
 *
 * @author widdis[at]gmail[dot]com
 */
public class MapUtil {

    private static final Object mapLock = new Object();

    private MapUtil() {
    }

    /**
     * Returns the value to which the specified key is mapped, or defaultValue
     * if this map contains no mapping for the key.
     *
     * @param <K>
     *            The map key type
     * @param <V>
     *            The map value type
     * @param map
     *            the map to use
     * @param key
     *            the key whose associated value is to be returned
     * @param defaultValue
     *            the default mapping of the key
     * @return the value to which the specified key is mapped, or defaultValue
     *         if this map contains no mapping for the key
     */
    public static <K, V> V getOrDefault(Map<K, V> map, K key, V defaultValue) {
        synchronized (mapLock) {
            V value = map.get(key);
            if (value != null) {
                return value;
            }
            return defaultValue;
        }
    }

    /**
     * If the specified key is not already associated with a value (or is mapped
     * to null) associates it with the given value and returns null, else
     * returns the current value.
     *
     * @param <K>
     *            The map key type
     * @param <V>
     *            The map value type
     * @param map
     *            the map to use
     * @param key
     *            key with which the specified value is to be associated
     * @param value
     *            value to be associated with the specified key
     * @return the previous value associated with the specified key, or null if
     *         there was no mapping for the key. (A null return can also
     *         indicate that the map previously associated null with the key, if
     *         the implementation supports null values.)
     */
    public static <K, V> V putIfAbsent(Map<K, V> map, K key, V value) {
        synchronized (mapLock) {
            V existingValue = map.get(key);
            if (existingValue != null) {
                return existingValue;
            }
            map.put(key, value);
            return null;
        }
    }

    /**
     * If the specified key is not already associated with a value (or is mapped
     * to null) associates it with a new List and returns it, else returns the
     * current value.
     *
     * @param <K>
     *            The map key type
     * @param <V>
     *            The map value type in the list
     * @param map
     *            the map to use
     * @param key
     *            key with which the specified value is to be associated
     * @return the previous value associated with the specified key, or a new
     *         list
     */
    public static <K, V> List<V> createNewListIfAbsent(Map<K, List<V>> map, K key) {
        synchronized (mapLock) {
            List<V> value = map.get(key);
            if (value != null) {
                return value;
            }
            value = new ArrayList<>();
            map.put(key, value);
            return value;
        }
    }

}
