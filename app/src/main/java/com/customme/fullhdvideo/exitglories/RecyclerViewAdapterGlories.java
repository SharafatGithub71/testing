package com.customme.fullhdvideo.exitglories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.customme.fullhdvideo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapterGlories extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<ItemObjectGlories> itemList;
    private Context context;
    public mediaPlayerCallback callBackInstanse;

    public RecyclerViewAdapterGlories(Context context, List<ItemObjectGlories> itemList, mediaPlayerCallback adapterCallback) {
        this.itemList = itemList;
        this.context = context;
        this.callBackInstanse = adapterCallback;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list_glories, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView, context, callBackInstanse);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.countryName.setText(itemList.get(position).getName());
        holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());
        Picasso.get().load(itemList.get(position).getPhoto()).fit()
                .placeholder(R.drawable.ic_menu_settings)
                .error(R.drawable.ic_menu_send)
                .into(holder.countryPhoto);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
