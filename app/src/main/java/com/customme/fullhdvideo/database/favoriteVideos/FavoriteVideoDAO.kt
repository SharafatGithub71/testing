package com.customme.fullhdvideo.database.favoriteVideos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.customme.fullhdvideo.database.favoriteVideos.FavoriteVideo

@Dao
interface FavoriteVideoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteVideo: FavoriteVideo)

    @Delete
    fun delete(favoriteVideo: FavoriteVideo)

    @Query("Select * FROM favorite_videos WHERE video_path= :path")
    fun get(path: String): FavoriteVideo

    //check call in recylerview at a time how many videos check for Favorite or check as scroll
    @Query("SELECT * FROM favorite_videos ORDER BY video_path DESC")
    fun getAllFavoriteVideos(): LiveData<List<FavoriteVideo>>

    @Query("DELETE FROM favorite_videos")
    fun clear()

    @Query("UPDATE favorite_videos SET video_path= :path , video_name= :name WHERE video_duration= :duration")
    fun update(path: String, name: String,duration:String)

}