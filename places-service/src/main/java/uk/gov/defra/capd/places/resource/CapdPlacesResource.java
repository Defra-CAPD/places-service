package uk.gov.defra.capd.places.resource;

import com.yammer.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.defra.capd.places.api.OsPlacesApiException;
import uk.gov.defra.capd.places.api.OsPlacesClient;
import uk.gov.defra.capd.places.api.OsPlacesClientException;
import uk.gov.defra.capd.places.model.CapdPlaces;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static uk.gov.defra.capd.common.logging.LogFormatter.Type.*;
import static uk.gov.defra.capd.common.logging.LogFormatter.Severity.*;
import static uk.gov.defra.capd.common.logging.LogEntry.logEntry;

@Path("/places")
@Produces(MediaType.APPLICATION_JSON)
public class CapdPlacesResource {

    private static Logger LOGGER = LoggerFactory.getLogger(CapdPlacesResource.class);

    private OsPlacesClient placesClient;

    public CapdPlacesResource(OsPlacesClient placesClient) {
        this.placesClient = placesClient;
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{postCode}")
    public CapdResponse<CapdPlaces> places(@PathParam("postCode") String postCode) {
        try {
            CapdPlaces response =  placesClient.getPlacesForPostcode(postCode);
            return new CapdResponse(response);
        } catch (OsPlacesClientException ex) {
            LOGGER.error("Error when calling os places client postcode: {}", postCode, logEntry(CRITICAL, ERROR, "CapdPlacesResource - places"), ex);
            throw new WebApplicationException(INTERNAL_SERVER_ERROR);
        } catch (OsPlacesApiException ex) {
            LOGGER.error("Error from os places api for postcode: {}", postCode, logEntry(CRITICAL, ERROR, "CapdPlacesResource - places"), ex);
            throw new WebApplicationException(createResponseFromException(ex));
        }
    }

    private Response createResponseFromException(OsPlacesApiException ex) {
        Response.ResponseBuilder responseBuilder = Response.status(ex.getHttpCode());
        if(ex.getOsPlacesMessage().isPresent()) {
            responseBuilder.entity(ex.getOsPlacesMessage().get());
        }
        return responseBuilder.build();
    }
}
