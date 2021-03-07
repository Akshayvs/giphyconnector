package com.sofi.giphyconnector.Utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofi.giphyconnector.DataTransferObjects.SearchResultDTO;
import com.sofi.giphyconnector.DataTransferObjects.SearchResultEntryDTO;
import com.sofi.giphyconnector.DataTransferObjects.SearchResultResponseDTO;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class GIPHYApiConnector {

    //CONSTANTS
    private static final String TEST_ENV_API_KEY = "nPcu7YTrpMkRwN3aMWS63gJWEm4bKKI0";
    private static final int SEARCH_RESULT_ENTRY_LIMIT = 5;
    private static final String SEARCH_ENDPOINT_BASE_URL = "http://api.giphy.com/v1/gifs/search";

    public SearchResultResponseDTO queryGiphySearchAPI(String searchQuery) {

        // STEP 1 : GET THE RESPONSE

        /*
        todo

        add timeout logic

        handle different errors

        have different response types for different errors



         */

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(SEARCH_ENDPOINT_BASE_URL)
                .queryParam("api_key", TEST_ENV_API_KEY)
                .queryParam("limit", SEARCH_RESULT_ENTRY_LIMIT)
                .queryParam("q", searchQuery);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);


        HttpStatus statusCode = response.getStatusCode();


        ObjectMapper mapper = new ObjectMapper();
        try {
            SearchResultDTO dao = mapper.readValue(response.getBody().toString(), SearchResultDTO.class);
            List<SearchResultEntryDTO> gifs = dao.getData();
            return this.constructResponsePayload(gifs);
        } catch (Exception e) {
            System.out.println(e);
        }

        // TODO : handle this code branch gracefully.
        return this.constructResponsePayload(null);
    }

    private SearchResultResponseDTO constructResponsePayload(List<SearchResultEntryDTO> gifs ) {
        if (gifs == null || gifs.size() < 5) {
            return new SearchResultResponseDTO(0);
        }
        SearchResultResponseDTO result = new SearchResultResponseDTO(SEARCH_RESULT_ENTRY_LIMIT);

        for(int i = 0; i < SEARCH_RESULT_ENTRY_LIMIT; i ++) {
            result.addDataEntry(gifs.get(i),i);
        }
        return result;
    }

}
