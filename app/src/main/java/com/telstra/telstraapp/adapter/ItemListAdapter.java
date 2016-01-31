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

import java.util.List;

/**
 * Created by Thahaseen on 1/31/2016.
 * RecyclerView Adapters to bind view with positions.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemHolder> {

    private List<Item> mDataset;
    private Context context;

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

    public ItemListAdapter(List<Item> myDataset , Context context) {
        this.mDataset = myDataset;
        this.context = context;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        ItemHolder dataObjectHolder = new ItemHolder(view);
        return dataObjectHolder;
    }

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

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}