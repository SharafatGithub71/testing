package com.customme.fullhdvideo.database.favoriteVideos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_videos")
data class FavoriteVideo(

        @PrimaryKey
        @ColumnInfo(name = "video_path")
        var videoPath: String,

        @ColumnInfo(name = "video_name")
        var videoName: String,

        @ColumnInfo(name = "video_duration")
        var videoDuration: String,

        @ColumnInfo(name = "video_size")
        var videoSize: Long
)