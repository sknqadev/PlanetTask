package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.qameta.allure.*;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.*;

@Epic("Banking")
@Feature("Transaction Details")
@Story("User retrieves a specific transaction")
@Owner("davi")
@Severity(SeverityLevel.NORMAL)
public class TransactionDetailsTests {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost:8080/";
    }

    @Test
    @DisplayName("Should return transaction details when transaction exists")
    void shouldReturnTransactionDetails_whenTransactionExists() {
        String transactionId = "TX123456";

        get("/transactions/" + transactionId)
                .then()
                .statusCode(200)
                .body("transaction_id", is(transactionId))
                .body("from_account", notNullValue())
                .body("to_account", notNullValue())
                .body("amount", greaterThan(0.0f))
                .body("currency", notNullValue())
                .body("status", notNullValue())
                .body("timestamp", notNullValue());
    }
}
