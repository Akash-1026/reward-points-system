package org.akash.dto;

import java.util.Date;

public class TransactionRequestDTO {
    String payer;
    int points;
    Date timestamp;

    public String getPayer() {
        return payer;
    }

    public int getPoints() {
        return points;
    }

    public Date getTimestamp() {
        return timestamp;
    }

}
