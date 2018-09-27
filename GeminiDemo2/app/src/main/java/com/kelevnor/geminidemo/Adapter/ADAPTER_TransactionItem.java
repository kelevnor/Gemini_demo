package com.kelevnor.geminidemo.Adapter;

/**
 * Created by kelevnor on 9/25/18.
 */

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelevnor.geminidemo.Model.address_info.Transaction;
import com.kelevnor.geminidemo.R;
import com.kelevnor.geminidemo.Utility.Config;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import me.grantland.widget.AutofitTextView;

/**
 * Created by kelevnor on 1/18/18.
 */

public class ADAPTER_TransactionItem extends RecyclerView.Adapter<ADAPTER_TransactionItem.ViewHolder> {
    private List<Transaction> searchList;
    onItemClickListener listener;
    Activity act;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;

        ImageView ivWeather;
        TextView from, time, amount;
        AutofitTextView to;
        LinearLayout outer;
        public ViewHolder(LinearLayout v) {
            super(v);
            layout = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ADAPTER_TransactionItem(Activity act, List<Transaction> searchList, onItemClickListener listener) {
        this.searchList = searchList;
        this.act = act;
        this.listener = listener;
//        fontAwesome = Typeface.createFromAsset(act.getAssets(),"fonts/fontawesome-webfont.ttf");
//        openSansRegular = Typeface.createFromAsset(act.getAssets(),"fonts/OpenSans-Regular.ttf");
//        openSansSemiBold = Typeface.createFromAsset(act.getAssets(),"fonts/OpenSans-Semibold.ttf");
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_list_item_transactions, parent, false);
        ViewHolder vh = new ViewHolder(v);
        vh.from = v.findViewById(R.id.tv_from);
        vh.to = v.findViewById(R.id.tv_to);
        vh.amount = v.findViewById(R.id.atv_amount);
        vh.time = v.findViewById(R.id.tv_time);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if(searchList.get(position).getFromAddress()!=null&&!searchList.get(position).getFromAddress().isEmpty()){
            holder.from.setText(searchList.get(position).getFromAddress()+" - "+searchList.get(position).getToAddress());
        }
        else{
            if(searchList.get(position).getToAddress().equals(Config.userName)){
                holder.from.setText("You created New Coins");
            }
            else{
                holder.from.setText(searchList.get(position).getToAddress()+" created New Coins");
            }

        }


        holder.time.setText(parseISO8601Date(searchList.get(position).getTimestamp()));
        holder.to.setText(searchList.get(position).getAmount());
        if((searchList.get(position).getFromAddress()!=null&&!searchList.get(position).getFromAddress().isEmpty())&&searchList.get(position).getFromAddress().equals(Config.userName)){
            holder.to.setTextColor(act.getResources().getColor(R.color.colorRed));
        }
        else if((searchList.get(position).getToAddress()!=null&&!searchList.get(position).getToAddress().isEmpty())&&searchList.get(position).getToAddress().equals(Config.userName)){
            holder.to.setTextColor(act.getResources().getColor(R.color.colorGreen));
        }
    }

    private String parseISO8601Date(String isoDate){

        DateTime dt = new DateTime( isoDate) ;

        DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM dd, yyyy - HH:mm:ss");
        String dtStr = fmt.print(dt);

//        SimpleDateFormat ISO8601DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
//        String date = isoDate.replaceAll("\\+0([0-9]){1}\\:00", "+0$100");
//        try {
//            return ISO8601DATEFORMAT.parse(date).getTime();
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return 0L;
//        }

        return dtStr;
    }

    public void notifyData(List<Transaction> data)
    {
        if (searchList != null) {
            searchList.clear();
            searchList.addAll(data);
        }
        else {
            searchList = data;
        }
        notifyDataSetChanged();
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public interface onItemClickListener{
        void onItemClick(Transaction transaction);
    }




}
