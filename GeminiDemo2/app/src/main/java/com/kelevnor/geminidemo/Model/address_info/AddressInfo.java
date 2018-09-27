
package com.kelevnor.geminidemo.Model.address_info;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressInfo {

    @SerializedName("balance")
    @Expose
    private String balance;
    @SerializedName("transactions")
    @Expose
    private List<Transaction> transactions = null;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}
