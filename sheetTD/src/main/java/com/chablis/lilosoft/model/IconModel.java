package com.chablis.lilosoft.model;
/**
 * Created by chablis on 2017/1/16.
 */

public class IconModel {
    private int res;
    private String name;

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IconModel() {
    }

    public IconModel(int res, String name) {
        this.res = res;
        this.name = name;
    }

    public IconModel(String name) {
        this.name = name;
    }
}
