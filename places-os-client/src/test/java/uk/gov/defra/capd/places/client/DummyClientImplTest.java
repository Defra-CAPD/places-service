package uk.gov.defra.capd.places.client;


import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import uk.gov.defra.capd.places.api.OsPlacesApiException;
import uk.gov.defra.capd.places.api.OsPlacesClient;
import uk.gov.defra.capd.places.model.CapdPlaces;

import static org.junit.Assert.assertEquals;

public class DummyClientImplTest {

    private final OsPlacesClient client = new DummyClientImpl();

    @Test
    public void testInvokeOsPlacesSuccess() throws Exception {
        String postCode = "SW 1 1 4DR";
        CapdPlaces response = client.getPlacesForPostcode(postCode);
        assertEquals(StringUtils.deleteWhitespace(postCode), response.getPostCode());
    }

    @Test(expected = OsPlacesApiException.class)
    public void testWrongCodeFormat() throws Exception {
        String postCode = "1W 1 1 4DR";
        CapdPlaces response = client.getPlacesForPostcode(postCode);
    }

}
