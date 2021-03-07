package com.sofi.giphyconnector.Utility;

import com.sofi.giphyconnector.controllers.HealthCheckController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputValidator {

    private final int MAX_QUERY_LENGTH = 30;
    private static final Logger LOGGER = LoggerFactory.getLogger(InputValidator.class);
    /**
     *
     * @param searchQuery - Keyword for searching Gifs
     * @return - true if the searchQuery is valid and does not expose us to injection attacks.
     */
    public boolean isValid (String searchQuery) {

        // String cannot be null/ empty
        if(searchQuery == null || searchQuery.isEmpty() ||
                searchQuery.length() > MAX_QUERY_LENGTH
        ) {
            LOGGER.warn("Input Validation Failed : input failed the length check");
            return false;
        }

        // String can contain only ASCII english letters
        char[] charArray = searchQuery.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];
            if (! Character.isAlphabetic(ch)) {
                LOGGER.warn("Input validation Failed : Non alphabetic character detected");
                return  false;
            }
        }
        LOGGER.info("Input validation successful");
        return true;
    }
}
