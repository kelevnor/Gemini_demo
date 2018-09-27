package com.kelevnor.geminidemo;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.kelevnor.geminidemo.Adapter.ADAPTER_ViewPager;

import com.kelevnor.geminidemo.Fragments.FRAGMENT_Main;
import com.kelevnor.geminidemo.Fragments.FRAGMENT_Transactions;
import com.kelevnor.geminidemo.Model.address_info.AddressInfo;
import com.kelevnor.geminidemo.Service.SERVICE_getUserAddress;
import com.kelevnor.geminidemo.Utility.Config;
import com.kelevnor.geminidemo.Utility.UtilityHelper;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    ADAPTER_ViewPager tempAdapter;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.vpPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        doBindService();

    }
    private void setupViewPager(final ViewPager viewPager) {
        this.viewPager = viewPager;
        tempAdapter = new ADAPTER_ViewPager(getSupportFragmentManager());

        FRAGMENT_Main FRAGMENTMain = new FRAGMENT_Main();
        FRAGMENT_Transactions transactionFragment = new FRAGMENT_Transactions();

        tempAdapter.addFragment(FRAGMENTMain, "Profile");
        tempAdapter.addFragment(transactionFragment, "Transactions");
        this.viewPager.setAdapter(tempAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private SERVICE_getUserAddress mBoundService;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has
            // been established, giving us the service object we can use
            // to interact with the service.  Because we have bound to a
            // explicit service that we know is running in our own
            // process, we can cast its IBinder to a concrete class and
            // directly access it.
            mBoundService = ((SERVICE_getUserAddress.LocalBinder)service).getService();

            // Tell the user about this for our demo.
            Toast.makeText(getApplicationContext(), "Service Bound", Toast.LENGTH_SHORT).show();
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has
            // been unexpectedly disconnected -- that is, its process
            // crashed. Because it is running in our same process, we
            // should never see this happen.
            mBoundService = null;
            Toast.makeText(getApplicationContext(),
                    "Service Disconnected",
                    Toast.LENGTH_SHORT).show();
        }
    };

    boolean mIsBound = false;
    void doBindService() {
        // Establish a connection with the service.  We use an explicit
        // class name because we want a specific service implementation
        // that we know will be running in our own process (and thus
        // won't be supporting component replacement by other
        // applications).
        Intent i = new Intent(getApplicationContext(), SERVICE_getUserAddress.class);
        bindService(i,
                mConnection,
                Context.BIND_AUTO_CREATE);
        mIsBound = true;


    }

    void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
        Config.allTransactions.clear();
        Config.user = new AddressInfo();
        Config.userName = "";
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(onBroadcast, new IntentFilter("user_update"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(onBroadcast);
    }

    public static BroadcastReceiver onBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent i) {
            // do stuff to the UI
            Log.e("Data Change","Data Change");
            if(FRAGMENT_Main.inTransactionsTab) {
                FRAGMENT_Main.transactionListAdapter.notifyData(Config.user.getTransactions());
            }
            else{
                Config.buddyList = UtilityHelper.fillBuddyList(Config.user);
                FRAGMENT_Main.buddyListAdapter.notifyData(Config.buddyList);
            }
            FRAGMENT_Main.balance.setText(Config.user.getBalance());
        }
    };
}
