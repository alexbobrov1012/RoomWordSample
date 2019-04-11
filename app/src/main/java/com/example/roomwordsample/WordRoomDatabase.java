package com.example.roomwordsample;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

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
                           .addCallback(sRoomDatabaseCallback)
                                .build();
           }
        }
        return ourInstance;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(ourInstance).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final WordDao mDao;
        PopulateDbAsync(WordRoomDatabase db) {
            mDao = db.wordDao();
        }
        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Word word = new Word("Hello");
            mDao.insertWord(word);
            word = new Word("World");
            mDao.insertWord(word);
            word = new Word("Here's");
            mDao.insertWord(word);
            word = new Word("Me");
            mDao.insertWord(word);
            return null;
        }
    }
}
