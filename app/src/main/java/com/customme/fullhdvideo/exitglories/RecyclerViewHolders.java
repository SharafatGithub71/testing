package com.customme.fullhdvideo.exitglories;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.customme.fullhdvideo.R;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView countryName;
    public ImageView countryPhoto;
    public Context cnt;
    public mediaPlayerCallback PlayerCallback;

    public RecyclerViewHolders(View itemView, Context context, mediaPlayerCallback Callback) {
        super(itemView);
        this.cnt = context;
        itemView.setOnClickListener(this);
        countryName = (TextView)itemView.findViewById(R.id.country_name);
        countryPhoto = (ImageView)itemView.findViewById(R.id.country_photo);
        this.PlayerCallback = Callback;
    }

    @Override
public void onClick(View view) {
//        Toast.makeText(view.getContext(), "App Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        PlayerCallback.mediaPlayerPosition(getAdapterPosition());

    }
}