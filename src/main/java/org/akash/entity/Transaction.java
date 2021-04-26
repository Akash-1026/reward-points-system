package org.akash.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity
public class Transaction {
    @Id
    @Type(type = "uuid-char")
    private UUID transactionId;
    private String payer;
    private long points;
    private Date timestamp;
    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean isProcessed;

    public Transaction(){
        this.transactionId = UUID.randomUUID();
    }

    public UUID getTid() {
        return transactionId;
    }

    public void setTid(UUID tid) {
        this.transactionId = tid;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }
}
