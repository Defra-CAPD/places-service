package uk.gov.defra.capd.places.client;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class JsonHelperTest {

    private static final String ERROR_MESSAGE = "No query parameter provided.";
    private static final String ERROR_RESPONSE = "{\n" +
            "\"error\" : {" +
            "\"statuscode\" : 400," +
            "\"message\" : \""+ ERROR_MESSAGE +"\"" +
            "}" +
            "}";

    @Test
    public void testExtractMessageFromOsPlacesBadRequest() throws Exception {
        String extractedMessage = JsonHelper.extractMessageFromOsPlacesBadRequest(ERROR_RESPONSE);
        assertEquals(ERROR_MESSAGE, extractedMessage);
    }
}
