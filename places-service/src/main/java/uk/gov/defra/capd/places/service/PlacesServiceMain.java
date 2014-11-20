package uk.gov.defra.capd.places.service;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import uk.gov.defra.capd.common.healthcheck.ApplicationStatusService;
import uk.gov.defra.capd.common.logging.DiagnosticContextFilter;
import uk.gov.defra.capd.common.logging.ParamsConverter;
import uk.gov.defra.capd.places.api.OsPlacesClient;
import uk.gov.defra.capd.places.client.DummyClientImpl;
import uk.gov.defra.capd.places.client.OsPlacesClientImpl;
import uk.gov.defra.capd.places.config.PlacesConfiguration;
import uk.gov.defra.capd.places.resource.ApplicationStatusResource;
import uk.gov.defra.capd.places.resource.CapdPlacesResource;

import static uk.gov.defra.capd.places.config.PlacesConfiguration.OsPlacesConfig;

public class PlacesServiceMain extends Service<PlacesConfiguration> {

    public static void main(String[] args) throws Exception {
        ParamsConverter.init();
        new PlacesServiceMain().run(args);
    }

    @Override
    public void initialize(Bootstrap<PlacesConfiguration> bootstrap) {
        bootstrap.setName("placesService");
    }

    @Override
    public void run(PlacesConfiguration configuration,
                    Environment environment) {
        PlacesHealthcheck healthcheck = createPlacesHealthcheck(configuration.getOsPlaces());
        CapdPlacesResource placesResource = initPlacesResource(configuration.getOsPlaces());
        ApplicationStatusResource statusResource = initStatusResource(configuration);
        environment.addHealthCheck(healthcheck);
        environment.addResource(placesResource);
        environment.addResource(statusResource);
        environment.addFilter(new DiagnosticContextFilter(), "/*");
    }

    private PlacesHealthcheck createPlacesHealthcheck(OsPlacesConfig osConfig) {
        String placesUrl = osConfig.getUrl();
        return new PlacesHealthcheck(placesUrl);
    }

    private ApplicationStatusResource initStatusResource(PlacesConfiguration config) {
        ApplicationStatusService appStatusService = new ApplicationStatusService();
        appStatusService.setApplicationVersionFilePath(config.getApplicationVersionFilePath());
        appStatusService.loadApplicationVersion("version.txt");
        return new ApplicationStatusResource(appStatusService);
    }

    private CapdPlacesResource initPlacesResource(OsPlacesConfig osConfig) {
        OsPlacesClient client;
        if(!osConfig.getDummy()) {
            client = new OsPlacesClientImpl(osConfig);
        } else {
            client = new DummyClientImpl();
        }
        return new CapdPlacesResource(client);
    }
}
