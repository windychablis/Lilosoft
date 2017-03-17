package com.chablis.lilosoft.model;

/**
 * Created by chablis on 2017/3/17.
 */

public class MapAddress {
    /**
     * area_id : 3d1614d562ad4e6ebf5530119d3e5f27
     * area_name : 朝阳区
     * area_code : 110105000000
     * area_level : 3
     * parent_id : 110100000000
     * china_initial : cyq
     * position : 114.277408,30.607629
     */

    private String area_id;
    private String area_name;
    private String area_code;
    private int area_level;
    private String parent_id;
    private String china_initial;
    private String position;

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public int getArea_level() {
        return area_level;
    }

    public void setArea_level(int area_level) {
        this.area_level = area_level;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getChina_initial() {
        return china_initial;
    }

    public void setChina_initial(String china_initial) {
        this.china_initial = china_initial;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "MapAddress{" +
                "area_id='" + area_id + '\'' +
                ", area_name='" + area_name + '\'' +
                ", area_code='" + area_code + '\'' +
                ", area_level=" + area_level +
                ", parent_id='" + parent_id + '\'' +
                ", china_initial='" + china_initial + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
