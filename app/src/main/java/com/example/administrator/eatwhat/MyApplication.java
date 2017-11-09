package com.example.administrator.eatwhat;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.example.administrator.eatwhat.model.MyObjectBox;

import io.objectbox.BoxStore;

/**
 * Created by Administrator on 2017/11/9.
 */

public class MyApplication extends Application {
    private static BoxStore mBoxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        mBoxStore = MyObjectBox.builder().androidContext(this).build();
    }


    public BoxStore getBoxStore(){
        return mBoxStore;
    }
}
