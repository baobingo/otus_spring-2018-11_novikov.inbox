package ru.otus.spring02.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@PropertySource("classpath:application.properties")
@Service
public class SimpleMessageResources implements MessageResource {

    private MessageSource ms;
    @Value("${locale.set}")
    private String languageTag;


    public SimpleMessageResources(@Value("${bundle.base}") String name) {
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setBasename("classpath:" + name);
        reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");
        this.ms = reloadableResourceBundleMessageSource;
    }

    @Override
    public String getI18nString(String value) {
        return ms.getMessage(value, null, Locale.forLanguageTag(languageTag));
    }

    public void setLanguageTag(String languageTag) {
        this.languageTag = languageTag;
    }
}
