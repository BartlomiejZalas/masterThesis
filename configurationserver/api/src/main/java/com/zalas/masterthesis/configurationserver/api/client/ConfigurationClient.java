package com.zalas.masterthesis.configurationserver.api.client;

import com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.*;

public class ConfigurationClient {

    public static final String CONFIGURATION_ENDPOINT_URL = "http://localhost:8888/configuration";

    public Value getConfiguration(ConfigurationConstants key) throws ConfigurationClientException {
        try {
            WebTarget target = ClientBuilder.newClient().target(CONFIGURATION_ENDPOINT_URL).path("/get").queryParam("key", key);
            Invocation.Builder invocationBuilder = target.request();
            Response response = invocationBuilder.get();

            return Value.valueOf(handleResponse(response));
        } catch (Exception e) {
            throw new ConfigurationClientException(e.getMessage());
        }
    }

    public void setConfiguration(ConfigurationConstants key, Value value) throws ConfigurationClientException {
        try {
            WebTarget target = ClientBuilder.newClient().target(CONFIGURATION_ENDPOINT_URL).path("/change").queryParam("key", key).queryParam("value", value);
            Invocation.Builder invocationBuilder = target.request();
            Response response = invocationBuilder.post(Entity.entity("", MediaType.TEXT_PLAIN));

            handleResponse(response);
        } catch (Exception e) {
            throw new ConfigurationClientException(e.getMessage());
        }
    }

    private String handleResponse(Response response) throws ConfigurationClientException {
        if (response.getStatus() != 200) {
            String message = response.readEntity(String.class);
            throw new ConfigurationClientException(message);
        } else {
            return response.readEntity(String.class);
        }
    }

}
