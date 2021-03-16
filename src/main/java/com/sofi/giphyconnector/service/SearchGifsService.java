package com.sofi.giphyconnector.service;

import com.sofi.giphyconnector.Exceptions.GenericException;
import com.sofi.giphyconnector.Utility.LRUCache;
import com.sofi.giphyconnector.model.connectorResponse.SearchGifsResponse;
import com.sofi.giphyconnector.model.giphy.searchendpoint.GIPHYSearchResponse;
import com.sofi.giphyconnector.model.giphy.searchendpoint.GIPHYSearchResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class SearchGifsService {

    //CONSTANTS
    @Autowired
    private Environment environment;

    private String  api_key = environment.getProperty("GIPHY_API_KEY");
    private final int SEARCH_RESULT_ENTRY_LIMIT = 5;
    private final int CACHE_SIZE = 1000;
    private final String SEARCH_ENDPOINT_BASE_URL = "http://api.giphy.com/v1/gifs/search";
    private final Logger LOGGER = LoggerFactory.getLogger(SearchGifsService.class);
    private final RestTemplate restTemplate = com.sofi.giphyconnector.Utility.RestTemplate.RestTemplateWithTimeout();
    private LRUCache lruCache = new LRUCache(CACHE_SIZE);
    private UriComponentsBuilder builder;

    @Autowired
    public SearchGifsService () {
    }

    /**
     * @param searchQuery
     * @return TODO :
     * Add retry logic
     * better error handling
     */
    public SearchGifsResponse queryGiphySearchAPI(String searchQuery) throws GenericException {
        /**
         *
         * To optimize the latency, I have added caching logic to dedupe search calls to the GIPHY api for the keywords that are most frequently used.
         * However, this logic will only work under the assumption that the results for each search query are constant and will not change over time .
         * Since we are using an in-memory cache, this caching will be invalidated every time the service reboots.
         * As a long term solution we should configure a memcached layer (local or shared) with an appropriate cache eviction / time to live polic.
         */

        LOGGER.info("Searching for keyword : " + searchQuery);

        if (lruCache.containsKey(searchQuery)) {
            LOGGER.info(" Returning results from cache");
            return lruCache.get(searchQuery);
        } else {
            LOGGER.info("Querying GIPHY API");
            try {

                //BUILD THE URL
                builder = UriComponentsBuilder.fromHttpUrl(SEARCH_ENDPOINT_BASE_URL)
                        .queryParam("api_key", api_key)
                        .queryParam("limit", SEARCH_RESULT_ENTRY_LIMIT)
                        .queryParam("q", searchQuery);

                //ADD HEADERS
                HttpHeaders headers = new HttpHeaders();
                headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
                HttpEntity<?> entity = new HttpEntity<>(headers);

                //REQUEST PAYLOAD
                ResponseEntity<GIPHYSearchResponse> response = restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        entity,
                        GIPHYSearchResponse.class);

                if (response.getStatusCode() != HttpStatus.OK) {
                    //TODO : Add retry logic here.
                    throw new GenericException("Failed to process request. Error when communicating with Giphy API + "
                            + response.getStatusCode().toString());
                }

                try {
                    GIPHYSearchResponse responseBody = response.getBody();
                    List<GIPHYSearchResponseData> gifs = responseBody.getData();
                    SearchGifsResponse responseDTO = this.constructResponsePayload(gifs);

                    lruCache.put(searchQuery, responseDTO);
                    return responseDTO;
                } catch (Exception ex) {
                    LOGGER.error("Error when parsing Response payload : " + ex.getMessage(), ex);
                    throw new GenericException("Something went wrong. We cannot process your request right now.");
                }

            } catch (GenericException ex) {
                LOGGER.error("Error when parsing Response payload : " + ex.getMessage(), ex);
                throw ex;
            } catch (ResourceAccessException ex) {
                LOGGER.error("Timeout when calling GIPHY API", ex);
                throw ex;
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                throw e;
            }
        }
    }

    private SearchGifsResponse constructResponsePayload(List<GIPHYSearchResponseData> gifs) {
        if (gifs == null || gifs.size() < 5) {
            return new SearchGifsResponse(0);
        }
        SearchGifsResponse result = new SearchGifsResponse(SEARCH_RESULT_ENTRY_LIMIT);

        for (int i = 0; i < SEARCH_RESULT_ENTRY_LIMIT; i++) {
            result.addDataEntry(gifs.get(i), i);
        }
        return result;
    }
}
