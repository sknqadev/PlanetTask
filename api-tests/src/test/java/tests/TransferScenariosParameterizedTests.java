package tests;

import data.TransferRequest;
import io.restassured.RestAssured;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("Banking")
@Feature("Fund Transfer")
@Story("User performs multiple fund transfers")
@Owner("davi")
@Severity(SeverityLevel.CRITICAL)
public class TransferScenariosParameterizedTests {

    static Stream<Arguments> namedArguments() {
        return Stream.of(
                Arguments.of(Named.of("Valid transfer", new TransferRequest("123456", "654321", 50.0, "USD"))),
                Arguments.of(Named.of("Invalid to_account", new TransferRequest("123456", "000000", 200.0, "EUR"))),
                Arguments.of(Named.of("Self-transfer", new TransferRequest("123456", "123456", 10.0, "USD"))),
                Arguments.of(Named.of("Zero amount", new TransferRequest("123456", "654321", 0.0, "USD")))
        );
    }

    static {
        RestAssured.baseURI = "http://localhost:8080/";
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("namedArguments")
    void should_handle_multiple_transfer_scenarios(TransferRequest request) {
        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/transactions")
                .then()
                .statusCode(200)
                .body("success", notNullValue()); // adapt to real response logic
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("updated_test_name"));
    }
}
