package com.example.administrator.eatwhat.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.administrator.eatwhat.model.EatWhatModel;

/**
 * @author Administrator
 * @date 2017/11/8
 */
public class EatWhatViewModel extends ViewModel {
    private String userId;
    private LiveData<EatWhatModel> eatWhatModel;


    public void init(String userId) {
        this.userId = userId;
    }

    public LiveData<EatWhatModel> getEatWhatModel() {
        return eatWhatModel;
    }

}
