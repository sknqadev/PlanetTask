package data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferRequest {
    @JsonProperty("from_account")
    private String fromAccount;

    @JsonProperty("to_account")
    private String toAccount;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("currency")
    private String currency;

    @Override
    public String toString() {
        return String.format("Transfer %s ➡ %s: %.2f %s", fromAccount, toAccount, amount, currency);
    }

    public TransferRequest(String fromAccount, String toAccount, Double amount, String currency) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.currency = currency;
    }
}
