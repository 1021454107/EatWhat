package com.example.administrator.eatwhat.model;

/**
 * @author Administrator
 * @date 2017/11/8
 */
//@Entity(indices = {@Index(value = {"name_name"}, unique = true)})
public class EatWhatModel {
//    @PrimaryKey
    public int id;
//    @ColumnInfo(name = "name_name")
    public String name;
    public String value;
//    @ColumnInfo(name = "path_name")
    public String path;

    public EatWhatModel() {
    }

    public EatWhatModel(String name, String value, String path) {
        this.name = name;
        this.value = value;
        this.path = path;
    }
}
