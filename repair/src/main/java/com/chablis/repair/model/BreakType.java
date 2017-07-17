package com.chablis.repair.model;

import java.util.List;

/**
 * Created by chablis on 2017/7/17.
 */

public class BreakType {


    private List<BigClassList> bigClassList;
    private List<SmallClassList> smallClassList;

    public List<BigClassList> getBigClassList() {
        return bigClassList;
    }

    public void setBigClassList(List<BigClassList> bigClassList) {
        this.bigClassList = bigClassList;
    }

    public List<SmallClassList> getSmallClassList() {
        return smallClassList;
    }

    public void setSmallClassList(List<SmallClassList> smallClassList) {
        this.smallClassList = smallClassList;
    }

    public static class BigClassList {
        /**
         * DICT_ID : 02
         * DICT_NAME : 设备故障
         */

        private String DICT_ID;
        private String DICT_NAME;

        public String getDICT_ID() {
            return DICT_ID;
        }

        public void setDICT_ID(String DICT_ID) {
            this.DICT_ID = DICT_ID;
        }

        public String getDICT_NAME() {
            return DICT_NAME;
        }

        public void setDICT_NAME(String DICT_NAME) {
            this.DICT_NAME = DICT_NAME;
        }
    }

    public static class SmallClassList {
        /**
         * DICT_ID : 02
         * DICT_NAME : 打印机缺墨
         */

        private String DICT_ID;
        private String DICT_NAME;

        public String getDICT_ID() {
            return DICT_ID;
        }

        public void setDICT_ID(String DICT_ID) {
            this.DICT_ID = DICT_ID;
        }

        public String getDICT_NAME() {
            return DICT_NAME;
        }

        public void setDICT_NAME(String DICT_NAME) {
            this.DICT_NAME = DICT_NAME;
        }
    }
}
