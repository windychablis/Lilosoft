package com.chablis.repair.model;

/**
 * Created by chablis on 2017/7/13.
 */

public class User {


    /**
     * User_ID : 5ddb9afc2eb64fff9d149a0f505851aa
     * is_admin : false
     * LOGIN_ID : 81bdc336f068493cba5c4c44537af355
     */

    private String User_ID;
    private boolean is_admin;
    private String LOGIN_ID;

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String User_ID) {
        this.User_ID = User_ID;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public String getLOGIN_ID() {
        return LOGIN_ID;
    }

    public void setLOGIN_ID(String LOGIN_ID) {
        this.LOGIN_ID = LOGIN_ID;
    }
}
