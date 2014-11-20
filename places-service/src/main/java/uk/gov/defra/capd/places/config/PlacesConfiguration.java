package uk.gov.defra.capd.places.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

import javax.validation.constraints.NotNull;

public class PlacesConfiguration extends Configuration {

    @JsonProperty
    @NotNull
    private String applicationVersionFilePath;

    @JsonProperty
    @NotNull
    private OsPlacesConfig osPlaces;

    public OsPlacesConfig getOsPlaces() {
        return osPlaces;
    }

    public String getApplicationVersionFilePath() {
        return applicationVersionFilePath;
    }

    public static class OsPlacesConfig implements OsPlacesClientConfig {

        @JsonProperty
        @NotNull
        private Boolean dummy;

        @JsonProperty
        @NotNull
        private String url;

        @JsonProperty
        @NotNull
        private String key;

        @JsonProperty
        @NotNull
        private Integer timeout;

        public Boolean getDummy() {
            return dummy;
        }

        public String getUrl() {
            return url;
        }

        public String getKey() {
            return key;
        }

        public Integer getTimeout() {
            return timeout;
        }
    }
}
