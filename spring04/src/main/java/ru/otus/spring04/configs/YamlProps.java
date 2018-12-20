package ru.otus.spring04.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="quiz")
public class YamlProps {

    private String filename;
    private String bundlebase;
    private String localeset;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getBundlebase() {
        return bundlebase;
    }

    public void setBundlebase(String bundlebase) {
        this.bundlebase = bundlebase;
    }

    public String getLocaleset() {
        return localeset;
    }

    public void setLocaleset(String localeset) {
        this.localeset = localeset;
    }
}
