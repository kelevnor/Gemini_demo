package com.kelevnor.geminidemo.Utility;

import com.kelevnor.geminidemo.Model.address_info.AddressInfo;
import com.kelevnor.geminidemo.Model.address_info.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelevnor on 9/24/18.
 */

public class Config {

    public static String userName = "";
    public static List<Transaction> allTransactions = new ArrayList<>();
    public static AddressInfo user = new AddressInfo();
    public static int userTransactionSize = 0;
    //base api
    public static String BASE_URL = "https://jobcoin.gemini.com/";
    public static List<String> buddyList = new ArrayList<>();

    //Integer Results
    public static int RESULT_SUCCESS = 1;
    public static int RESULT_FAIL = 0;
    public static int INTERNET_FAIL = 2;

    //Update Interval for user data update
    public static int NOTIFY_UPDATE_INTERVAL = 15000;

    //Update Interval to check for updated data for second service
    public static int NOTIFY_UPDATE_INTERVAL_INTERNAL = 5000;

}
