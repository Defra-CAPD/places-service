package uk.gov.defra.capd.places.client;

import org.codehaus.jackson.map.ObjectMapper;
import uk.gov.defra.capd.places.model.OsPlacesBadResponse;

import static org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL;

public class JsonHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.setSerializationInclusion(NON_NULL);
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static String extractMessageFromOsPlacesBadRequest(String osPlacesSerializedMessage) {
        try{
            return getObjectMapper().readValue(osPlacesSerializedMessage, OsPlacesBadResponse.class).getError().getMessage();
        } catch (Exception ex) {
            throw new RuntimeException("Error when deserializing OS places response");
        }
    }
}
