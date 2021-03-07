package com.sofi.giphyconnector.Utility;

public class InputValidator {

    private int MAX_QUERY_LENGTH = 30;

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
            return false;
        }

        // String can contain only ASCII english letters
        char[] charArray = searchQuery.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];
            if (! Character.isAlphabetic(ch)) return  false;
        }

        return true;
    }
}
