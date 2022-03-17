package com.customme.fullhdvideo.view_controllersGlory;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.customme.fullhdvideo.AudioListActivityGlory;
import com.customme.fullhdvideo.VideoListActGlory;
import com.customme.fullhdvideo.dataGlory.MediaFolder;
import com.customme.fullhdvideo.R;

import java.util.ArrayList;

public class FolderAdapter extends ArrayAdapter<MediaFolder> {
    Activity context;
    String type;

    static class ViewHolder {
        TextView folderName;
        TextView videos;

        public ViewHolder(View view) {
            this.folderName = (TextView) view.findViewById(R.id.folder_name);
            this.videos = (TextView) view.findViewById(R.id.no_of_videos);
        }
    }

    public FolderAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull ArrayList<MediaFolder> objects, String type) {
        super(context, resource, objects);
        this.type = type;
        this.context = context;
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(this.context).inflate( R.layout.folder_list_item_glory, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        final MediaFolder mediaFolder = (MediaFolder) getItem(position);
        viewHolder.folderName.setText(mediaFolder.getDisplayName());
        String videoString = " " + this.type;
        if (mediaFolder.getNumberOfMediaFiles() > 1) {
            videoString = videoString + "s";
        }
        viewHolder.videos.setText(mediaFolder.getNumberOfMediaFiles() + videoString);
        view.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(FolderAdapter.this.context, VideoListActGlory.class);
                Log.e("folderAct", "onClickListener");
                if (FolderAdapter.this.type.contains("audio")) {
                    Log.e("folderAct", "onClickListener audio");
                    i = new Intent(FolderAdapter.this.context, AudioListActivityGlory.class);
                }
                i.putExtra("FOLDER_PATH", mediaFolder.getPath());
                FolderAdapter.this.context.startActivityForResult(i, 1002);
            }
        });
        return view;
    }
}
