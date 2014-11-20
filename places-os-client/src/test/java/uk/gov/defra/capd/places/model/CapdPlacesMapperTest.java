package uk.gov.defra.capd.places.model;

import org.junit.Test;

import java.net.URL;

import static junit.framework.Assert.assertEquals;
import static uk.gov.defra.capd.places.client.JsonHelper.getObjectMapper;

public class CapdPlacesMapperTest {

    @Test
    public void testMap() throws Exception {
        CapdPlacesMapper mapper = new CapdPlacesMapper();
        String path = "/api-response.json";
        URL url = this.getClass().getResource(path);
        OsPlacesResponse entity = getObjectMapper().readValue(url, OsPlacesResponse.class);
        CapdPlaces mappedValue = mapper.map(entity, CapdPlaces.class);
        assertEquals("SW112DR", mappedValue.getPostCode());
        CapdPlaces.Address address = mappedValue.getAddresses().get(0);
        assertEquals("99, LATCHMERE ROAD, LONDON, SW11 2DR", address.getAddress());
        assertEquals(null, address.getBuildingName());
        assertEquals("99", address.getBuildingNumber());
        assertEquals("LONDON", address.getCity());
        assertEquals(null, address.getFlatNumber());
        assertEquals("LATCHMERE ROAD", address.getStreet());
        assertEquals("100022661883", address.getUprn());
        assertEquals("EAST KILBRIDE", address.getDependentLocality());
        assertEquals("Tyre Industrial Estate", address.getDoubleDependentLocality());
    }
}
