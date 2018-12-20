package ru.otus.spring04.service;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring04.configs.YamlProps;

import java.util.Locale;

@Service
public class SimpleMessageResources implements MessageResource {

    private MessageSource ms;
    private Locale locale;


    public SimpleMessageResources(YamlProps props) {
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setBasename("classpath:" + props.getBundlebase());
        reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");
        this.ms = reloadableResourceBundleMessageSource;
        this.locale = Locale.forLanguageTag(props.getLocaleset());

    }

    @Override
    public String getI18nString(String value) {
        return ms.getMessage(value, null, this.locale);
    }
}
