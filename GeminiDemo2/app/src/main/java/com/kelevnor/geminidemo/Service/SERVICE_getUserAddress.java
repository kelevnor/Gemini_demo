package com.kelevnor.geminidemo.Service;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.kelevnor.geminidemo.Model.address_info.AddressInfo;
import com.kelevnor.geminidemo.Model.address_info.Result;
import com.kelevnor.geminidemo.Model.address_info.Transaction;
import com.kelevnor.geminidemo.REST.Retrofit_API;
import com.kelevnor.geminidemo.Utility.Config;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kelevnor on 9/26/18.
 */

public class SERVICE_getUserAddress extends Service{
    private Timer mTimer = null;
    Retrofit_API _api;
    Context con;
    Boolean hasBinded = false;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hasBinded = false;

        if(mTimer!=null){
            mTimer.cancel();
        }
    }

    private final IBinder mBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        con = getApplicationContext();
        hasBinded = true;
        return mBinder;
    }

    @Override
    public void onCreate() {
        // cancel if already existed
        if(mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, Config.NOTIFY_UPDATE_INTERVAL);
    }

    class TimeDisplayTimerTask extends TimerTask implements Retrofit_API.OnAsyncResult{
        @Override
        public void run() {
            if(hasBinded) {
                _api = new Retrofit_API(con,  this);
                _api.getAddressInfo(Config.userName);
            }
            else {
                Log.e("Not Binded Yet", "Not Binded Yet");
            }
        }

        @Override
        public void onResultSuccessAddress(int resultCode, AddressInfo objList) {
            Log.e("SUCCESS","SUCCESS");
            Log.e("Name",Config.userName);
            Log.e("Balance:","Balance: "+ objList.getBalance());
            Log.e("No# of Transactions: ","No# of Transactions: "+ objList.getTransactions().size());

            //method to check for new transaction on user

            Config.user = objList;
            Config.userTransactionSize = Config.user.getTransactions().size();

            Gson datajson = new Gson();
            String userData = datajson.toJson(objList);
            Intent i = new Intent("user_update");
            i.putExtra("user", userData);
            getApplicationContext().sendBroadcast(i);

        }

        @Override
        public void onResultSuccessTransactions(int resultCode, List<Transaction> objList) {

        }

        @Override
        public void onResultSuccessPostAmountToAddress(int resultCode, Result obj) {

        }

        @Override
        public void onResultFail(int resultCode, String errorMessage) {

        }

        @Override
        public void onInternetFail(int resultCode, String errorMessage) {

        }
    }

    //returns the instance of the service

        public class LocalBinder extends Binder {
            public SERVICE_getUserAddress getService() {
                return SERVICE_getUserAddress.this;
            }
        }
}
