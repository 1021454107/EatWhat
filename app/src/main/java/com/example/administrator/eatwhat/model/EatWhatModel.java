package com.example.administrator.eatwhat.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * @author Administrator
 * @date 2017/11/8
 */
@Entity
public class EatWhatModel {
    @Id
    public long id;
    public String name = "";
    public String value = "";
    public String path = "";

    public EatWhatModel() {
    }

    public EatWhatModel(String name, String value, String path) {
        this.name = name;
        this.value = value;
        this.path = path;
    }
}
