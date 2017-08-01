package com.chablis.lilosoft.model;

import java.io.Serializable;

/**
 * Created by chablis on 2017/3/28.
 */

public class Dept implements Serializable {
    /**
     * dept_id : 1
     * parent_id : -1
     * simply_name : 朝阳区
     * is_order : 1
     * dept_code : CODE
     * dept_name : 朝阳区
     * is_use : 0
     * is_bus : 1
     * remark : 组织架构
     * dept_icon :
     * dept_name_py : CYQQ
     * area_code : 110105000000
     * position : 116.331398,39.897445
     */

    private String dept_id;
    private String parent_id;
    private String simply_name;
    private int is_order;
    private String dept_code;
    private String dept_name;
    private String is_use;
    private String is_bus;
    private String remark;
    private String dept_icon;
    private String dept_name_py;
    private String area_code;
    private String position;

    public String getDept_id() {
        return dept_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getSimply_name() {
        return simply_name;
    }

    public void setSimply_name(String simply_name) {
        this.simply_name = simply_name;
    }

    public int getIs_order() {
        return is_order;
    }

    public void setIs_order(int is_order) {
        this.is_order = is_order;
    }

    public String getDept_code() {
        return dept_code;
    }

    public void setDept_code(String dept_code) {
        this.dept_code = dept_code;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getIs_use() {
        return is_use;
    }

    public void setIs_use(String is_use) {
        this.is_use = is_use;
    }

    public String getIs_bus() {
        return is_bus;
    }

    public void setIs_bus(String is_bus) {
        this.is_bus = is_bus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDept_icon() {
        return dept_icon;
    }

    public void setDept_icon(String dept_icon) {
        this.dept_icon = dept_icon;
    }

    public String getDept_name_py() {
        return dept_name_py;
    }

    public void setDept_name_py(String dept_name_py) {
        this.dept_name_py = dept_name_py;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "dept_id='" + dept_id + '\'' +
                ", parent_id='" + parent_id + '\'' +
                ", simply_name='" + simply_name + '\'' +
                ", is_order=" + is_order +
                ", dept_code='" + dept_code + '\'' +
                ", dept_name='" + dept_name + '\'' +
                ", is_use='" + is_use + '\'' +
                ", is_bus='" + is_bus + '\'' +
                ", remark='" + remark + '\'' +
                ", dept_icon='" + dept_icon + '\'' +
                ", dept_name_py='" + dept_name_py + '\'' +
                ", area_code='" + area_code + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
