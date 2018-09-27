
package com.kelevnor.geminidemo.Model.address_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transaction {

    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("fromAddress")
    @Expose
    private String fromAddress;
    @SerializedName("toAddress")
    @Expose
    private String toAddress;
    @SerializedName("amount")
    @Expose
    private String amount;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
