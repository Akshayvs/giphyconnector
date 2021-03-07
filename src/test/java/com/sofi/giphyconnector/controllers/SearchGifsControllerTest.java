package com.sofi.giphyconnector.controllers;

import com.sofi.giphyconnector.DataTransferObjects.SearchResultResponseDTO;
import com.sofi.giphyconnector.Utility.GIPHYApiConnector;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class SearchGifsControllerTest {

    @Test
    public void testHappyPath() {
        SearchGifsController searchEndpoint = new SearchGifsController();

        try {
            ResponseEntity response =  searchEndpoint.searchGifs("hamburger");
            assertNotNull(response);
            assertEquals(response.getStatusCode(), HttpStatus.OK);
        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }

    @Test
    public void testInputValidation() {
        SearchGifsController searchEndpoint = new SearchGifsController();

        try {
            ResponseEntity response =  searchEndpoint.searchGifs("wordWith1Number");
            assertNotNull(response);
            assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }

}