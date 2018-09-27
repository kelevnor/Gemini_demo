package com.kelevnor.geminidemo.REST;

/**
 * Created by kelevnor on 9/24/18.
 */

import android.content.Context;

import com.kelevnor.geminidemo.Model.address_info.AddressInfo;
import com.kelevnor.geminidemo.Model.address_info.Result;
import com.kelevnor.geminidemo.Model.address_info.Transaction;
import com.kelevnor.geminidemo.Utility.Config;
import com.kelevnor.geminidemo.Utility.UtilityHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by kelevnor on 9/25/18.
 */

public class Retrofit_API {

    OnAsyncResult onAsyncResult;
    Context con;
    Retrofit retrofit;

    //Class constructor instantiating the retrofit object
    public Retrofit_API(Context con, final OnAsyncResult onAsyncResult) {
        this.onAsyncResult = onAsyncResult;
        this.con = con;

        retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    //Get user info with balance and user transactions
    public void getAddressInfo(String address) {
        if(UtilityHelper.isNetworkAvailable(con)){
            PullTestData testData = retrofit.create(PullTestData.class);
            Call<AddressInfo> call= testData.getTestData(address);
            call.enqueue(new Callback<AddressInfo>() {
                @Override
                public void onResponse(Call<AddressInfo> call, Response<AddressInfo> response) {
                    onAsyncResult.onResultSuccessAddress(Config.RESULT_SUCCESS, response.body());
                }
                @Override
                public void onFailure(Call<AddressInfo> call, Throwable t){
                    onAsyncResult.onResultFail(Config.RESULT_FAIL, t.getMessage());
                }
            });
        }
        else{
            this.onAsyncResult.onInternetFail(Config.INTERNET_FAIL, "");
        }
    }

    //Get all transactions with User's transactions included
    public void getAllTransactions(){
        if(UtilityHelper.isNetworkAvailable(con)){
            PullTestData testData = retrofit.create(PullTestData.class);

            Call<List<Transaction>> call= testData.getTransactions();
            call.enqueue(new Callback<List<Transaction>>() {
                @Override
                public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                    onAsyncResult.onResultSuccessTransactions(Config.RESULT_SUCCESS, response.body());

                }
                @Override
                public void onFailure(Call<List<Transaction>> call, Throwable t){
                    onAsyncResult.onResultFail(Config.RESULT_FAIL, t.getMessage());
                }
            });
        }
        else{
            this.onAsyncResult.onInternetFail(Config.INTERNET_FAIL, "");
        }
    }

    //Post amount from user to different address
    public void postAmountToAddress(String to, String amount){
        if(UtilityHelper.isNetworkAvailable(con)){
            PullTestData testData = retrofit.create(PullTestData.class);

            Call<Result> call= testData.postAmountToAddress(Config.userName, to, amount);
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> obj) {
                    onAsyncResult.onResultSuccessPostAmountToAddress(Config.RESULT_SUCCESS, obj.body());
                }
                @Override
                public void onFailure(Call<Result> call, Throwable t){
                    onAsyncResult.onResultFail(Config.RESULT_FAIL, t.getMessage());
                }
            });
        }
        else{
            this.onAsyncResult.onInternetFail(Config.INTERNET_FAIL, "");
        }
    }

    //This is the Interface for the RetrofitCall
    public interface PullTestData{
        //Retrofit Interface to complete the header and make the CALL - Get Address
        @GET("liquefy/api/addresses/{address}")
        Call<AddressInfo> getTestData(@Path("address") String address);

        //Retrofit Interface to complete the header and make the CALL - Get All Transactions
        @GET("liquefy/api/transactions")
        Call<List<Transaction>> getTransactions();

        @FormUrlEncoded
        @POST("liquefy/api/transactions")
        Call<Result> postAmountToAddress(@Field("fromAddress") String fromAddress, @Field("toAddress") String toAddress, @Field("amount") String amount);
    }

    //This is the Interface for listener on Activity
    public interface OnAsyncResult {
        void onResultSuccessAddress(int resultCode, AddressInfo objList);
        void onResultSuccessTransactions(int resultCode, List<Transaction> objList);
        void onResultSuccessPostAmountToAddress(int resultCode, Result obj);
        void onResultFail(int resultCode, String errorMessage);
        void onInternetFail(int resultCode, String errorMessage);
    }
}
