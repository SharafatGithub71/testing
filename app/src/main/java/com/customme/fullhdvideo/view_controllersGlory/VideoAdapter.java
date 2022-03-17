package com.customme.fullhdvideo.view_controllersGlory;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.customme.fullhdvideo.VideoViewActGlory;
import com.customme.fullhdvideo.dataGlory.Itemjamshaid;
import com.customme.fullhdvideo.dataGlory.Queryjamshaid;
import com.customme.fullhdvideo.database.VideoDatabase;
import com.customme.fullhdvideo.helperClassesGlory.FileDataHelper;
import com.customme.fullhdvideo.R;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.customme.fullhdvideo.database.favoriteVideos.FavoriteVideo;
import com.customme.fullhdvideo.database.historyVideos.VideosHistory;
import com.customme.fullhdvideo.helperClassesGlory.UtilTime;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MenuItem.OnMenuItemClickListener {
    private Context context;
    public List<Itemjamshaid> videoListFiltered;
    public List<Itemjamshaid> videoItemList;
    public List<FavoriteVideo> listFavoriteVideos;
    public List<VideosHistory> listHistoryVideos;
    public VideoDatabase database;
    private static final int MENU_ITEM_VIEW_TYPE = 0;

    private static final int UNIFIED_NATIVE_AD_VIEW_TYPE = 1;
    int currentPostition = 0;

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(context, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.video_menu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(this::onMenuItemClick);
    }

    public VideoAdapter(Context c, List<Itemjamshaid> videoItemList, VideoDatabase database,
                        List<FavoriteVideo> listFavoriteVideos, List<VideosHistory> listHistoryVideos) {
        context = c;
        this.videoItemList = videoItemList;
        videoListFiltered = videoItemList;
        this.listFavoriteVideos = listFavoriteVideos;
        this.listHistoryVideos = listHistoryVideos;
        this.database = database;
        if (videoListFiltered != null)
            Log.e("historyTest", "videoAdapterCostructorListSize: " + videoListFiltered.size());
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (videoListFiltered != null) {

            return videoListFiltered.size();
        } else return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Log.e("historyTest", "OnCreateViewHolder ");
        switch (viewType) {
//            case UNIFIED_NATIVE_AD_VIEW_TYPE:
//                View unifiedNativeLayoutView = LayoutInflater.from(
//                        viewGroup.getContext()).inflate(R.layout.ad_unified_list,
//                        viewGroup, false);
//                return new UnifiedNativeAdViewHolder(unifiedNativeLayoutView);
            case MENU_ITEM_VIEW_TYPE:
                // Fall through.
            default:
                View menuItemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.list_single_glory, viewGroup, false);
                return new MenuItemViewHolder(menuItemLayoutView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.e("historyTest", "OnBindViewHolder ");
        int viewType = getItemViewType(position);
        switch (viewType) {
            case UNIFIED_NATIVE_AD_VIEW_TYPE:
//                    UnifiedNativeAd nativeAd = (UnifiedNativeAd) videoListFiltered.get(position);
//                    populateNativeAdView(nativeAd, ((UnifiedNativeAdViewHolder) holder).getAdView());
                Log.e("historyTest", "OnBindViewHolder AD TYPE ");
                break;
            case MENU_ITEM_VIEW_TYPE:
                // fall through
            default:
                viewBindigs((MenuItemViewHolder) holder, position);


        }
    }

    private void viewBindigs(@NonNull MenuItemViewHolder holder, int position) {
        Log.e("historyTest", "OnBindViewHolder DEFAULT ");
        MenuItemViewHolder menuItemHolder = holder;
        Itemjamshaid videoItem = (Itemjamshaid) videoListFiltered.get(position);
        long dur;
        String songTime = null;

        try {

            dur = Long.parseLong(videoItem.getDURATION());
//                        int hrs = (int) (dur / 3600000);
//                        int mns = (int) ((dur / 60000) % 60000);
//                        int scs = (int) (dur % 60000 / 1000);
//                        songTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
            songTime = UtilTime.millisToTime(dur);

            menuItemHolder.txtTitle.setText(videoItem.getDISPLAY_NAME());
            Spanned styledText = Html.fromHtml(context.getString(R.string.video_size, FileDataHelper.getFileSize(videoItem.getSize())));
            menuItemHolder.txtSize.setText(styledText);
            menuItemHolder.txtduration.setText(songTime);

            if (checkFavorite(videoItem.getDATA())) {
                menuItemHolder.ivFavorite.setImageResource(R.drawable.ic_favorite_24);
            } else {
                menuItemHolder.ivFavorite.setImageResource(R.drawable.ic_un_favorite_24);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Glide.with(context)
                .load(videoItem.getDATA())
                .into(menuItemHolder.thumbImage);

        menuItemHolder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPostition = position;

                showPopup(view);
            }
        });
        String finalSongTime = songTime;
        menuItemHolder.ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFavorite(videoItem.getDATA(), videoItem.getDISPLAY_NAME(), videoItem.getDURATION(), videoItem.getSize());
            }
        });
    }

    public boolean checkFavorite(String path) {

        for (FavoriteVideo favoriteVideo : listFavoriteVideos) {
            if (favoriteVideo.getVideoPath().contentEquals(path)) {
                return true;


            }
        }
        return false;
    }

    public void setFavorite(String path, String name, String time, Long size) {

        Log.e("AllVideoDatabaseTest", " setFavorite " + path + " :" + name + " :" + time + " :" + size);

        for (FavoriteVideo favoriteVideo : listFavoriteVideos) {
            if (favoriteVideo.getVideoPath().contentEquals(path)) {

                AsyncTask.execute(() -> database.getFavoriteVideoDAO()
                        .delete(favoriteVideo));
                return;
            }
        }
        AsyncTask.execute(() -> database.getFavoriteVideoDAO()
                .insert(new FavoriteVideo(path, name, time, size)));
    }

    public void updateFileNameInDatabase(String oldPath, String newPath, String newName) {
        for (FavoriteVideo favoriteVideo : listFavoriteVideos) {
            if (favoriteVideo.getVideoPath().contentEquals(oldPath)) {
                favoriteVideo.setVideoPath(newPath);
                favoriteVideo.setVideoName(newName);

                Log.e("AllVideoDatabaseTest", " newPath: " + newPath + " newNane: " + newName);
//                    Log.e("AllVideoDatabaseTest", " faID :"+favoriteVideo.getNightId());
                AsyncTask.execute(() -> database.getFavoriteVideoDAO()
                        .update(favoriteVideo.getVideoPath(), favoriteVideo.getVideoName(), favoriteVideo.getVideoDuration()));

            }
        }
        for (VideosHistory historyVideo : listHistoryVideos) {
            if (historyVideo.getPath().contentEquals(oldPath)) {
                historyVideo.setPath(newPath);
                historyVideo.setVideoName(newName);
                Log.e("AllVideoDatabaseTest", " (historyVideo.getPath().contentEquals(oldPath)) ");
                AsyncTask.execute(() -> database.getVideosHistoryDAO()
                        .update(historyVideo.getPath(), historyVideo.getVideoName(), historyVideo.getVideoDuration()));

            }
        }
    }

    public void deleteFileNameInDatabase(String oldPath) {
        for (FavoriteVideo favoriteVideo : listFavoriteVideos) {
            if (favoriteVideo.getVideoPath().contentEquals(oldPath)) {
//                    Log.e("AllVideoDatabaseTest", " faID :"+favoriteVideo.getNightId());
                AsyncTask.execute(() -> database.getFavoriteVideoDAO()
                        .delete(favoriteVideo));

            }
        }
        for (VideosHistory historyVideo : listHistoryVideos) {
            if (historyVideo.getPath().contentEquals(oldPath)) {

                AsyncTask.execute(() -> database.getVideosHistoryDAO()
                        .delete(historyVideo));

            }
        }
    }

    @Override
    public int getItemViewType(int position) {

        Object recyclerViewItem = videoListFiltered.get(position);
        if (recyclerViewItem instanceof UnifiedNativeAd) {
            return UNIFIED_NATIVE_AD_VIEW_TYPE;
        }
        return MENU_ITEM_VIEW_TYPE;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        Log.e("AllmenuCLick", " onMenuItemClick ");
        switch (menuItem.getItemId()) {
            case R.id.menu_delete:
                deleteVideoDialoge();
                return true;
            case R.id.menu_rename:
                renameVideoDialoge();
                return true;
            case R.id.menu_share:
                shareVideo();
                return true;
            default:
                return false;
        }

    }

    private void shareVideo() {
        Itemjamshaid videoItem = (Itemjamshaid) videoListFiltered.get(currentPostition);
        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
        File videoFile = new File(videoItem.getDATA());

        if (videoFile.exists()) {
            intentShareFile.setType("video/*");
//            intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(videoFile));
            intentShareFile.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context,
                    context.getApplicationContext().getPackageName() + ".provider", videoFile));

            intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
                    "Sharing File...");

            context.startActivity(Intent.createChooser(intentShareFile, "Share Video"));
        }
    }

    private void renameVideoDialoge() {
        Log.e("AllmenuCLick", " rename ");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.rename_file, null);
        AlertDialog materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context)
                .setTitle("Rename?")
                .setView(dialogView)
                .setCancelable(false)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Rename", null)
                .show();


        Button btnPositive = materialAlertDialogBuilder.getButton(AlertDialog.BUTTON_POSITIVE);
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    TextInputEditText textInputEditText = dialogView.findViewById(R.id.etRename);
                    Itemjamshaid videoItem = (Itemjamshaid) videoListFiltered.get(currentPostition);
                    Log.e("AllmenuCLick", " onClickPositiveButton " + textInputEditText.getText().toString());
                    if (textInputEditText.getText() != null && textInputEditText.getText().toString().length() > 0) {
                        String newName = textInputEditText.getText().toString();
                        boolean isChanged = setFileName(videoItem.getDATA(), videoItem.getDISPLAY_NAME(), newName);
                        if (isChanged) {
                            Queryjamshaid.showToast(context, "Video Renamed Successfully!");
                        } else {

                            Queryjamshaid.showToast(context, "Unfortunately Video not Renamed!");
                        }
                        materialAlertDialogBuilder.dismiss();
                    } else {
                        Queryjamshaid.showToast(context, "Kindly write valid name!");
                    }
                }

            }
        });
    }

    private void deleteVideoDialoge() {
        new MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
                .setTitle("Delete Video?")

//                            .setMessage("")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Itemjamshaid videoItem = (Itemjamshaid) videoListFiltered.get(currentPostition);
                        File from = new File(videoItem.getDATA());
                        if (from.exists()) {
                            boolean isDelete = from.delete();
                            if (isDelete) {
                                Queryjamshaid.showToast(context, "Video Delete Successfully!");
                                Log.i("AllmenuCLick", "from.exists() not exist");
                                Intent intent1 = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                intent1.setData(Uri.fromFile(from));
                                context.sendBroadcast(intent1);
                                videoListFiltered.remove(currentPostition);
                                deleteFileNameInDatabase(videoItem.getDATA());
                                notifyDataSetChanged();

                            } else {

                                Queryjamshaid.showToast(context, "Unfortunately Video not Delete!");
                            }
                        }
                    }
                })
                .show();
    }

    public class MenuItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtSize;
        TextView txtduration;
        ImageView thumbImage;
        ImageView ivMore, ivFavorite;
        Group group;

        MenuItemViewHolder(View view) {
            super(view);
            txtTitle = view.findViewById(R.id.txtTitle);
            txtSize = view.findViewById(R.id.txtSize);
            txtduration = view.findViewById(R.id.txtduration);
            thumbImage = view.findViewById(R.id.imgIcon);
            ivMore = view.findViewById(R.id.imgBtnListMore);
            ivFavorite = view.findViewById(R.id.imgFavoriteVideoItem);
            group = view.findViewById(R.id.group);
//                menuItemDescription = (TextView) view.findViewById(R.id.menu_item_description);Group group = findViewById(R.id.group);


            int refIds[] = group.getReferencedIds();
//                Log.e("AllVideoDatabaseTest", "path: " + refIds.length + " ids:" + refIds);
            for (int id : refIds) {
                view.findViewById(id).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        onItemClickPerform(getAdapterPosition());
                    }
                });
            }

        }


    }


    public void filter(String charSequence) {
//            videoListFiltered.clear();
        if (charSequence.isEmpty()) {
            videoListFiltered = videoItemList;
        } else {
            List<Itemjamshaid> filteredList = new ArrayList<>();
            for (Itemjamshaid row : videoItemList) {
                // name match condition. this might differ depending on your requirement
                // here we are looking for name or phone number match
                if (row.getDISPLAY_NAME().toLowerCase().contains(charSequence.toLowerCase())) {
                    filteredList.add(row);
                }
            }

            videoListFiltered = filteredList;
        }
        notifyDataSetChanged();

    }

    private void populateNativeAdView(UnifiedNativeAd nativeAd,

                                      UnifiedNativeAdView adView) {
        // Some assets are guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        NativeAd.Image icon = nativeAd.getIcon();

        if (icon == null) {
            adView.getIconView().setVisibility(View.INVISIBLE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(icon.getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // Assign native ad object to the native view.
        adView.setNativeAd(nativeAd);
    }

    public boolean setFileName(String oldPath, String existingFileName, String newFileName) {
        File directory = new File(oldPath.substring(0, oldPath.lastIndexOf("/")));
        Log.i("AllmenuCLick", "setFile: " + directory);
        if (directory.exists()) {
            File from = new File(directory, existingFileName);
            String ext = from.getAbsolutePath().substring(from.getAbsolutePath().lastIndexOf("."));
            File to = new File(directory, newFileName.trim() + ext);

            Log.i("AllmenuCLick", directory.toString());
            Log.i("AllmenuCLick", from.toString());
            Log.i("AllmenuCLick", to.toString());
            if (from.exists()) {
                if (from.renameTo(to)) {
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.fromFile(to));
                    context.sendBroadcast(intent);
                    if (from.exists()) {
                        from.delete();
                    } else {
                        Log.i("AllmenuCLick", "from.exists() not exist");
                        Intent intent1 = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        intent1.setData(Uri.fromFile(from));
                        context.sendBroadcast(intent1);
                    }
                    updateFileNameInDatabase(oldPath, to.getPath(), newFileName + ext);
                    videoListFiltered.get(currentPostition).setDATA(to.getPath());
                    videoListFiltered.get(currentPostition).setDISPLAY_NAME(newFileName + ext);
                    notifyDataSetChanged();
                    return true;
                }
            } else {
                Log.i("AllmenuCLick", "from not exist");


                return false;
            }

        } else {
            Log.i("AllmenuCLick", "directory not exist");


            return false;
        }
        return false;
    }

    public void onItemClickPerform(int position) {
        String songTime = "";
        System.gc();
        Itemjamshaid videoItem = videoListFiltered.get(position);

        long dur = 0;
        try {
            dur = Long.parseLong(videoItem.getDURATION());
            songTime = UtilTime.millisToTime(dur);

        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
//        long mns = (dur % 3600000) / 60000;
//        long scs = ((dur % 3600000) % 60000) / 1000;
//        if (dur / 3600000 > 0) {
//            songTime = String.format("%02d:%02d:%02d", (dur / 3600000), (mns), (scs));
//        } else {
//            songTime = String.format("%02d:%02d", (mns), (scs));
//        }

        if (videoItem.getDATA() != null) {
            AsyncTask.execute(() -> database.getVideosHistoryDAO()
                    .insert(new VideosHistory(videoItem.getDATA(), videoItem.getDISPLAY_NAME(), videoItem.getDURATION(), videoItem.getSize())));
        }
        Intent intent = new Intent(context, VideoViewActGlory.class);
        Log.e("OnClickTest", "DisplayName: " + videoItem.getDISPLAY_NAME());
        intent.putExtra("videofilename", videoItem.getDATA());
        intent.putExtra("VideoDisplayName", videoItem.getDISPLAY_NAME());
        intent.putExtra("durations", songTime);
        intent.putExtra("pos2", "pos2");
        context.startActivity(intent);

    }
}

