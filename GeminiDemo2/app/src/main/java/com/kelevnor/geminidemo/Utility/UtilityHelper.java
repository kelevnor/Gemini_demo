package com.kelevnor.geminidemo.Utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.kelevnor.geminidemo.Model.address_info.AddressInfo;
import com.kelevnor.geminidemo.Model.address_info.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelevnor on 9/24/18.
 */

public class UtilityHelper {

    //Method to detect internet availability for both Wifi and Mobile Data
    public static boolean isNetworkAvailable(Context context) {

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
    //Method to detect internet availability for Wifi
    public static boolean isWifiAvailable(Context context) {
        boolean haveConnectedWifi = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
        }
        return haveConnectedWifi;
    }

    public static List<String> fillBuddyList(AddressInfo userInfo){
        List<String> buddyList = new ArrayList<>();
        for(Transaction e: userInfo.getTransactions()){
            try {
                if(e.getFromAddress()!=null&&!e.getFromAddress().isEmpty()){
                    if(!buddyList.contains(e.getFromAddress())&&!e.getFromAddress().equals(Config.userName)){
                        buddyList.add(e.getFromAddress());
                    }
                }
                if(e.getToAddress()!=null&&!e.getToAddress().isEmpty()&&!e.getToAddress().equals(Config.userName)){
                    if(!buddyList.contains(e.getToAddress())){
                        buddyList.add(e.getToAddress());
                    }
                }
            } catch (NullPointerException e1) {
                e1.printStackTrace();

            }
        }

        return buddyList;
    }
}
