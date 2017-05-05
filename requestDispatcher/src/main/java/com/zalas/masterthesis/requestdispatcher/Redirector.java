package com.zalas.masterthesis.requestdispatcher;

import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClient;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClientException;
import com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Redirector {

    public static void main(String[] args) throws Exception {
        new Redirector().go();
    }

    public void go() throws Exception {
        Server server = new Server(8000);
        server.setHandler(new PortRedirector());
        server.start();
        server.dumpStdErr();
        server.join();
    }
}

class PortRedirector extends AbstractHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(Redirector.class);

    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int port = getPortFromExternalSource();
        LOGGER.info("Used port: " + port);

        String uri = request.getScheme() + "://" + request.getServerName() + ":" + port + request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        response.sendRedirect(uri);
    }

    public int getPortFromExternalSource() {
        try {
            ConfigurationClient configurationClient = new ConfigurationClient();
            ConfigurationConstants.Value threadsValue = configurationClient.getConfiguration(ConfigurationConstants.THREADS);
            LOGGER.info("Current threads configuration : " + threadsValue);
            switch (threadsValue) {
                case T20:
                    return 8020;
                case T50:
                    return 8051;
                case T80:
                    return 8080;
                default:
                    return 8050;
            }
        } catch (ConfigurationClientException e) {
            LOGGER.error("Cannot read threads configuration, default value used (8050)",e);
            return 8050;
        }
    }
}
