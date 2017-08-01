package com.chablis.lilosoft.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by chablis on 2017/3/28.
 */

public class Affair implements Serializable {

    /**
     * item_id : 740c87845dc7452e853880688da8f90c
     * item_no : 017001
     * item_show_no :
     * item_name : 公章备案审批
     * dept_id : 3acfab14052841189ec516e9f6dbc0e5
     * is_use : 0
     * remark :
     * area_code_id :
     * legal_basis :
     * create_time : 2014-10-9 13:42:50
     * version : 1
     * is_public : 0
     */

    private String item_id;
    private String item_no;
    private String item_show_no;
    private String item_name;
    private String dept_id;
    private String is_use;
    private String remark;
    private String area_code_id;
    private String legal_basis;
    private String create_time;
    private int version;
    private String is_public;
    private ArrayList<AffairItem> affairItems;

    public ArrayList<AffairItem> getAffairItems() {
        return affairItems;
    }

    public void setAffairItems(ArrayList<AffairItem> affairItems) {
        this.affairItems = affairItems;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getItem_show_no() {
        return item_show_no;
    }

    public void setItem_show_no(String item_show_no) {
        this.item_show_no = item_show_no;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getDept_id() {
        return dept_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }

    public String getIs_use() {
        return is_use;
    }

    public void setIs_use(String is_use) {
        this.is_use = is_use;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getArea_code_id() {
        return area_code_id;
    }

    public void setArea_code_id(String area_code_id) {
        this.area_code_id = area_code_id;
    }

    public String getLegal_basis() {
        return legal_basis;
    }

    public void setLegal_basis(String legal_basis) {
        this.legal_basis = legal_basis;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getIs_public() {
        return is_public;
    }

    public void setIs_public(String is_public) {
        this.is_public = is_public;
    }

    @Override
    public String toString() {
        return "Affair{" +
                "item_id='" + item_id + '\'' +
                ", item_no='" + item_no + '\'' +
                ", item_show_no='" + item_show_no + '\'' +
                ", item_name='" + item_name + '\'' +
                ", dept_id='" + dept_id + '\'' +
                ", is_use='" + is_use + '\'' +
                ", remark='" + remark + '\'' +
                ", area_code_id='" + area_code_id + '\'' +
                ", legal_basis='" + legal_basis + '\'' +
                ", create_time='" + create_time + '\'' +
                ", version=" + version +
                ", is_public='" + is_public + '\'' +
                ", affairItems=" + affairItems +
                '}';
    }
}
