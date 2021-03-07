package com.sofi.giphyconnector.DataTransferObjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Data Transfer Object for holding the search result metadata
 * Dev Doc : https://developers.giphy.com/docs/api/endpoint#search
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultMetadataDTO {

    private String status;
    private String response_id;

    public SearchResultMetadataDTO() {

    }

    @Override
    public String toString() {
        return "SearchResultMetadataDAO{" +
                "status=" + status +
                "response_id=" + response_id +
                '}';
    }

    // GETTERS AND SETTERS
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponse_id() {
        return response_id;
    }

    public void setResponse_id(String response_id) {
        this.response_id = response_id;
    }
}
