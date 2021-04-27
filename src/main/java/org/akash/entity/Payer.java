package org.akash.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Payer {
    @Id
    @Type(type = "uuid-char")
    private UUID payerId;
    private String payer;
    private long points;

    public Payer(){
        this.payerId = UUID.randomUUID();
    }

    public UUID getPayerId() {
        return payerId;
    }

    public void setPayerId(UUID payerId) {
        this.payerId = payerId;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String name) {
        this.payer = name;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long balance) {
        this.points = balance;
    }
}
