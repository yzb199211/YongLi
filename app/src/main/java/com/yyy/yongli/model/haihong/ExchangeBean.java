package com.yyy.yongli.model.haihong;

import java.util.List;

public class ExchangeBean {

    /**
     * success : true
     * message : null
     * tables : [[{"iRecNo":817,"sBillNo":"SDB1908-0215","iOutBscDataStockMRecNo":192,"iInBscDataStockMRecNo":192,"sOutStockName11`1``":"成品外贸仓","sInStockName":"成品外贸仓","sUserName":"超级用户","sDateStr":"2019-08-16","fQty":0,"fPurQty":0,"iCount":7},{"iRecNo":818,"sBillNo":"SDB1908-0216","iOutBscDataStockMRecNo":192,"iInBscDataStockMRecNo":192,"sOutStockName":"成品外贸仓","sInStockName":"成品外贸仓","sUserName":"超级用户","sDateStr":"2019-08-16","fQty":1523.6,"fPurQty":663.8,"iCount":29},{"iRecNo":825,"sBillNo":"SDB1908-0218","iOutBscDataStockMRecNo":192,"iInBscDataStockMRecNo":192,"sOutStockName":"成品外贸仓","sInStockName":"成品外贸仓","sUserName":"超级用户","sDateStr":"2019-08-16","fQty":100.1,"fPurQty":57.4,"iCount":2}]]
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
         * iRecNo : 817
         * sBillNo : SDB1908-0215
         * iOutBscDataStockMRecNo : 192
         * iInBscDataStockMRecNo : 192
         * sOutStockName : 成品外贸仓
         * sInStockName : 成品外贸仓
         * sUserName : 超级用户
         * sDateStr : 2019-08-16
         * fQty : 0
         * fPurQty : 0
         * iCount : 7
         */

        private int iRecNo;
        private String sBillNo;
        private int iOutBscDataStockMRecNo;
        private int iInBscDataStockMRecNo;
        private String sOutStockName;
        private String sInStockName;
        private String sUserName;
        private String sDateStr;
        private double fQty;
        private double fPurQty;
        private int iCount;

        public int getIRecNo() {
            return iRecNo;
        }
        public void setIRecNo(int iRecNo) {
            this.iRecNo = iRecNo;
        }

        public String getSBillNo() {
            return sBillNo;
        }

        public void setSBillNo(String sBillNo) {
            this.sBillNo = sBillNo;
        }

        public int getIOutBscDataStockMRecNo() {
            return iOutBscDataStockMRecNo;
        }

        public void setIOutBscDataStockMRecNo(int iOutBscDataStockMRecNo) {
            this.iOutBscDataStockMRecNo = iOutBscDataStockMRecNo;
        }

        public int getIInBscDataStockMRecNo() {
            return iInBscDataStockMRecNo;
        }

        public void setIInBscDataStockMRecNo(int iInBscDataStockMRecNo) {
            this.iInBscDataStockMRecNo = iInBscDataStockMRecNo;
        }

        public String getSOutStockName() {
            return sOutStockName;
        }

        public void setSOutStockName(String sOutStockName) {
            this.sOutStockName = sOutStockName;
        }

        public String getSInStockName() {
            return sInStockName;
        }

        public void setSInStockName(String sInStockName) {
            this.sInStockName = sInStockName;
        }

        public String getSUserName() {
            return sUserName;
        }

        public void setSUserName(String sUserName) {
            this.sUserName = sUserName;
        }

        public String getSDateStr() {
            return sDateStr;
        }

        public void setSDateStr(String sDateStr) {
            this.sDateStr = sDateStr;
        }

        public double getFQty() {
            return fQty;
        }

        public void setFQty(double fQty) {
            this.fQty = fQty;
        }

        public double getFPurQty() {
            return fPurQty;
        }

        public void setFPurQty(double fPurQty) {
            this.fPurQty = fPurQty;
        }

        public int getICount() {
            return iCount;
        }

        public void setICount(int iCount) {
            this.iCount = iCount;
        }
    }
}
