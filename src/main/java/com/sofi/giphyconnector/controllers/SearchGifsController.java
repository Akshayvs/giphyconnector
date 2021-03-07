package com.sofi.giphyconnector.controllers;

import com.sofi.giphyconnector.DataTransferObjects.SearchResultResponseDTO;
import com.sofi.giphyconnector.Utility.GIPHYApiConnector;
import com.sofi.giphyconnector.Utility.InputValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchGifsController {

    /**
     * This class handles the gif search endpoint for the connector.
     * Handles the input validation and error code paths and returns the appropriate response codes
     */

    private static GIPHYApiConnector apiConnector = new GIPHYApiConnector();
    private static InputValidator inputValidator = new InputValidator();
    private static final String BAD_REQUEST_ERROR_MESSAGE = "Malformed input. Please check your input string";

    @GetMapping(value = "/search/{searchKey}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SearchResultResponseDTO> searchGifs(@PathVariable("searchKey") String searchKey) {

        if(! inputValidator.isValid(searchKey)) {
            //HTTP 400 BAD REQUEST
            return new ResponseEntity(BAD_REQUEST_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        try {
            SearchResultResponseDTO responseData = apiConnector.queryGiphySearchAPI(searchKey);
            ResponseEntity<SearchResultResponseDTO> entity = new ResponseEntity(responseData, HttpStatus.OK);
            return entity;

        } catch (Exception ex) {
            //todo : Return different HTTP RESPONSE TYPES for each of the error scenarios
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}