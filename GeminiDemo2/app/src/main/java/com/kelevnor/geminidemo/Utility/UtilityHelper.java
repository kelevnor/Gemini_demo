package com.kelevnor.geminidemo.Utility;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.kelevnor.geminidemo.Fragments.FRAGMENT_Main;
import com.kelevnor.geminidemo.Model.address_info.AddressInfo;
import com.kelevnor.geminidemo.Model.address_info.Transaction;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
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

    public static void fillGraph (){
        DataPoint[] data = new DataPoint[Config.user.getTransactions().size()];
        int counter = 0;
        Double amountTotal = 0.0;
        try{
//            Collections.reverse(Config.user.getTransactions());
            if(Config.user!=null&&Config.user.getTransactions().size()>0){
                for(Transaction e : Config.user.getTransactions()){

                    if(e.getFromAddress()!=null&&!e.getFromAddress().isEmpty()){
                        if(e.getFromAddress().equals(Config.userName)){
                            amountTotal-=Double.parseDouble(e.getAmount());
                        }
                        else{
                            amountTotal+=Double.parseDouble(e.getAmount());
                        }
                    }
                    else{
                        amountTotal+=Double.parseDouble(e.getAmount());
                    }
                    data[counter] = new DataPoint(UtilityHelper.parseISO8601DateToTimeStamp(e.getTimestamp()), amountTotal);
                    counter++;
                }
            }
            else {

            }
            LineGraphSeries<DataPoint> series= new LineGraphSeries<>(data);
            series.setColor(Color.parseColor("#00dcfa"));
            FRAGMENT_Main.graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
            FRAGMENT_Main.graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);// remove horizontal x labels and line
            FRAGMENT_Main.graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
            FRAGMENT_Main.graph.getViewport().setDrawBorder(true);

            FRAGMENT_Main.graph.addSeries(series);
        }
        catch (Exception e){

        }

    }

    public static String parseISO8601Date(String isoDate){

        DateTime dt = new DateTime( isoDate) ;

        DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM dd, yyyy - HH:mm:ss");
        String dtStr = fmt.print(dt);

        return dtStr;
    }

    public static long parseISO8601DateToTimeStamp(String isoDate){

        DateTime dt = new DateTime( isoDate) ;
        Timestamp ts = new Timestamp(dt.getMillis());

        return ts.getTime();
    }
}
