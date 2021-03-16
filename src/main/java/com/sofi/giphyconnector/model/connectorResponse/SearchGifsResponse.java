package com.sofi.giphyconnector.model.connectorResponse;

import com.sofi.giphyconnector.model.giphy.searchendpoint.GIPHYSearchResponseData;

/**
 * Data Transfer object for constructing the API response payload returned by GIPHYConnector svc.
 *
 * @return
 */
public class SearchGifsResponse {

    private GIPHYSearchResponseData[] data;

    public SearchGifsResponse(int size) {
        this.data = new GIPHYSearchResponseData[size];
    }

    public GIPHYSearchResponseData[] getData() {
        return data;
    }

    public void setData(GIPHYSearchResponseData[] data) {
        this.data = data;
    }

    public void addDataEntry(GIPHYSearchResponseData entry, int index) {
        this.data[index] = entry;
    }
}
