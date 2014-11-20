package uk.gov.defra.capd.places.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.defra.capd.places.api.OsPlacesApiException;
import uk.gov.defra.capd.places.config.OsPlacesClientConfig;
import uk.gov.defra.capd.places.model.CapdPlaces;
import uk.gov.defra.capd.places.model.CapdPlacesMapper;
import uk.gov.defra.capd.places.model.OsPlacesResponse;

import java.io.IOException;
import java.net.URL;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.mockito.Mockito.*;
import static uk.gov.defra.capd.places.client.JsonHelper.getObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class OsPlacesClientImplTest {

    private static final String POST_CODE = "SW114DR";

    private static final String ERROR_MESSAGE = "No query parameter provided.";
    private static final String ERROR_RESPONSE = "{\n" +
            "\"error\" : {" +
            "\"statuscode\" : 400," +
            "\"message\" : \""+ ERROR_MESSAGE +"\"" +
            "}" +
            "}";

    @Mock
    private CapdPlacesMapper capdPlacesMapperMock;

    @Mock
    Client client = mock(Client.class);

    @Mock
    OsPlacesClientConfig config = mock(OsPlacesClientConfig.class);

    @InjectMocks
    OsPlacesClientImpl osPlacesClient = new OsPlacesClientImpl(config);



    @Test
    public void testInvokeOsPlaces_Success() throws Exception {
        WebResource.Builder resource = prepareResourceMock();
        when(resource.get(any(Class.class))).thenReturn(loadFileContent());
        osPlacesClient.getPlacesForPostcode(POST_CODE);
        verify(capdPlacesMapperMock).map(any(OsPlacesResponse.class), eq(CapdPlaces.class));
    }
    @Test
    public void testInvokeOsPlaces_onOsPlacesApiExceptionWithBadRequst() throws Exception {
        WebResource.Builder builder = prepareResourceMock();
        UniformInterfaceException exception = prepareException(BAD_REQUEST.getStatusCode());
        when(builder.get(any(Class.class))).thenThrow(exception);
        try{
            osPlacesClient.getPlacesForPostcode(POST_CODE);
            fail();
        }catch (OsPlacesApiException e){
            assertEquals(BAD_REQUEST.getStatusCode(), e.getHttpCode());
            assertEquals(ERROR_MESSAGE, e.getOsPlacesMessage().get());
            assertEquals(exception, e.getCause());
        }
    }

    @Test
    public void testInvokeOsPlaces_onOsPlacesApiExceptionWithUnauthorized() throws Exception {
        WebResource.Builder builder = prepareResourceMock();
        UniformInterfaceException exception = prepareException(UNAUTHORIZED.getStatusCode());
        when(builder.get(any(Class.class))).thenThrow(exception);
        try{
            osPlacesClient.getPlacesForPostcode(POST_CODE);
            fail();
        }catch (OsPlacesApiException e){
            assertEquals(UNAUTHORIZED.getStatusCode(), e.getHttpCode());
            assertEquals(exception, e.getCause());
        }
    }

    private UniformInterfaceException prepareException(int status) {
        ClientResponse clientResponse = mock(ClientResponse.class);
        when(clientResponse.getStatus()).thenReturn(status);
        when(clientResponse.getEntity(String.class)).thenReturn(ERROR_RESPONSE);
        return new UniformInterfaceException(clientResponse);
    }

    private WebResource.Builder prepareResourceMock(){
        WebResource resource = mock(WebResource.class);
        when(client.resource(anyString())).thenReturn(resource);
        WebResource.Builder builder = mock(WebResource.Builder.class);
        when(resource.type(anyString())).thenReturn(builder);
        when(builder.accept(anyString())).thenReturn(builder);
        when(builder.header(anyString(), anyString())).thenReturn(builder);
        return builder;
    }

    private OsPlacesResponse loadFileContent() throws IOException {
        String path = "/api-response.json";
        URL url = this.getClass().getResource(path);
        OsPlacesResponse entity = getObjectMapper().readValue(url, OsPlacesResponse.class);
        return entity;
    }
}
