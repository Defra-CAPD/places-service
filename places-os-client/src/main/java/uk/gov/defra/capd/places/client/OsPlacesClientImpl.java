package uk.gov.defra.capd.places.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import uk.gov.defra.capd.places.api.OsPlacesApiException;
import uk.gov.defra.capd.places.api.OsPlacesClient;
import uk.gov.defra.capd.places.api.OsPlacesClientException;
import uk.gov.defra.capd.places.config.OsPlacesClientConfig;
import uk.gov.defra.capd.places.model.CapdPlaces;
import uk.gov.defra.capd.places.model.CapdPlacesMapper;
import uk.gov.defra.capd.places.model.OsPlacesResponse;

import javax.ws.rs.core.MediaType;

import static com.sun.jersey.api.client.ClientResponse.Status.BAD_REQUEST;
import static uk.gov.defra.capd.places.client.JsonHelper.extractMessageFromOsPlacesBadRequest;

public class OsPlacesClientImpl implements OsPlacesClient {

    private Client client;
    private CapdPlacesMapper mapper;
    private OsPlacesClientConfig config;

    public OsPlacesClientImpl(OsPlacesClientConfig config) {
        this.config = config;
        this.initClient();
        this.mapper = new CapdPlacesMapper();
    }

    private void initClient() {
        ClientConfig jerseyClientConfig = new DefaultClientConfig();
        jerseyClientConfig.getFeatures().put(
                JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        this.client = Client.create(jerseyClientConfig);
        this.client.setConnectTimeout(config.getTimeout());
        this.client.setReadTimeout(config.getTimeout());
    }

    @Override
    public CapdPlaces getPlacesForPostcode(String postcode) throws OsPlacesClientException, OsPlacesApiException {
        try {
            String url = createURL(postcode);
            OsPlacesResponse response = client.resource(url).
                    type(MediaType.APPLICATION_JSON).
                    accept(MediaType.APPLICATION_JSON).
                    get(OsPlacesResponse.class);

            return mapper.map(response, CapdPlaces.class);

        } catch (UniformInterfaceException ex) {
            int status = ex.getResponse().getStatus();
            String message = ex.getResponse().getEntity(String.class);
            if(status == BAD_REQUEST.getStatusCode()) {
                String osPlacesMessage = extractMessageFromOsPlacesBadRequest(message);
                throw new OsPlacesApiException(osPlacesMessage, status, ex);
            } else {
                throw new OsPlacesApiException(status, ex);
            }
        } catch (ClientHandlerException ex) {
            throw new OsPlacesClientException(ex);
        }
    }

    private String createURL(String postcode) {
        return config.getUrl() + "?key=" + config.getKey() + "&postcode=" + postcode.replace(" ", "%20");
    }
}
