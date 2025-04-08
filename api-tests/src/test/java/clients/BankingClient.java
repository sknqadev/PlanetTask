package clients;

import data.TransferRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BankingClient {

    public static Response initiateTransfer(TransferRequest request) {
        return given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/banking/transactions");
    }
}
