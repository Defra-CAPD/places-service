package uk.gov.defra.capd.places.api;

import uk.gov.defra.capd.places.model.CapdPlaces;

public interface OsPlacesClient {

    public CapdPlaces getPlacesForPostcode(String postcode) throws OsPlacesClientException, OsPlacesApiException;
}
