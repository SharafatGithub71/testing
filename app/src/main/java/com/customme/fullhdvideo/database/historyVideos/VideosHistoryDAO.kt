package com.customme.fullhdvideo.database.historyVideos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.customme.fullhdvideo.database.historyVideos.VideosHistory

@Dao
interface VideosHistoryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(videosHistory: VideosHistory)

    @Delete
    fun delete(videosHistory: VideosHistory)

    @Query("SELECT * FROM videos_history ORDER BY video_path DESC")
    fun getAllHistoryVideos(): LiveData<List<VideosHistory>>

    @Query("DELETE FROM videos_history")
    fun clear()
    @Query("UPDATE videos_history SET video_path= :path , video_name= :name WHERE video_duration= :duration")
    fun update(path: String, name: String,duration:String)
}