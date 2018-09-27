package com.kelevnor.geminidemo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelevnor.geminidemo.Adapter.ADAPTER_TransactionItem;
import com.kelevnor.geminidemo.Model.address_info.Transaction;
import com.kelevnor.geminidemo.R;
import com.kelevnor.geminidemo.Utility.Config;

/**
 * Created by kelevnor on 9/25/18.
 */
public class FRAGMENT_Transactions extends Fragment implements ADAPTER_TransactionItem.onItemClickListener{
    ADAPTER_TransactionItem transactionListAdapter;
    android.support.v7.widget.RecyclerView rvTransactions;

    public FRAGMENT_Transactions() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transactions_fragment, container, false);
        rvTransactions = view.findViewById(R.id.rv_transactions);

        transactionListAdapter = new ADAPTER_TransactionItem(getActivity(), Config.allTransactions, this);
        rvTransactions.setAdapter(transactionListAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvTransactions.setLayoutManager(mLayoutManager);
        rvTransactions.setItemAnimator(new DefaultItemAnimator());

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onItemClick(Transaction transaction) {

    }
}
