package com.zalas.masterthesis.requestdispatcher;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

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

        server.setHandler(new PortRedirector(getPortFromExternalSource()));
        server.start();
        server.dumpStdErr();
        server.join();
    }

    public int getPortFromExternalSource() {
        System.out.println("sprawdzam port!");
        return 8080;
    }
}

class PortRedirector extends AbstractHandler {
    private int to;

    PortRedirector(int to) {
        this.to = to;
    }

    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uri = request.getScheme() + "://" + request.getServerName() + ":" + to + request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        response.sendRedirect(uri);
    }
}
