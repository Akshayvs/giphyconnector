package com.sofi.giphyconnector.DataTransferObjects;

public class SearchResultResponseDTO {

    /**
     * Data Transfer object for constructing the API response payload returned by GIPHYConnector svc.
     * @return
     */
    public SearchResultEntryDTO[] getData() {
        return data;
    }

    public void setData(SearchResultEntryDTO[] data) {
        this.data = data;
    }

    private SearchResultEntryDTO[] data;
    public SearchResultResponseDTO(int size) {
        this.data = new SearchResultEntryDTO[size];
    }

    public void addDataEntry(SearchResultEntryDTO entry, int index) {
        this.data[index] = entry;
    }
}
