package com.kelevnor.geminidemo.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.kelevnor.geminidemo.Adapter.ADAPTER_BuddyListItem;
import com.kelevnor.geminidemo.Adapter.ADAPTER_BuddyListItem.onItemClickListener;
import com.kelevnor.geminidemo.Adapter.ADAPTER_TransactionItem;
import com.kelevnor.geminidemo.Dialogs.DIALOG_PostAmountToUser;
import com.kelevnor.geminidemo.Model.address_info.AddressInfo;
import com.kelevnor.geminidemo.Model.address_info.Result;
import com.kelevnor.geminidemo.Model.address_info.Transaction;
import com.kelevnor.geminidemo.R;
import com.kelevnor.geminidemo.REST.Retrofit_API;
import com.kelevnor.geminidemo.Utility.Config;
import com.kelevnor.geminidemo.Utility.UtilityHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.internal.Util;

/**
 * Created by kelevnor on 9/24/18.
 */

public class FRAGMENT_Main extends Fragment implements ADAPTER_TransactionItem.onItemClickListener, View.OnClickListener, onItemClickListener, DIALOG_PostAmountToUser.onPostRequest, Retrofit_API.OnAsyncResult{
    TextView transactionTv, buddyListTv;
    public static android.support.v7.widget.RecyclerView rvTransactions;
    public static ADAPTER_TransactionItem transactionListAdapter;
    public static ADAPTER_BuddyListItem buddyListAdapter;
    public static Boolean inTransactionsTab = true;
    public static TextView balance;
    public static GraphView graph;
    Retrofit_API _api;
    public FRAGMENT_Main() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        balance = view.findViewById(R.id.tickerView);
        rvTransactions = view.findViewById(R.id.rv_transactions);
        transactionTv = view.findViewById(R.id.tv_transactionslabel);
        buddyListTv = view.findViewById(R.id.tv_friendslabel);
        graph = view.findViewById(R.id.graph);

        _api = new Retrofit_API(getActivity(), this);

        // Filling user's buddy list based on transactions
        // they have with different addresses
        Config.buddyList = UtilityHelper.fillBuddyList(Config.user);
        // Filling graph values
        UtilityHelper.fillGraph();
        // Showing newest transactions on top, reversing the user transactions list
        Collections.reverse(Config.user.getTransactions());

        transactionListAdapter = new ADAPTER_TransactionItem(getActivity(), Config.user.getTransactions(), this);
        buddyListAdapter = new ADAPTER_BuddyListItem(getActivity(), Config.buddyList, this);
        rvTransactions.setAdapter(transactionListAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvTransactions.setLayoutManager(mLayoutManager);
        rvTransactions.setItemAnimator(new DefaultItemAnimator());


        transactionTv.setOnClickListener(this);
        buddyListTv.setOnClickListener(this);
        balance.setText(Config.user.getBalance());

        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.tv_friendslabel:
                changeSelectedInnerTab(buddyListTv, transactionTv);
                Config.buddyList = UtilityHelper.fillBuddyList(Config.user);
                rvTransactions.setAdapter(buddyListAdapter);
                buddyListAdapter.notifyData(Config.buddyList);
                inTransactionsTab = false;
                break;

            case R.id.tv_transactionslabel:
                changeSelectedInnerTab(transactionTv, buddyListTv);
                rvTransactions.setAdapter(transactionListAdapter);
                transactionListAdapter.notifyData(Config.user.getTransactions());
                inTransactionsTab = true;
                break;
        }
    }

    //Method to change the inner tab styles
    public void changeSelectedInnerTab(TextView selected, TextView notSelected){
        selected.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        notSelected.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));

        selected.setBackgroundColor(getActivity().getResources().getColor(R.color.colorCCC));
        notSelected.setBackgroundColor(getActivity().getResources().getColor(R.color.colorEEE));
    }

    //Item click for user transaction recycleview list
    @Override
    public void onItemClick(Transaction transaction) {

    }
    //Item click for user recycleview list BuddyList
    @Override
    public void onItemClick(String name) {
        DIALOG_PostAmountToUser postAmountDialog = new DIALOG_PostAmountToUser(getActivity(), this,name);
        postAmountDialog.show();
    }

    // Interface listener for successful post action detected
    // in the custom dialog class used for post amount to address action
    @Override
    public void onPostAmountSuccess(String toAddress) {
        Log.e("Success","Reached Activity");
        _api.getAddressInfo(Config.userName);

    }

    @Override
    public void onPostAmountFail(String error) {
        Log.e("Fail","Reached Activity");
    }

    @Override
    public void onResultSuccessAddress(int resultCode, AddressInfo objList) {

        Config.user = objList;
        Collections.reverse(Config.user.getTransactions());
        balance.setText(Config.user.getBalance());

        Config.buddyList = UtilityHelper.fillBuddyList(Config.user);

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