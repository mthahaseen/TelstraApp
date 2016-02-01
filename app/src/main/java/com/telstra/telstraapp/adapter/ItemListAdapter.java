package com.telstra.telstraapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.telstra.telstraapp.R;
import com.telstra.telstraapp.model.Item;
import com.telstra.telstraapp.model.ItemDetail;

import java.util.List;

/**
 * Created by Thahaseen on 1/31/2016.
 * RecyclerView Adapters to bind view with positions.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemHolder> {

    private List<ItemDetail> mDataset;
    private Context context;

    /*
    ViewHolder for the views present in the inflated layout
    */
    public static class ItemHolder extends RecyclerView.ViewHolder{
        TextView txtTitle;
        TextView txtDesc;
        ImageView imgItem;
        public ItemHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtItemTitle);
            txtDesc = (TextView) itemView.findViewById(R.id.txtItemDesc);
            imgItem = (ImageView) itemView.findViewById(R.id.imgItemImage);
        }
    }

    public ItemListAdapter(List<ItemDetail> myDataset , Context context) {
        this.mDataset = myDataset;
        this.context = context;
    }

    /*
    Creates a new RecyclerView.ViewHolder and initializes some private fields to be used by RecyclerView.
    */
    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        ItemHolder dataObjectHolder = new ItemHolder(view);
        return dataObjectHolder;
    }

    /*
    Called by RecyclerView to display the data at the specified position.
    */
    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.txtTitle.setText(mDataset.get(position).getTitle());
        holder.txtDesc.setText(mDataset.get(position).getDescription());
        if(!(mDataset.get(position).getImageHref() == null || mDataset.get(position).getImageHref().equals(""))){
            Picasso.with(context)
                    .load(mDataset.get(position).getImageHref())
                    .placeholder(R.drawable.image_placeholder)
                    .into(holder.imgItem);
        }
    }

    /*
    Returns the total number of items in the data set hold by the adapter.
    */
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}