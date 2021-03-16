package com.sofi.giphyconnector.api_rest;

import com.sofi.giphyconnector.Exceptions.GenericException;
import com.sofi.giphyconnector.Utility.InputValidator;
import com.sofi.giphyconnector.model.connectorResponse.SearchGifsResponse;
import com.sofi.giphyconnector.service.SearchGifsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

/**
 * This class handles the gif search endpoint for the connector.
 * Handles the input validation and error code paths and returns the appropriate response codes
 */
@RestController
@RequestMapping("/api/v1")
public class SearchGifsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchGifsController.class);
    private static final String BAD_REQUEST_ERROR_MESSAGE = "Malformed input. Please check your input string";
    private static final SearchGifsService apiConnector = new SearchGifsService();
    private static final InputValidator inputValidator = new InputValidator();

    @GetMapping(value = "/search/{searchKey}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SearchGifsResponse> searchGifs(@PathVariable("searchKey") String searchKey) {

        LOGGER.info("Executing /Search endpoint");

        if (!inputValidator.isValid(searchKey)) {
            LOGGER.warn("Search key failed input validation");
            //HTTP 400 BAD REQUEST
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BAD_REQUEST_ERROR_MESSAGE);
        }

        try {
            SearchGifsResponse responseData = apiConnector.queryGiphySearchAPI(searchKey);
            ResponseEntity<SearchGifsResponse> responseEntity = new ResponseEntity<>(responseData, HttpStatus.OK);

            LOGGER.info("Search query successful for searchKey : " + searchKey);
            return responseEntity;

        } catch (ResourceAccessException ex) {
            return new ResponseEntity("Connection timeout when calling GIPHY API.", HttpStatus.REQUEST_TIMEOUT);
        } catch (GenericException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}