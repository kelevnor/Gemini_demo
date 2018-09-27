package com.kelevnor.geminidemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.kelevnor.geminidemo.Model.address_info.AddressInfo;
import com.kelevnor.geminidemo.Model.address_info.Result;
import com.kelevnor.geminidemo.Model.address_info.Transaction;
import com.kelevnor.geminidemo.REST.Retrofit_API;
import com.kelevnor.geminidemo.Utility.Config;
import com.kelevnor.geminidemo.Utility.UtilityHelper;

import java.util.Collections;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,Retrofit_API.OnAsyncResult {
    private ProgressBar dialog;
    EditText mEmailEditText;
    Button mEmailSignInButton;
    Retrofit_API _API;
    String userData = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailEditText = (EditText) findViewById(R.id.et_email);
        dialog = (ProgressBar) findViewById(R.id.pb_loader);
        mEmailSignInButton = (Button) findViewById(R.id.btn_signin);
        mEmailSignInButton.setOnClickListener(this);
        _API = new Retrofit_API(this, this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_signin:

                if(mEmailEditText.getText().length()>0){
                    mEmailSignInButton.setEnabled(false);
                    Log.e("INPUT",mEmailEditText.getText().toString().trim());
                    dialog.setVisibility(View.VISIBLE);
                    _API.getAddressInfo(mEmailEditText.getText().toString().trim());
                    Config.userName = mEmailEditText.getText().toString().trim();
                }
                else{

                }

                break;
        }
    }


    // Result Listeners from Retrofit_API Class calls captured in activity through
    // these five interfaces
    @Override
    public void onResultSuccessAddress(int resultCode, AddressInfo objList) {
//        Gson datajson = new Gson();
//        userData = datajson.toJson(objList);
        Config.user = objList;
        Config.buddyList = UtilityHelper.fillBuddyList(Config.user);
        _API.getAllTransactions();
    }
    @Override
    public void onResultSuccessTransactions(int resultCode, List<Transaction> objList) {

        Config.allTransactions = objList;
        Collections.reverse(Config.allTransactions);
        mEmailSignInButton.setEnabled(true);
        dialog.setVisibility(View.INVISIBLE);

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    @Override
    public void onResultSuccessPostAmountToAddress(int resultCode, Result obj) {

    }
    @Override
    public void onResultFail(int resultCode, String errorMessage) {
        mEmailSignInButton.setEnabled(true);
    }
    @Override
    public void onInternetFail(int resultCode, String errorMessage) {
        mEmailSignInButton.setEnabled(true);
    }
}

