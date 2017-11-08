package com.example.administrator.eatwhat.room;

import android.arch.persistence.room.RoomDatabase;

import com.example.administrator.eatwhat.room.dao.EatWhatDao;

/**
 * @author Administrator
 * @date 2017/11/8
 */
//@Database(entities = {EatWhatModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EatWhatDao eatWhatDao();
}
