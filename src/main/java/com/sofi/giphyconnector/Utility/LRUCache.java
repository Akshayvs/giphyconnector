package com.sofi.giphyconnector.Utility;

import com.sofi.giphyconnector.DataTransferObjects.SearchResultResponseDTO;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache extends LinkedHashMap<String, SearchResultResponseDTO> {
    private int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75F, true);
        this.capacity = capacity;
    }

    public boolean containsKey( String key) {
        return super.containsKey(key);
    }

    public SearchResultResponseDTO get(String key) {
        return super.getOrDefault(key, null);
    }

    public SearchResultResponseDTO put(String key, SearchResultResponseDTO value) {
        return super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, SearchResultResponseDTO> eldest) {
        return size() > capacity;
    }
}