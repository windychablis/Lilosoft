package com.chablis.repair.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chablis on 2017/7/10.
 */

public class RepairDetail {

    /**
     * resultPiclist : [{"FILE_URL":"http://27.17.62.40:8899/files/EGS\\normalpic\\201707041100_5aad3e8badea4a36bfb89b513ce5409d.jpg","TYPE":"2"},{"FILE_URL":"http://27.17.62.40:8899/files/EGS\\normalpic\\201707041057_26ab424eb94d4522b2b7a79e1e5499bf.jpg","TYPE":"2"}]
     * dePiclist : [{"FILE_URL":"http://27.17.62.40:8899/files/EGS\\normalpic\\201706301010_6cfde0c216a14612b64f58601c65c8f5.jpg","TYPE":"1"}]
     * clientInfo : {"FLOORNUM":2,"TITLE":"好饿啦","SMALLCLASS":"打印机缺墨","BIGCLASS":"设备故障","SERVICERESULT":"好了","TIMEVIEW":"2016-01-04","CLIENT_TYPE":"TD-H-A01-001","AREA_CODE":"110105000000","STATUS":"1","MAINTAIN_ID":"ac66136a351a43829a3e4bfca5ec4d39","AREACODE":"北京市朝阳区行政服务中心","TERMTYPE":"电子填单台","REMARK":"2楼1电子填单台","TERM_TYPE":"03","CLIENT_IP":"10.180.227.221","PROBLEMDTION":"快"}
     */

    private ClientInfo clientInfo;
    private ArrayList<RepairImage> resultPiclist;
    private ArrayList<RepairImage> dePiclist;

