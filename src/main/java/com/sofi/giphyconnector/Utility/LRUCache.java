package com.sofi.giphyconnector.Utility;

import com.sofi.giphyconnector.model.connectorResponse.SearchGifsResponse;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache extends LinkedHashMap<String, SearchGifsResponse> {
    /**
     * This is a local implementation of a in-memory cache which uses the 'Least Recently Used' principal for cache eviction
     * In the context of our application, this wrapper will store search-key to -> search Result mapping.
     */

    private final int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75F, true);
        this.capacity = capacity;
    }

    public boolean containsKey(String key) {
        return super.containsKey(key);
    }

    public SearchGifsResponse get(String key) {
        return super.getOrDefault(key, null);
    }

    public SearchGifsResponse put(String key, SearchGifsResponse value) {
        return super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, SearchGifsResponse> eldest) {
        return size() > capacity;
    }
}