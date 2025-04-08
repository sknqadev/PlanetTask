package com.example.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.Resource;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MockServer {
    private static WireMockServer wiremockServer;
    private static Server webServer;

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("No command found use 'start' or 'stop'.");
            return;
        }

        String command = args[0].toLowerCase();
        switch (command) {
            case "start":
                startServers();
                break;
            case "stop":
                stopServers();
                break;
            default:
                System.out.println("Invalid command use 'start' or 'stop'.");
        }
    }

    public static void startServers() throws Exception {
        // Start Wiremock
        if (wiremockServer == null || !wiremockServer.isRunning()) {
            wiremockServer = new WireMockServer(WireMockConfiguration.options().port(8080));
            wiremockServer.start();

            // Stub for POST /banking/transactions
            wiremockServer.stubFor(post(urlEqualTo("/transactions"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody("{ \"transaction_id\": \"TX123456\", \"success\": true, \"new_balance\": 900.0 }")));

            // Stub for GET /banking/transactions/{transaction_id}
            wiremockServer.stubFor(get(urlPathMatching("/transactions/.*"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody("{ \"transaction_id\": \"TX123456\", \"from_account\": \"123456\", \"to_account\": \"654321\", \"amount\": 100.0, \"currency\": \"USD\", \"status\": \"completed\", \"timestamp\": \"2023-01-01T10:00:00Z\" }")));

            // Stub for GET /banking/accounts/{account_id}/balance
            wiremockServer.stubFor(get(urlPathMatching("/accounts/.*/balance"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody("{ \"account_id\": \"123456\", \"balance\": 900.0, \"currency\": \"USD\" }")));

            System.out.println("WireMock started on port 8080");
        } else {
            System.out.println("WireMock is already running.");
        }

        // Start Jetty to serve HTML files
        if (webServer == null || !webServer.isRunning()) {
            webServer = new Server(5500);
            ServletContextHandler context = new ServletContextHandler();

            context.setContextPath("/");
            context.setBaseResource(Resource.newClassPathResource("/static"));
            context.addServlet(DefaultServlet.class, "/");

            webServer.setHandler(context);
            webServer.start();

            System.out.println("Jetty started on port 5500");
        } else {
            System.out.println("Jetty is already running.");
        }
    }

    public static void stopServers() {
        // Stop Wiremock Server
        if (wiremockServer != null && wiremockServer.isRunning()) {
            try {
                wiremockServer.stop();
            } catch (Exception e) {
                System.err.println("Error trying to stop Wiremock: " + e.getMessage());
            }
            System.out.println("Wiremock stopped.");
        } else {
            System.out.println("Wiremock is not running.");
        }

        // Stop Jetty Server
        if (webServer != null && webServer.isRunning()) {
            try {
                webServer.stop();
                System.out.println("Jetty stopped.");
            } catch (Exception e) {
                System.err.println("Error while trying to stop Jetty; " + e.getMessage());
            }
        } else {
            System.out.println("Jetty is not running.");
        }
    }
}