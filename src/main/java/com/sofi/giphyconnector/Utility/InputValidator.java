package com.sofi.giphyconnector.Utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class InputValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputValidator.class);
    private static final int MAX_QUERY_LENGTH = 30;

    /**
     * @param searchQuery - Keyword for searching Gifs
     * @return - true if the searchQuery is valid and does not expose us to injection attacks.
     */
    public static boolean isValid(String searchQuery) {

        // String cannot be null/ empty
        if (searchQuery == null || searchQuery.isEmpty() ||
                searchQuery.length() > MAX_QUERY_LENGTH
        ) {
            LOGGER.warn("Input Validation Failed : input failed the length check");
            return false;
        }

        // String can contain only ASCII english letters
        char[] charArray = searchQuery.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];
            if (!Character.isAlphabetic(ch)) {
                LOGGER.warn("Input validation Failed : Non alphabetic character detected");
                return false;
            }
        }
        LOGGER.info("Input validation successful");
        return true;
    }
}
