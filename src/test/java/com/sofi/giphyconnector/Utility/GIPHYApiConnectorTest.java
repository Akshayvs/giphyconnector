package com.sofi.giphyconnector.Utility;

import org.junit.jupiter.api.Test;

class GIPHYApiConnectorTest {
    @Test
    public void testSearchFlow() {

        GIPHYApiConnector conenctor = new GIPHYApiConnector();
        conenctor.queryGiphySearchAPI("cheeseburger");
    }
}