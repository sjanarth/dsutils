package com.sjanarth.dsutils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/*
 * Leetcode -- https://leetcode.com/explore/interview/card/google/65/design-4/3090/
 * 
 * Design and implement a data structure for Least Recently Used (LRU) cache. 
 * 
 * It should support the following operations: get and put.
 * 
 * get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
 * put(key, value) - Set or insert the value if the key is not already present. 
 * 		When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.
 * 
 * The cache is initialized with a positive capacity.
 * 
 * Follow up
 * Could you do both operations in O(1) time complexity?
 * 
 * Example:
 * 
 * LRUCache cache = new LRUCache( 2 );
 * cache.put(1, 1);
 * cache.put(2, 2);
 * cache.get(1);       // returns 1
 * cache.put(3, 3);    // evicts key 2
 * cache.get(2);       // returns -1 (not found)
 * cache.put(4, 4);    // evicts key 1
 * cache.get(1);       // returns -1 (not found)
 * cache.get(3);       // returns 3
 * cache.get(4);       // returns 4
 * 
 */
public class LRUCache<K, V>
{
	public static void main(String[] args) {
		LRUCache<Integer,Integer> cache = new LRUCache<> ( 2 /* capacity */ );
		cache.put(1, 1);
		cache.put(2, 2);
		cache.get(1);       // returns 1
		cache.put(3, 3);    // evicts key 2
		cache.get(2);       // returns -1 (not found)
		cache.put(4, 4);    // evicts key 1
		cache.get(1);       // returns -1 (not found)
		cache.get(3);       // returns 3
		cache.get(4);       // returns 4		
	}

    public LRUCache(int c) {
        capacity = c;
        map = new HashMap<>();
        list = new LinkedList<>();
    }
    
    public V get(K key) {
        sop("-> get, "+key+", "+list.toString()+", "+map.toString());
        V value = null;
        if (map.containsKey(key))   {
            value = map.get(key);
            remove(key);
            addHead(key, value);
        }
        sop("<- get, "+key+", "+list.toString()+", "+map.toString()+", returning "+value);
        return value;
    }
    
    public void put(K key, V value) {
        sop("-> put, "+key+", "+list.toString()+", "+map.toString());
        if (map.containsKey(key))   {
            remove(key);
            addHead(key, value);
        } else {
            addHead(key, value);
        }
        sop("<- put, "+key+", "+list.toString()+", "+map.toString());
    }
    
    private void remove (K key)   {
        sop("    Removing "+key);
        list.remove(key);
        map.remove(key);
    }
    
    private void addHead(K key, V value) {
        sop("    Adding at head "+key);
        if (map.containsKey(key)) {
            remove(key);
        } else if (list.size() == capacity) {
            remove(list.getLast());
        }
        list.addFirst(key);
        map.put(key, value);
    }
    
    private void sop(String s) {
	    System.out.println(s);
    }
    
    private int capacity = 0;
    private LinkedList<K> list;
    private Map<K,V> map;
}