package uk.gov.defra.capd.places.resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.defra.capd.places.api.OsPlacesApiException;
import uk.gov.defra.capd.places.api.OsPlacesClient;
import uk.gov.defra.capd.places.api.OsPlacesClientException;
import uk.gov.defra.capd.places.model.CapdPlaces;

import javax.ws.rs.WebApplicationException;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CapdPlacesResourceTest {

    private static final String POST_CODE = "SD123A";

    @Mock
    private OsPlacesClient placesClientMock;

    @Mock
    private CapdPlaces placesResponseMock;

    @InjectMocks
    private CapdPlacesResource capdPlacesResource;

    @Test
    public void testPlaces_OnInternalServerError() throws Exception {
        when(placesClientMock.getPlacesForPostcode(anyString())).thenThrow(new OsPlacesClientException(null));
        try{
            capdPlacesResource.places(POST_CODE);
            fail();
        }catch (WebApplicationException e){
            assertEquals(INTERNAL_SERVER_ERROR.getStatusCode(), e.getResponse().getStatus());
        }
    }

    @Test
    public void testPlaces_OnApiException() throws Exception {
        String errorMessage = "Error on osPlaces api side occurred";
        int httpCode = 400;
        when(placesClientMock.getPlacesForPostcode(anyString())).thenThrow(new OsPlacesApiException(errorMessage, httpCode, null));
        try{
            capdPlacesResource.places(POST_CODE);
            fail();
        }catch (WebApplicationException e){
            assertEquals(httpCode, e.getResponse().getStatus());
            assertEquals(errorMessage, e.getResponse().getEntity().toString());
        }
    }

    @Test
    public void testPlaces_Success() throws Exception {
        when(placesClientMock.getPlacesForPostcode(POST_CODE)).thenReturn(placesResponseMock);
        CapdPlaces response = capdPlacesResource.places(POST_CODE).getData();
        assertEquals(placesResponseMock, response);

    }
}
