package com.customme.fullhdvideo.exitglories;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.customme.fullhdvideo.R;


public class MyDialogFragmentGlories extends DialogFragment {

    public int spanCount = 2;
    RecyclerViewAdapterGlories rcAdapter;
    public MyDialogFragmentGlories(){

    }
    @SuppressLint("ValidFragment")
    public MyDialogFragmentGlories(RecyclerViewAdapterGlories rcAdapter){
        this.rcAdapter = rcAdapter;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, getActivity().getApplicationInfo().theme);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setIcon(R.mipmap.icon)
                // Set Dialog Title
                .setTitle("Click to Download our other Apps!");
        builder.setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        LayoutInflater i = getActivity().getLayoutInflater();
        View v = i.inflate(R.layout.frg_splash_glories, null);
        ImageView clickImage = (ImageView) v.findViewById(R.id.logo);
        RecyclerView rView = (RecyclerView) v.findViewById(R.id.id_recyclerview);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(
                new GridLayoutManager(rView.getContext(), spanCount, RecyclerView.VERTICAL, false));
        rView.setAdapter(this.rcAdapter);
        clickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/developer?id=GloryApps"));
                startActivity(browserIntent);

            }
        });

        builder.setView(v);
        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
//        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);

    }


}