package com.example.roomwordsample;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Word.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {
    private static volatile WordRoomDatabase ourInstance;

    public abstract WordDao wordDao();

    public static WordRoomDatabase getInstance(final Context context) {
        if (ourInstance == null) {
           synchronized (WordRoomDatabase.class) {
               if (ourInstance == null)
                   ourInstance = Room.databaseBuilder(context.getApplicationContext(),
                       WordRoomDatabase.class, "word_database")
                       .build();
           }
        }
        return ourInstance;
    }
}
