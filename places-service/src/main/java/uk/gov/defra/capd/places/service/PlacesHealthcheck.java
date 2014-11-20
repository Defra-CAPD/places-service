package uk.gov.defra.capd.places.service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.yammer.metrics.core.HealthCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static uk.gov.defra.capd.common.logging.LogFormatter.Type.*;
import static uk.gov.defra.capd.common.logging.LogFormatter.Severity.*;
import static uk.gov.defra.capd.common.logging.LogEntry.logEntry;

import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

public class PlacesHealthcheck extends HealthCheck{

    private String url;
    private Client client;
    private static Logger LOGGER = LoggerFactory.getLogger(PlacesHealthcheck.class);

    public PlacesHealthcheck(String url) {
        super("OS places");
        this.url = url;
        client = Client.create();
    }

    @Override
    protected Result check() throws Exception {
        try {
            ClientResponse response = client.resource(url).options(ClientResponse.class);
            if (response.getStatus() == UNAUTHORIZED.getStatusCode()) {
                return Result.healthy();
            } else {
                return Result.unhealthy(response.getEntity(String.class));
            }
        } catch (Exception ex) {
            LOGGER.error("Healthcheck error", logEntry(CRITICAL, HEALTH, "PlacesHealthcheck - check"), ex);
            return Result.unhealthy(ex);
        }
    }


}
