package com.sofi.giphyconnector.DataTransferObjects;

/**
 * Data Transfer object for constructing the API response payload returned by GIPHYConnector svc.
 *
 * @return
 */
public class SearchResultResponseDTO {

    private SearchResultEntryDTO[] data;

    public SearchResultResponseDTO(int size) {
        this.data = new SearchResultEntryDTO[size];
    }

    public SearchResultEntryDTO[] getData() {
        return data;
    }

    public void setData(SearchResultEntryDTO[] data) {
        this.data = data;
    }

    public void addDataEntry(SearchResultEntryDTO entry, int index) {
        this.data[index] = entry;
    }
}
