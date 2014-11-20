package uk.gov.defra.capd.places.api;

import com.google.common.base.Optional;

public class OsPlacesApiException extends Exception {

    private Optional<String> osPlacesMessage;

    private int httpCode;

    public OsPlacesApiException (int httpCode, Throwable throwable) {
        this(null, httpCode, throwable);
    }

    public OsPlacesApiException(String osPlacesMessage, int httpCode, Throwable throwable) {
        super(throwable);
        this.osPlacesMessage = Optional.fromNullable(osPlacesMessage);
        this.httpCode = httpCode;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public Optional<String> getOsPlacesMessage() {
        return osPlacesMessage;
    }

}
