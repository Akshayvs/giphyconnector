package com.sofi.giphyconnector.model.giphy.searchendpoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GIPHYSearchResponseData {
    /**
     * Data Transfer Object for storing each gif entry within the SearchResult DTO.
     * Dev Doc : https://developers.giphy.com/docs/api/endpoint#search
     */
    private String id;
    private String url;

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", url=" + url +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
