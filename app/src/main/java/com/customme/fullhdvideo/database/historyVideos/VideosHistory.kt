package com.customme.fullhdvideo.database.historyVideos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videos_history")
data class VideosHistory(

        @PrimaryKey
        @ColumnInfo(name = "video_path")
        var path: String,

        @ColumnInfo(name = "video_name")
        var videoName: String,

        @ColumnInfo(name = "video_duration")
        var videoDuration: String,

        @ColumnInfo(name = "video_size")
        var videoSize: Long
)