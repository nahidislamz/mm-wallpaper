package com.dragonlfy.mmwallpaper.database;

import android.content.Context;
import android.view.ViewDebug;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = Favourite.class,version = 1)

public abstract class FavouriteRoomDatabase extends RoomDatabase {

    public abstract FavouriteDao dao();
    private static FavouriteRoomDatabase instance;
    public static FavouriteRoomDatabase getDatabase(Context context){
        if(instance==null){
            synchronized (FavouriteRoomDatabase.class){

                if(instance==null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                    FavouriteRoomDatabase.class,
                    "fav_data").build();
                }
            }
        }

        return instance;
    }

}
