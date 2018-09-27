package com.kelevnor.geminidemo.Adapter;

/**
 * Created by kelevnor on 9/25/18.
 */

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelevnor.geminidemo.Model.address_info.Transaction;
import com.kelevnor.geminidemo.R;

import java.util.List;

/**
 * Created by kelevnor on 1/18/18.
 */

public class ADAPTER_BuddyListItem extends RecyclerView.Adapter<ADAPTER_BuddyListItem.ViewHolder>{
    private List<String> searchList;
    onItemClickListener listener;
    Activity act;



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        LinearLayout layout;
        LinearLayout outer;

        public ViewHolder(LinearLayout v) {
            super(v);
            layout = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ADAPTER_BuddyListItem(Activity act, List<String> searchList, onItemClickListener listener) {
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
                .inflate(R.layout.custom_list_item_buddylist, parent, false);
        ViewHolder vh = new ViewHolder(v);
        vh.name = v.findViewById(R.id.tv_name);
        vh.outer = v.findViewById(R.id.ll_outer);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.name.setText(searchList.get(position));
        holder.outer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(searchList.get(position));
            }
        });

    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public interface onItemClickListener{
        void onItemClick(String name);
    }

    public void notifyData(List<String> data)
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




}
