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
    private Locale locale;


    public SimpleMessageResources(@Value("${bundle.base}") String name, @Value("${locale.set}") String locale) {
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setBasename("classpath:" + name);
        reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");
        this.ms = reloadableResourceBundleMessageSource;
        this.locale = Locale.forLanguageTag(locale);

    }

    @Override
    public String getI18nString(String value) {
        return ms.getMessage(value, null, this.locale);
    }
}