    @Override
    public String toString() {
        return "RepairDetail{" +
                "clientInfo=" + clientInfo +
                ", resultPiclist=" + resultPiclist +
                ", dePiclist=" + dePiclist +
                '}';
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public ArrayList<RepairImage> getResultPiclist() {
        return resultPiclist;
    }

    public void setResultPiclist(ArrayList<RepairImage> resultPiclist) {
        this.resultPiclist = resultPiclist;
    }

    public ArrayList<RepairImage> getDePiclist() {
        return dePiclist;
    }

    public void setDePiclist(ArrayList<RepairImage> dePiclist) {
        this.dePiclist = dePiclist;
    }

    public static class ClientInfo {
        /**
         * FLOORNUM : 2
         * TITLE : 好饿啦
         * SMALLCLASS : 打印机缺墨
         * BIGCLASS : 设备故障
         * SERVICERESULT : 好了
         * TIMEVIEW : 2016-01-04
         * CLIENT_TYPE : TD-H-A01-001
         * AREA_CODE : 110105000000
         * STATUS : 1
         * MAINTAIN_ID : ac66136a351a43829a3e4bfca5ec4d39
         * AREACODE : 北京市朝阳区行政服务中心
         * TERMTYPE : 电子填单台
         * REMARK : 2楼1电子填单台
         * TERM_TYPE : 03
         * CLIENT_IP : 10.180.227.221
         * PROBLEMDTION : 快
         */

        private int FLOORNUM;
        private String TITLE;
        private String SMALLCLASS;
        private String BIGCLASS;
        private String SERVICERESULT;
        private String TIMEVIEW;
        private String CLIENT_TYPE;
        private String AREA_CODE;
        private String STATUS;
        private String MAINTAIN_ID;
        private String AREACODE;
        private String TERMTYPE;
        private String REMARK;
        private String TERM_TYPE;
        private String CLIENT_IP;
        private String PROBLEMDTION;

        @Override
        public String toString() {
            return "ClientInfo{" +
                    "FLOORNUM=" + FLOORNUM +
                    ", TITLE='" + TITLE + '\'' +
                    ", SMALLCLASS='" + SMALLCLASS + '\'' +
                    ", BIGCLASS='" + BIGCLASS + '\'' +
                    ", SERVICERESULT='" + SERVICERESULT + '\'' +
                    ", TIMEVIEW='" + TIMEVIEW + '\'' +
                    ", CLIENT_TYPE='" + CLIENT_TYPE + '\'' +
                    ", AREA_CODE='" + AREA_CODE + '\'' +
                    ", STATUS='" + STATUS + '\'' +
                    ", MAINTAIN_ID='" + MAINTAIN_ID + '\'' +
                    ", AREACODE='" + AREACODE + '\'' +
                    ", TERMTYPE='" + TERMTYPE + '\'' +
                    ", REMARK='" + REMARK + '\'' +
                    ", TERM_TYPE='" + TERM_TYPE + '\'' +
                    ", CLIENT_IP='" + CLIENT_IP + '\'' +
                    ", PROBLEMDTION='" + PROBLEMDTION + '\'' +
                    '}';
        }

        public int getFLOORNUM() {
            return FLOORNUM;
        }

        public void setFLOORNUM(int FLOORNUM) {
            this.FLOORNUM = FLOORNUM;
        }

        public String getTITLE() {
            return TITLE;
        }

        public void setTITLE(String TITLE) {
            this.TITLE = TITLE;
        }

        public String getSMALLCLASS() {
            return SMALLCLASS;
        }

        public void setSMALLCLASS(String SMALLCLASS) {
            this.SMALLCLASS = SMALLCLASS;
        }

        public String getBIGCLASS() {
            return BIGCLASS;
        }

        public void setBIGCLASS(String BIGCLASS) {
            this.BIGCLASS = BIGCLASS;
        }

        public String getSERVICERESULT() {
            return SERVICERESULT;
        }

        public void setSERVICERESULT(String SERVICERESULT) {
            this.SERVICERESULT = SERVICERESULT;
        }

        public String getTIMEVIEW() {
            return TIMEVIEW;
        }

        public void setTIMEVIEW(String TIMEVIEW) {
            this.TIMEVIEW = TIMEVIEW;
        }

        public String getCLIENT_TYPE() {
            return CLIENT_TYPE;
        }

        public void setCLIENT_TYPE(String CLIENT_TYPE) {
            this.CLIENT_TYPE = CLIENT_TYPE;
        }

        public String getAREA_CODE() {
            return AREA_CODE;
        }

        public void setAREA_CODE(String AREA_CODE) {
            this.AREA_CODE = AREA_CODE;
        }

        public String getSTATUS() {
            return STATUS;
        }

        public void setSTATUS(String STATUS) {
            this.STATUS = STATUS;
        }

        public String getMAINTAIN_ID() {
            return MAINTAIN_ID;
        }

        public void setMAINTAIN_ID(String MAINTAIN_ID) {
            this.MAINTAIN_ID = MAINTAIN_ID;
        }

        public String getAREACODE() {
            return AREACODE;
        }

        public void setAREACODE(String AREACODE) {
            this.AREACODE = AREACODE;
        }

        public String getTERMTYPE() {
            return TERMTYPE;
        }

        public void setTERMTYPE(String TERMTYPE) {
            this.TERMTYPE = TERMTYPE;
        }

        public String getREMARK() {
            return REMARK;
        }

        public void setREMARK(String REMARK) {
            this.REMARK = REMARK;
        }

        public String getTERM_TYPE() {
            return TERM_TYPE;
        }

        public void setTERM_TYPE(String TERM_TYPE) {
            this.TERM_TYPE = TERM_TYPE;
        }

        public String getCLIENT_IP() {
            return CLIENT_IP;
        }

        public void setCLIENT_IP(String CLIENT_IP) {
            this.CLIENT_IP = CLIENT_IP;
        }

        public String getPROBLEMDTION() {
            return PROBLEMDTION;
        }

        public void setPROBLEMDTION(String PROBLEMDTION) {
            this.PROBLEMDTION = PROBLEMDTION;
        }
    }

    public static class RepairImage {
        /**
         * FILE_URL : http://27.17.62.40:8899/files/EGS\normalpic\201707041100_5aad3e8badea4a36bfb89b513ce5409d.jpg
         * TYPE : 2
         */

        private String FILE_URL;
        private String TYPE;

        @Override
        public String toString() {
            return "RepairImage{" +
                    "FILE_URL='" + FILE_URL + '\'' +
                    ", TYPE='" + TYPE + '\'' +
                    '}';
        }

        public String getFILE_URL() {
            return FILE_URL;
        }

        public void setFILE_URL(String FILE_URL) {
            this.FILE_URL = FILE_URL;
        }

        public String getTYPE() {
            return TYPE;
        }

        public void setTYPE(String TYPE) {
            this.TYPE = TYPE;
        }
    }
}
