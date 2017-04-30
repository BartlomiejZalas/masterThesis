package com.zalas.masterthesis.resourcemonitoring.api;

import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;


public class ResourceMonitoringServiceClient {

    private static final String SERVICE_ENDPOINT_URL = "http://localhost:8889/resources";

    public void start() throws ResourceMonitoringServiceClientException {
        executeRequest("/start");
    }

    public void stop() throws ResourceMonitoringServiceClientException {
        executeRequest("/stop");
    }

    private void executeRequest(String path) throws ResourceMonitoringServiceClientException {
        Client client = ClientBuilder.newClient();
        client.property(ClientProperties.CONNECT_TIMEOUT, 1000);
        WebTarget target = client.target(SERVICE_ENDPOINT_URL).path(path);
        Invocation.Builder invocationBuilder = target.request();
        handleResponse(invocationBuilder.get());
    }

    private String handleResponse(Response response) throws ResourceMonitoringServiceClientException {
        if (response.getStatus() != 200) {
            String message = response.readEntity(String.class);
            throw new ResourceMonitoringServiceClientException(message);
        } else {
            return response.readEntity(String.class);
        }
    }
}
