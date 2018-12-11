package ru.otus.spring02.service;

public interface MessageResource {
    String getI18nString(String value);
    void setLanguageTag(String languageTag);
}
