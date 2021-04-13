package com.dragonlfy.mmwallpaper.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "fav_table")
public class Favourite{
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "image")
    public String image;

    @ColumnInfo(name = "time")
    public String time;

    public Favourite(@NonNull String image,String time){
        this.image=image;
        this.time=time;
    }

}
