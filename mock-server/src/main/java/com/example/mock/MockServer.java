package com.example.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.PathResource;

import java.net.URL;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MockServer {
    private static WireMockServer wireMockServer;
    private static Server webServer;

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("❌ Nenhum comando fornecido. Use 'start' ou 'stop'.");
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
                System.out.println("❌ Comando inválido. Use 'start' ou 'stop'.");
        }
    }

    public static void startServers() throws Exception {
        // Inicia o WireMock
        if (wireMockServer == null || !wireMockServer.isRunning()) {
            wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8080));
            wireMockServer.start();

            // Stub para POST /banking/transactions
            wireMockServer.stubFor(post(urlEqualTo("/transactions"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody("{ \"transaction_id\": \"TX123456\", \"success\": true, \"new_balance\": 900.0 }")));

            // Stub para GET /banking/transactions/{transaction_id}
            wireMockServer.stubFor(get(urlPathMatching("/transactions/.*"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody("{ \"transaction_id\": \"TX123456\", \"from_account\": \"123456\", \"to_account\": \"654321\", \"amount\": 100.0, \"currency\": \"USD\", \"status\": \"completed\", \"timestamp\": \"2023-01-01T10:00:00Z\" }")));

            // Stub para GET /banking/accounts/{account_id}/balance
            wireMockServer.stubFor(get(urlPathMatching("/accounts/.*/balance"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody("{ \"account_id\": \"123456\", \"balance\": 900.0, \"currency\": \"USD\" }")));

            System.out.println("✅ WireMock iniciado na porta 8080");
        } else {
            System.out.println("⚠️ WireMock já está em execução.");
        }

        // Inicia o Jetty para servir HTML
        if (webServer == null || !webServer.isRunning()) {
            webServer = new Server(5500);
            ServletContextHandler context = new ServletContextHandler();

            URL staticDir = MockServer.class.getClassLoader().getResource("static");
            if (staticDir == null) {
                System.err.println("❌ Pasta 'static' não encontrada.");
                return;
            }

            PathResource resource = new PathResource(Paths.get(staticDir.toURI()));
            context.setContextPath("/");
            context.setBaseResource(resource);
            context.addServlet(DefaultServlet.class, "/");

            webServer.setHandler(context);
            webServer.start();

            System.out.println("✅ Jetty iniciado na porta 5500");
        } else {
            System.out.println("⚠️ Jetty já está em execução.");
        }
    }

    public static void stopServers() {
        // Stop WireMock
        if (wireMockServer != null && wireMockServer.isRunning()) {
            wireMockServer.stop();
            System.out.println("✅ WireMock parado.");
        } else {
            System.out.println("⚠️ WireMock não está em execução.");
        }

        // Stop Jetty
        if (webServer != null && webServer.isRunning()) {
            try {
                webServer.stop();
                System.out.println("✅ Jetty parado.");
            } catch (Exception e) {
                System.err.println("❌ Erro ao parar o Jetty: " + e.getMessage());
            }
        } else {
            System.out.println("⚠️ Jetty não está em execução.");
        }
    }
}