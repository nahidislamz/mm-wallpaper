package com.dragonlfy.mmwallpaper.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface FavouriteDao {

    @Query("select image from fav_table order by time desc")
    List<String> getAllFavourite();

    @Insert
    void insertFavourite(Favourite favourtie);

    @Query("delete from fav_table where image = :id")
    void deleteFavourite(String id);

    @Query("select * from fav_table where image = :id")
    Favourite isExist(String id);
}
