package com.oracleone.ducklib.model;

public enum Language {
    EN("[en]"),
    ES("[es]"),
    FR("[fr]"),
    PT("[pt]");

    private String language;

    Language(String language) {
        this.language = language;
    }

    public static Language fromString(String text) {
        for (Language language : Language.values()) {
            if (language.language.equalsIgnoreCase(text)) {
                return language;
            }
        }
        throw new IllegalArgumentException("Language not found: " + text);
    }

    public String getLanguage() {
        return language;
    }
}
