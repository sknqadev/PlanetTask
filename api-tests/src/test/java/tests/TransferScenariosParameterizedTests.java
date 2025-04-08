package tests;

import data.TransferRequest;
import io.restassured.RestAssured;
import io.qameta.allure.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("Banking")
@Feature("Fund Transfer")
@Story("User performs multiple fund transfers")
@Owner("davi")
@Severity(SeverityLevel.CRITICAL)
public class TransferScenariosParameterizedTests {

    static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of("Valid transfer", new TransferRequest("123456", "654321", 50.0, "USD")),
                Arguments.of("Invalid to_account", new TransferRequest("123456", "000000", 200.0, "EUR")),
                Arguments.of("Self-transfer", new TransferRequest("123456", "123456", 10.0, "USD")),
                Arguments.of("Zero amount", new TransferRequest("123456", "654321", 0.0, "USD"))
        );
    }

    static {
        RestAssured.baseURI = "http://localhost:8080/";
    }

    @ParameterizedTest()
    @MethodSource("dataProvider")
    void should_handle_multiple_transfer_scenarios(String description, TransferRequest request) {
        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/transactions")
                .then()
                .statusCode(200)
                .body("success", notNullValue());
        AllureLifecycle lifecycle = Allure.getLifecycle();
        lifecycle.updateTestCase(testResult -> testResult.setName("Should handle multiple transfers: " + description));
    }
}
