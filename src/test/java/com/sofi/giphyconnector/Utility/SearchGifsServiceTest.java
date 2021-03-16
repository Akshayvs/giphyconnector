package com.sofi.giphyconnector.Utility;

import com.sofi.giphyconnector.model.connectorResponse.SearchGifsResponse;
import com.sofi.giphyconnector.service.SearchGifsService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

class SearchGifsServiceTest {
    @Test
    public void testSearchFlow() {
        SearchGifsService conenctor = new SearchGifsService();

        try {
            SearchGifsResponse response = conenctor.queryGiphySearchAPI("cheeseburger");
            assertNotNull(response);

        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }
}