package com.sofi.giphyconnector.Utility;

import com.sofi.giphyconnector.DataTransferObjects.SearchResultResponseDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GIPHYApiConnectorTest {
    @Test
    public void testSearchFlow() {
        GIPHYApiConnector conenctor = new GIPHYApiConnector();

        try {
            SearchResultResponseDTO response = conenctor.queryGiphySearchAPI("cheeseburger");
            assertNotNull(response);

        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }
}