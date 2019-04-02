package ru.otus.spring16.domain.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.ResourceSupport;
import ru.otus.spring16.domain.Book;

public class ObjectResource extends ResourceSupport {

    @JsonUnwrapped
    private Object object;

    public ObjectResource(Book object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
