package com.sofi.giphyconnector.controllers;

import com.sofi.giphyconnector.DataTransferObjects.SearchResultResponseDTO;
import com.sofi.giphyconnector.Exceptions.GenericException;
import com.sofi.giphyconnector.Utility.GIPHYApiConnector;
import com.sofi.giphyconnector.Utility.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

/**
 * This class handles the gif search endpoint for the connector.
 * Handles the input validation and error code paths and returns the appropriate response codes
 */
@RestController
public class SearchGifsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchGifsController.class);
    private static final String BAD_REQUEST_ERROR_MESSAGE = "Malformed input. Please check your input string";
    private static final GIPHYApiConnector apiConnector = new GIPHYApiConnector();
    private static final InputValidator inputValidator = new InputValidator();

    @GetMapping(value = "/search/{searchKey}", produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<SearchResultResponseDTO> searchGifs(@PathVariable("searchKey") String searchKey) {

        LOGGER.info("Executing /Search endpoint");

        if (!inputValidator.isValid(searchKey)) {
            LOGGER.warn("Search key failed input validation");
            //HTTP 400 BAD REQUEST
            return new ResponseEntity(BAD_REQUEST_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        try {
            SearchResultResponseDTO responseData = apiConnector.queryGiphySearchAPI(searchKey);
            ResponseEntity<SearchResultResponseDTO> entity = new ResponseEntity(responseData, HttpStatus.OK);
            LOGGER.info("Search query successful for searchKey : " + searchKey);
            return entity;
        }
        catch (ResourceAccessException ex) {
            return new ResponseEntity("Connection Timeout when calling GIPHY API.", HttpStatus.REQUEST_TIMEOUT);
        }
        catch (GenericException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}