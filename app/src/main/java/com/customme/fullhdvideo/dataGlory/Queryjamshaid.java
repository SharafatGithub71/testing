package com.customme.fullhdvideo.dataGlory;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Queryjamshaid {
    private Context context;
    private int count = 0;
    private Cursor cursor;
    List<Itemjamshaid> videoItems;

    public Queryjamshaid(Context context) {
        this.context = context;
    }

    public List<Itemjamshaid> getAllVideo() {
        String selection = null;

        String[] projection = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.ARTIST,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE
        };

        cursor = context.getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                MediaStore.Video.Media.DEFAULT_SORT_ORDER);

        videoItems = new ArrayList<>();
        Itemjamshaid videoItemWhats;
        while (cursor.moveToNext()) {
            videoItemWhats = new Itemjamshaid();
            videoItemWhats.set_ID(cursor.getString(cursor.getColumnIndex(projection[0])));
            videoItemWhats.setARTIST(cursor.getString(cursor.getColumnIndex(projection[1])));
            videoItemWhats.setTITLE(cursor.getString(cursor.getColumnIndex(projection[2])));
            videoItemWhats.setDATA(cursor.getString(cursor.getColumnIndex(projection[3])));
            videoItemWhats.setDISPLAY_NAME(cursor.getString(cursor.getColumnIndex(projection[4])));
            videoItemWhats.setDURATION(cursor.getString(cursor.getColumnIndex(projection[5])));
            videoItemWhats.setSIZE(cursor.getLong(cursor.getColumnIndex(projection[6])));
            this.videoItems.add(videoItemWhats);
        }
        return this.videoItems;
    }

    public int getVideoCount() {
        int count = 0;
        count = (getAllVideo()).size();
        return count;

    }

    public static void showToast(Context context, String showText) {
        Toast.makeText(context, showText, Toast.LENGTH_SHORT).show();
    }

    public static boolean setFileName(String existingFileName, String newFileName) {

        File directory = Environment.getExternalStorageDirectory();
        if (directory.exists()) {
            File from = new File(directory, existingFileName);
            String ext = from.getAbsolutePath().substring(from.getAbsolutePath().lastIndexOf("."));
            File to = new File(directory, newFileName.trim() + ext);
            if (from.exists()) {
                if (from.renameTo(to)) {
                    return true;
                }
            } else {
                return false;
            }
            Log.i("Directory is", directory.toString());
            Log.i("From path is", from.toString());
            Log.i("To path is", to.toString());
        }
        return false;
    }

}

