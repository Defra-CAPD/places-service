package uk.gov.defra.capd.places.client;


import org.apache.commons.lang3.StringUtils;
import uk.gov.defra.capd.places.api.OsPlacesApiException;
import uk.gov.defra.capd.places.api.OsPlacesClient;
import uk.gov.defra.capd.places.api.OsPlacesClientException;
import uk.gov.defra.capd.places.model.CapdPlaces;

import java.io.IOException;
import java.net.URL;

import static com.sun.jersey.api.client.ClientResponse.Status.BAD_REQUEST;
import static uk.gov.defra.capd.places.client.JsonHelper.getObjectMapper;


public class DummyClientImpl implements OsPlacesClient {

    @Override
    public CapdPlaces getPlacesForPostcode(String postcode) throws OsPlacesClientException, OsPlacesApiException {
        try {
             String normalizedPostcode = StringUtils.deleteWhitespace(postcode.toLowerCase());
             if(!Character.isLetter(normalizedPostcode.charAt(0))) {
                 throw new OsPlacesApiException("Wrong postcode format", BAD_REQUEST.getStatusCode(), null);
             }
             String path = "/"+ normalizedPostcode + ".json";
             URL url = this.getClass().getResource(path);
             return url != null ? getObjectMapper().readValue(url, CapdPlaces.class) : new CapdPlaces();
         } catch (IOException | NullPointerException ex) {
             throw new OsPlacesClientException(ex);
         }
    }
}
