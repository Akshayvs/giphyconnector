package com.sofi.giphyconnector.model.giphy.searchendpoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GIPHYSearchResponse {
    /**
     * Data Transfer Object for holding the success result returned by the GIPHY gif search v1 api endpoint.
     * Dev Doc : https://developers.giphy.com/docs/api/endpoint#search
     */
    private GIPHYSearchResponseMeta meta;
    private List<GIPHYSearchResponseData> data;


    public List<GIPHYSearchResponseData> getData() {
        return data;
    }

    public void setData(List<GIPHYSearchResponseData> data) {
        this.data = data;
    }

    public GIPHYSearchResponseMeta getMeta() {
        return meta;
    }

    public void setMeta(GIPHYSearchResponseMeta meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {

        return "Quote{" +
                "data='" + data + '\'' +
                ", meta=" + meta +
                '}';
    }
}
