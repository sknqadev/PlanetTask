package tests;

import data.TransferRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.qameta.allure.*;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

@Epic("Banking")
@Feature("Fund Transfer")
@Story("User initiates a valid fund transfer")
@Owner("Davi")
@Severity(SeverityLevel.BLOCKER)
public class FundTransferTests {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost:8080/";
    }

    @Test
    @DisplayName("Should transfer funds successfully when data is valid")
    void shouldTransferFundsSuccessfully_whenDataIsValid() {
        TransferRequest request = new TransferRequest("123456", "654321", 100.0, "USD");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/transactions")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/transfer-response-schema.json"));
    }
}
