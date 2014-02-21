package de.cosh.gemlords;

import java.util.HashMap;

/**
 * Created by cosh on 06.02.14.
 */
public class LanguageManager {
    private static LanguageManager instance = null;

    private static final String LANGUAGE_FILE = "data/language.xml";
    private static final String DEFAULT_LANGUAGE = "en_UK";

    private HashMap<String, String> language = null;
    private String languageName = null;

    private LanguageManager() {
        language = new HashMap<String, String>();
        languageName = java.util.Locale.getDefault().toString();
    }

    public static LanguageManager getInstance() {
        if( instance == null )
            instance = new LanguageManager();
        return instance;
    }

}
