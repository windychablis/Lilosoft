package com.chablis.repair.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chablis on 2017/7/6.
 */
public class Equipment implements Serializable {

    /**
     * repairList : [{"STATUS":"1","MAINTAIN_ID":"1149A314632F4245BBE0F34ECCD96AF9","SMALLCLASS":"打印机卡纸","BIGCLASS":"网络故障","CLIENT_TYPE":"JH-H-A01-3","REPAIRDATE":"2017-06-14"}]
     * clientinfo : {"FLOOR":1,"FLOORNUM":"一楼","TYPENUM":"01","TIMEVIEW":"2017-02-10","REMARK":"1楼左边取号机","CLIENT_TYPE":"JH-H-A01-3","TERM_TYPE":"取号机","CLIENT_IP":"10.180.220.232","CODE":"110105000000","AREA_CODE":"北京市朝阳区行政服务中心"}
     */

    private ClientInfo clientinfo;
    private List<RepairInfo> repairList;

    @Override
    public String toString() {
        return "Equipment{" +
                "clientinfo=" + clientinfo +
                ", repairList=" + repairList +
                '}';
    }

    public ClientInfo getClientinfo() {
        return clientinfo;
    }

    public void setClientinfo(ClientInfo clientinfo) {
        this.clientinfo = clientinfo;
    }

    public List<RepairInfo> getRepairList() {
        return repairList;
    }

    public void setRepairList(List<RepairInfo> repairList) {
        this.repairList = repairList;
    }

    public static class ClientInfo implements Serializable {

        /**
         * FLOOR : 1
         * FLOORNUM : 一楼
         * TYPENUM : 01
         * TIMEVIEW : 2017-02-10
         * REMARK : 1楼左边取号机
         * CLIENT_TYPE : JH-H-A01-3
         * TERM_TYPE : 取号机
         * CLIENT_IP : 10.180.220.232
         * CODE : 110105000000
         * AREA_CODE : 北京市朝阳区行政服务中心
         */

        private int FLOOR;
        private String FLOORNUM;
        private String TYPENUM;
        private String TIMEVIEW;
        private String REMARK;
        private String CLIENT_TYPE;
        private String TERM_TYPE;
        private String CLIENT_IP;
        private String CODE;
        private String AREA_CODE;

        @Override
        public String toString() {
            return "ClientInfo{" +
                    "FLOOR=" + FLOOR +
                    ", FLOORNUM='" + FLOORNUM + '\'' +
                    ", TYPENUM='" + TYPENUM + '\'' +
                    ", TIMEVIEW='" + TIMEVIEW + '\'' +
                    ", REMARK='" + REMARK + '\'' +
                    ", CLIENT_TYPE='" + CLIENT_TYPE + '\'' +
                    ", TERM_TYPE='" + TERM_TYPE + '\'' +
                    ", CLIENT_IP='" + CLIENT_IP + '\'' +
                    ", CODE='" + CODE + '\'' +
                    ", AREA_CODE='" + AREA_CODE + '\'' +
                    '}';
        }

        public int getFLOOR() {
            return FLOOR;
        }

        public void setFLOOR(int FLOOR) {
            this.FLOOR = FLOOR;
        }

        public String getFLOORNUM() {
            return FLOORNUM;
        }

        public void setFLOORNUM(String FLOORNUM) {
            this.FLOORNUM = FLOORNUM;
        }

        public String getTYPENUM() {
            return TYPENUM;
        }

        public void setTYPENUM(String TYPENUM) {
            this.TYPENUM = TYPENUM;
        }

        public String getTIMEVIEW() {
            return TIMEVIEW;
        }

        public void setTIMEVIEW(String TIMEVIEW) {
            this.TIMEVIEW = TIMEVIEW;
        }

        public String getREMARK() {
            return REMARK;
        }

        public void setREMARK(String REMARK) {
            this.REMARK = REMARK;
        }

        public String getCLIENT_TYPE() {
            return CLIENT_TYPE;
        }

        public void setCLIENT_TYPE(String CLIENT_TYPE) {
            this.CLIENT_TYPE = CLIENT_TYPE;
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

        public String getCODE() {
            return CODE;
        }

        public void setCODE(String CODE) {
            this.CODE = CODE;
        }

        public String getAREA_CODE() {
            return AREA_CODE;
        }

        public void setAREA_CODE(String AREA_CODE) {
            this.AREA_CODE = AREA_CODE;
        }
    }

    public static class RepairInfo implements Serializable {


        /**
         * STATUS : 1
         * MAINTAIN_ID : 1149A314632F4245BBE0F34ECCD96AF9
         * SMALLCLASS : 打印机卡纸
         * BIGCLASS : 网络故障
         * CLIENT_TYPE : JH-H-A01-3
         * REPAIRDATE : 2017-06-14
         */

        private String STATUS;
        private String MAINTAIN_ID;
        private String SMALLCLASS;
        private String BIGCLASS;
        private String CLIENT_TYPE;
        private String REPAIRDATE;

        @Override
        public String toString() {
            return "RepairInfo{" +
                    "STATUS='" + STATUS + '\'' +
                    ", MAINTAIN_ID='" + MAINTAIN_ID + '\'' +
                    ", SMALLCLASS='" + SMALLCLASS + '\'' +
                    ", BIGCLASS='" + BIGCLASS + '\'' +
                    ", CLIENT_TYPE='" + CLIENT_TYPE + '\'' +
                    ", REPAIRDATE='" + REPAIRDATE + '\'' +
                    '}';
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

        public String getCLIENT_TYPE() {
            return CLIENT_TYPE;
        }

        public void setCLIENT_TYPE(String CLIENT_TYPE) {
            this.CLIENT_TYPE = CLIENT_TYPE;
        }

        public String getREPAIRDATE() {
            return REPAIRDATE;
        }

        public void setREPAIRDATE(String REPAIRDATE) {
            this.REPAIRDATE = REPAIRDATE;
        }
    }
}
