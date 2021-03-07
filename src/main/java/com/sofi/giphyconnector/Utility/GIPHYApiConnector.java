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
    private static final int CACHE_SIZE = 1000;
    private static LRUCache lruCache;

    public GIPHYApiConnector() {
        this.lruCache = new LRUCache(CACHE_SIZE);
    }
    public SearchResultResponseDTO queryGiphySearchAPI(String searchQuery) {
        /**
         *
         * To optimize the latency, I have added caching logic to dedupe search calls to the GIPHY api for the keywords that are most frequently used.
         * However, this logic will only work under the assumption that the results for each search query are constant and will not change over time .
         * Since we are using an in-memory cache, this caching will be invalidated every time the service reboots.
         * As a long term solution we should configure a memcached layer (local or shared) with an appropriate cache eviction / time to live polic.
         */

        if(lruCache.containsKey(searchQuery)) {
            return lruCache.get(searchQuery);
        }

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
            SearchResultResponseDTO responseDTO = this.constructResponsePayload(gifs);

            lruCache.put(searchQuery, responseDTO);

            return responseDTO;
        } catch (Exception e) {
            System.out.println(e);
        }

        // TODO : handle this code branch gracefully.

        SearchResultResponseDTO responseDTO = this.constructResponsePayload(null);
        lruCache.put(searchQuery, responseDTO);
        return responseDTO;
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
