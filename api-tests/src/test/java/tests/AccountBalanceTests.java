package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.qameta.allure.*;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.*;

@Epic("Banking")
@Feature("Account Balance")
@Story("User retrieves account balance")
@Owner("davi")
@Severity(SeverityLevel.CRITICAL)
public class AccountBalanceTests {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost:8080/";
    }

    @Test
    @DisplayName("Should return account balance when account ID is valid")
    void shouldReturnAccountBalance_whenAccountIdIsValid() {
        String accountId = "123456";

        get("/accounts/" + accountId + "/balance")
                .then()
                .statusCode(200)
                .body("account_id", is(accountId))
                .body("balance", greaterThanOrEqualTo(0.0f))
                .body("currency", anyOf(is("USD"), is("EUR")));
    }
}
