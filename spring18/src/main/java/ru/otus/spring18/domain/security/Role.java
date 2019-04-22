package ru.otus.spring18.domain.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document
@JsonDeserialize(as=Role.class)
public class Role implements GrantedAuthority {
    @Id
    private String id;
    @JsonProperty("authority")
    private String role;

    public Role(String role) {
        this.role = role;
    }

    public Role() {
        super();
    }

    @Override
    public String getAuthority() {
        return role;
    }
}