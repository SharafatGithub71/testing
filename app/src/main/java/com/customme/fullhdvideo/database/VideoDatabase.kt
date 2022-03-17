package com.customme.fullhdvideo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.customme.fullhdvideo.database.favoriteVideos.FavoriteVideo
import com.customme.fullhdvideo.database.favoriteVideos.FavoriteVideoDAO
import com.customme.fullhdvideo.database.historyVideos.VideosHistory
import com.customme.fullhdvideo.database.historyVideos.VideosHistoryDAO

@Database(entities = [VideosHistory::class, FavoriteVideo::class], version = 1, exportSchema = false)
abstract class VideoDatabase : RoomDatabase() {
    abstract val favoriteVideoDAO: FavoriteVideoDAO
    abstract val videosHistoryDAO: VideosHistoryDAO

    companion object {
        @Volatile
        private var INSTANCE: VideoDatabase? = null
       @JvmStatic fun getInstance(context: Context): VideoDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            VideoDatabase::class.java,
                            "videos_history_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}