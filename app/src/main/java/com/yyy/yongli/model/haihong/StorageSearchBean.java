package com.yyy.yongli.model.haihong;

import java.util.List;

public class StorageSearchBean {

    /**
     * success : true
     * message : null
     * tables : [[{"sStockName":"内贸现货仓","sName":"扁平斜纹C版","sCustColorID":"28","iQty":1,"sBerChID":"M1-02"},{"sStockName":"内贸现货仓","sName":"超柔0.5烂花大小米粒","sCustColorID":"28","iQty":9,"sBerChID":"无仓位,M1-03"},{"sStockName":"内贸现货仓","sName":"超柔0.5毛烂花地球花","sCustColorID":"28","iQty":10,"sBerChID":"无仓位,M3-09,N2-06"},{"sStockName":"内贸现货仓","sName":"仿超柔0.7毛","sCustColorID":"28","iQty":19,"sBerChID":"M3-11"},{"sStockName":"内贸现货仓","sName":"荷兰绒平板","sCustColorID":"28","iQty":34,"sBerChID":"无仓位,C3-02,D2-10,D3-07"},{"sStockName":"内贸现货仓","sName":"荷兰绒平板","sCustColorID":"28#酒红","iQty":1,"sBerChID":"D3-07"},{"sStockName":"内贸现货仓","sName":"马丁尼平板","sCustColorID":"28","iQty":19,"sBerChID":"G1-09,G1-11,H1-02"},{"sStockName":"内贸现货仓","sName":"磨砂绒","sCustColorID":"28","iQty":2,"sBerChID":"J3-03"},{"sStockName":"内贸现货仓","sName":"意大利绒","sCustColorID":"28","iQty":20,"sBerChID":"H2-09,H4-02,H4-07"},{"sStockName":"内贸现货仓","sName":"意大利绒平板复合","sCustColorID":"28","iQty":44,"sBerChID":"无仓位"},{"sStockName":"内贸现货仓","sName":"英国绒","sCustColorID":"28","iQty":12,"sBerChID":"E3-12,E4-10"}]]
     */

    private boolean success;
    private String message;
    private List<List<TablesBean>> tables;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<List<TablesBean>> getTables() {
        return tables;
    }

    public void setTables(List<List<TablesBean>> tables) {
        this.tables = tables;
    }

    public static class TablesBean {
        /**
         * sStockName : 内贸现货仓
         * sName : 扁平斜纹C版
         * sCustColorID : 28
         * iQty : 1
         * sBerChID : M1-02
         */

        private String sStockName;
        private String sName;
        private String sCustColorID;
        private int iQty;
        private String sBerChID;

        public String getSStockName() {
            return sStockName;
        }

        public void setSStockName(String sStockName) {
            this.sStockName = sStockName;
        }

        public String getSName() {
            return sName;
        }

        public void setSName(String sName) {
            this.sName = sName;
        }

        public String getSCustColorID() {
            return sCustColorID;
        }

        public void setSCustColorID(String sCustColorID) {
            this.sCustColorID = sCustColorID;
        }

        public int getIQty() {
            return iQty;
        }

        public void setIQty(int iQty) {
            this.iQty = iQty;
        }

        public String getSBerChID() {
            return sBerChID;
        }

        public void setSBerChID(String sBerChID) {
            this.sBerChID = sBerChID;
        }
    }
}
