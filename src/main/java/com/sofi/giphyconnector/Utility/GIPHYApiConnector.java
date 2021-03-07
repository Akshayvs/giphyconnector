package com.sofi.giphyconnector.Utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofi.giphyconnector.DataTransferObjects.SearchResultDTO;
import com.sofi.giphyconnector.DataTransferObjects.SearchResultEntryDTO;
import com.sofi.giphyconnector.DataTransferObjects.SearchResultResponseDTO;
import com.sofi.giphyconnector.Exceptions.GenericException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class GIPHYApiConnector {

    //CONSTANTS
    private static final String TEST_ENV_API_KEY = "nPcu7YTrpMkRwN3aMWS63gJWEm4bKKI0";
    private static final int SEARCH_RESULT_ENTRY_LIMIT = 5;
    private static final int CACHE_SIZE = 1000;

    private static final String SEARCH_ENDPOINT_BASE_URL = "http://api.giphy.com/v1/gifs/search";
    private static final Logger LOGGER = LoggerFactory.getLogger(GIPHYApiConnector.class);
    private static final RestTemplate restTemplate = com.sofi.giphyconnector.Utility.RestTemplate.RestTemplateWithTimeout();
    private static LRUCache lruCache = new LRUCache(CACHE_SIZE);

    /**
     * @param searchQuery
     * @return TODO :
     * Add retry logic
     * better error handling
     */
    public SearchResultResponseDTO queryGiphySearchAPI(String searchQuery) throws GenericException {
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
                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(SEARCH_ENDPOINT_BASE_URL)
                        .queryParam("api_key", TEST_ENV_API_KEY)
                        .queryParam("limit", SEARCH_RESULT_ENTRY_LIMIT)
                        .queryParam("q", searchQuery);


                //ADD HEADERS
                HttpHeaders headers = new HttpHeaders();
                headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
                HttpEntity<?> entity = new HttpEntity<>(headers);

                //REQUEST PAYLOAD
                ResponseEntity response = restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        entity,
                        String.class);

                if (response.getStatusCode() != HttpStatus.OK) {
                    //TODO : Add retry logic here.

                    throw new GenericException("Failed to process request. Error when communicating with Giphy API + "
                            + response.getStatusCode().toString());
                }

                ObjectMapper mapper = new ObjectMapper();

                try {
                    SearchResultDTO dao = mapper.readValue(response.getBody().toString(), SearchResultDTO.class);
                    List<SearchResultEntryDTO> gifs = dao.getData();
                    SearchResultResponseDTO responseDTO = this.constructResponsePayload(gifs);

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

    private SearchResultResponseDTO constructResponsePayload(List<SearchResultEntryDTO> gifs) {
        if (gifs == null || gifs.size() < 5) {
            return new SearchResultResponseDTO(0);
        }
        SearchResultResponseDTO result = new SearchResultResponseDTO(SEARCH_RESULT_ENTRY_LIMIT);

        for (int i = 0; i < SEARCH_RESULT_ENTRY_LIMIT; i++) {
            result.addDataEntry(gifs.get(i), i);
        }
        return result;
    }
}
