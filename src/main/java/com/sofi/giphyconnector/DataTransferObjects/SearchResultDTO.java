package com.sofi.giphyconnector.DataTransferObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultDTO {
    /**
     * Data Transfer Object for holding the success result returned by the GIPHY gif search v1 api endpoint.
     * Dev Doc : https://developers.giphy.com/docs/api/endpoint#search
     */
    private SearchResultMetadataDTO meta;
    private List<SearchResultEntryDTO> data;


    public List<SearchResultEntryDTO> getData() {
        return data;
    }

    public void setData(List<SearchResultEntryDTO> data) {
        this.data = data;
    }

    public SearchResultMetadataDTO getMeta() {
        return meta;
    }

    public void setMeta(SearchResultMetadataDTO meta) {
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
