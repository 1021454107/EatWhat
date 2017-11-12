package com.example.administrator.eatwhat.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by hbysd on 2017/11/4.
 */

@Entity
public class EatWhatModel {
    @Id
    public long id;

    public String title = "", value = "";
    public String img_url = "";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public EatWhatModel(String title) {
        this.title = title;
    }

    public EatWhatModel() {
    }


    public EatWhatModel(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public EatWhatModel(String title, String value, String img_url) {
        this.title = title;
        this.value = value;
        this.img_url = img_url;
    }
}