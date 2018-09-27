package com.kelevnor.geminidemo.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kelevnor.geminidemo.Model.address_info.AddressInfo;
import com.kelevnor.geminidemo.Model.address_info.Result;
import com.kelevnor.geminidemo.Model.address_info.Transaction;
import com.kelevnor.geminidemo.R;
import com.kelevnor.geminidemo.REST.Retrofit_API;
import com.kelevnor.geminidemo.Utility.Config;

import java.util.List;

/**
 * Created by kelevnor on 9/26/18.
 */

public class DIALOG_PostAmountToUser extends Dialog implements android.view.View.OnClickListener, Retrofit_API.OnAsyncResult {

        String amountEntered = "0.00";
        public Activity act;
        public TextView postAction, cancelAction, fontAwesomeSymbol, from, to;
        EditText inputAmountET;
        String toAddress;
        Typeface fontAwesome;
        onPostRequest requestListener;
        public DIALOG_PostAmountToUser(Activity a, onPostRequest requestListener, String toAddress) {
            super(a);
            // TODO Auto-generated constructor stub
            this.act = a;
            this.toAddress = toAddress;
            fontAwesome = Typeface.createFromAsset(act.getAssets(), "fonts/fontawesome-webfont.ttf");
            this.requestListener = requestListener;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_dialog_post_amount);
            fontAwesomeSymbol = (TextView) findViewById(R.id.tv_fontawesometo);
            postAction = (TextView) findViewById(R.id.tv_actionpost);
            cancelAction = (TextView) findViewById(R.id.tv_actioncancel);
            from = (TextView) findViewById(R.id.tv_from);
            to = (TextView) findViewById(R.id.tv_to);
            inputAmountET = (EditText) findViewById(R.id.et_inputamount);
            postAction.setOnClickListener(this);
            cancelAction.setOnClickListener(this);
            from.setText(Config.userName);
            to.setText(toAddress);
            fontAwesomeSymbol.setTypeface(fontAwesome);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_actionpost:
                    if(inputAmountET.getText().length()>0){
                        amountEntered = inputAmountET.getText().toString();
                        Retrofit_API _api = new Retrofit_API(act,this);
                        _api.postAmountToAddress(toAddress, amountEntered);
                    }
                    else{
                        Toast.makeText(act,"Empty Amount Detected!",Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.tv_actioncancel:
                    dismiss();
                    break;
            }
            dismiss();
        }

    @Override
    public void onResultSuccessAddress(int resultCode, AddressInfo objList) {

    }

    @Override
    public void onResultSuccessTransactions(int resultCode, List<Transaction> objList) {

    }

    @Override
    public void onResultSuccessPostAmountToAddress(int resultCode, Result obj) {
        dismiss();
        requestListener.onPostAmountSuccess(toAddress);
        Toast.makeText(act,"Successfully sent "+amountEntered+" to "+ toAddress+"!",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResultFail(int resultCode, String errorMessage) {
        dismiss();
        requestListener.onPostAmountFail(errorMessage);
        Toast.makeText(act,"Transaction Failed!",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onInternetFail(int resultCode, String errorMessage) {

    }

    public interface onPostRequest{
        void onPostAmountSuccess(String toAddress);
        void onPostAmountFail(String error);
    }


}
