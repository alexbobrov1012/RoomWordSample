package com.example.roomwordsample;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;

public interface WordDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertWord (Word word);
    @Update
    void updateWord (Word word);
    @Query ("delete from word_table")
    void deleteAll ();
    @Query ("select * from word_table order by word asc")
    LiveData<List<Word>> getAllWords ();
}
