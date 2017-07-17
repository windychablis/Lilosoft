package com.chablis.repair.model;

/**
 * Created by chablis on 2017/7/17.
 */

public class Area {

    /**
     * isParent : true
     * areaCode : 110105000000
     * id : 110105000000
     * open : true
     * name : 北京市朝阳区行政服务中心
     * pId : 110100000000
     */

    private boolean isParent;
    private String areaCode;
    private String id;
    private boolean open;
    private String name;
    private String pId;

    public boolean isIsParent() {
        return isParent;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }
}
