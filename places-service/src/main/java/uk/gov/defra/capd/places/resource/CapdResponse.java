package uk.gov.defra.capd.places.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CapdResponse<T> {

    @JsonProperty("_data")
    private T data;

    public CapdResponse(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
