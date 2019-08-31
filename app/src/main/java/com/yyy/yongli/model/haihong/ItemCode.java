package com.yyy.yongli.model.haihong;

import java.util.List;

public class ItemCode {

    /**
     * success : true
     * message : null
     * tables : [[{"iRecNo":46845,"iBscDataStockMRecNo":192,"iBscDataStockDRecNo":0,"iBscDataColorRecNo":46683,"iSdOrderMRecNo":1197,"iBscDataCustomerRecNo":0,"iBscDataMatRecNo":8590,"iBscDataSizeRecNo":null,"fQty":49.4,"fPurQty":28.6,"fTotal":0,"sBatchNo":"516","sBarCode":"00003476","sReelNo":"19","sLetCode":54.02,"sMatLevel":null,"iQty":1,"fProductWeight":370,"fProductWidth":142,"iManufacturerBscDataCustomerRecNo":0,"sMachine":null,"iBscDataProcessMRecNo":0,"sUnitID":null,"fPurQtyM":null,"iBscDataFlowerTypeRecNo":null,"sTrayCode":"000144","iProPlanDRecNo":null,"iSDContractDProcessDRecNo":null,"sSerial":"32","iProPlanMRecNo":null,"sFieldName1":null,"sFieldName2":null,"fFieldName3":null,"fFieldName4":null,"iSDOrderMRecNoBatch":4850,"dStockInDate":null,"sName":"荷兰绒","sColorID":"红咖","sContractNo":"5217","sOrderNo":"HHP18199","sOutOrderNo":"HHP18199","sBerChID":""},{"iRecNo":46846,"iBscDataStockMRecNo":192,"iBscDataStockDRecNo":0,"iBscDataColorRecNo":46683,"iSdOrderMRecNo":1197,"iBscDataCustomerRecNo":0,"iBscDataMatRecNo":8590,"iBscDataSizeRecNo":null,"fQty":50.7,"fPurQty":28.8,"fTotal":0,"sBatchNo":"516","sBarCode":"00003489","sReelNo":"20","sLetCode":55.45,"sMatLevel":null,"iQty":1,"fProductWeight":370,"fProductWidth":142,"iManufacturerBscDataCustomerRecNo":0,"sMachine":null,"iBscDataProcessMRecNo":0,"sUnitID":null,"fPurQtyM":null,"iBscDataFlowerTypeRecNo":null,"sTrayCode":"000144","iProPlanDRecNo":null,"iSDContractDProcessDRecNo":null,"sSerial":"32","iProPlanMRecNo":null,"sFieldName1":null,"sFieldName2":null,"fFieldName3":null,"fFieldName4":null,"iSDOrderMRecNoBatch":4850,"dStockInDate":null,"sName":"荷兰绒","sColorID":"红咖","sContractNo":"5217","sOrderNo":"HHP18199","sOutOrderNo":"HHP18199","sBerChID":""}]]
     */

    private boolean success;
    private Object message;
    private List<List<TablesBean>> tables;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
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
         * iRecNo : 46845
         * iBscDataStockMRecNo : 192
         * iBscDataStockDRecNo : 0
         * iBscDataColorRecNo : 46683
         * iSdOrderMRecNo : 1197
         * iBscDataCustomerRecNo : 0
         * iBscDataMatRecNo : 8590
         * iBscDataSizeRecNo : null
         * fQty : 49.4//米数
         * fPurQty : 28.6//重量
         * fTotal : 0.0
         * sBatchNo : 516
         * sBarCode : 00003476//条码
         * sReelNo : 19
         * sLetCode : 54.02
         * sMatLevel : null
         * iQty : 1
         * fProductWeight : 370.0
         * fProductWidth : 142.0
         * iManufacturerBscDataCustomerRecNo : 0
         * sMachine : null
         * iBscDataProcessMRecNo : 0
         * sUnitID : null
         * fPurQtyM : null
         * iBscDataFlowerTypeRecNo : null
         * sTrayCode : 000144//托盘号
         * iProPlanDRecNo : null
         * iSDContractDProcessDRecNo : null
         * sSerial : 32
         * iProPlanMRecNo : null
         * sFieldName1 : null
         * sFieldName2 : null
         * fFieldName3 : null
         * fFieldName4 : null
         * iSDOrderMRecNoBatch : 4850
         * dStockInDate : null
         * sName : 荷兰绒//品名
         * sColorID : 红咖//色号
         * sContractNo : 5217
         * sOrderNo : HHP18199//批次订单号
         * sOutOrderNo : HHP18199
         * sBerChID : //仓位
         */

        private int iRecNo;
        private int iBscDataStockMRecNo;
        private int iBscDataStockDRecNo;
        private int iBscDataColorRecNo;
        private int iSdOrderMRecNo;
        private int iBscDataCustomerRecNo;
        private int iBscDataMatRecNo;
        private Object iBscDataSizeRecNo;
        private double fQty;
        private double fPurQty;
        private double fTotal;
        private String sBatchNo;
        private String sBarCode;
        private String sReelNo;
        private double sLetCode;
        private Object sMatLevel;
        private int iQty;
        private double fProductWeight;
        private double fProductWidth;
        private int iManufacturerBscDataCustomerRecNo;
        private Object sMachine;
        private int iBscDataProcessMRecNo;
        private Object sUnitID;
        private Object fPurQtyM;
        private Object iBscDataFlowerTypeRecNo;
        private String sTrayCode;
        private Object iProPlanDRecNo;
        private Object iSDContractDProcessDRecNo;
        private String sSerial;
        private Object iProPlanMRecNo;
        private Object sFieldName1;
        private Object sFieldName2;
        private Object fFieldName3;
        private Object fFieldName4;
        private int iSDOrderMRecNoBatch;
        private Object dStockInDate;
        private String sName;
        private String sColorID;
        private String sContractNo;
        private String sOrderNo;
        private String sOutOrderNo;
        private String sBerChID;

        public int getIRecNo() {
            return iRecNo;
        }

        public void setIRecNo(int iRecNo) {
            this.iRecNo = iRecNo;
        }

        public int getIBscDataStockMRecNo() {
            return iBscDataStockMRecNo;
        }

        public void setIBscDataStockMRecNo(int iBscDataStockMRecNo) {
            this.iBscDataStockMRecNo = iBscDataStockMRecNo;
        }

        public int getIBscDataStockDRecNo() {
            return iBscDataStockDRecNo;
        }

        public void setIBscDataStockDRecNo(int iBscDataStockDRecNo) {
            this.iBscDataStockDRecNo = iBscDataStockDRecNo;
        }

        public int getIBscDataColorRecNo() {
            return iBscDataColorRecNo;
        }

        public void setIBscDataColorRecNo(int iBscDataColorRecNo) {
            this.iBscDataColorRecNo = iBscDataColorRecNo;
        }

        public int getISdOrderMRecNo() {
            return iSdOrderMRecNo;
        }

        public void setISdOrderMRecNo(int iSdOrderMRecNo) {
            this.iSdOrderMRecNo = iSdOrderMRecNo;
        }

        public int getIBscDataCustomerRecNo() {
            return iBscDataCustomerRecNo;
        }

        public void setIBscDataCustomerRecNo(int iBscDataCustomerRecNo) {
            this.iBscDataCustomerRecNo = iBscDataCustomerRecNo;
        }

        public int getIBscDataMatRecNo() {
            return iBscDataMatRecNo;
        }

        public void setIBscDataMatRecNo(int iBscDataMatRecNo) {
            this.iBscDataMatRecNo = iBscDataMatRecNo;
        }

        public Object getIBscDataSizeRecNo() {
            return iBscDataSizeRecNo;
        }

        public void setIBscDataSizeRecNo(Object iBscDataSizeRecNo) {
            this.iBscDataSizeRecNo = iBscDataSizeRecNo;
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

        public double getFTotal() {
            return fTotal;
        }

        public void setFTotal(double fTotal) {
            this.fTotal = fTotal;
        }

        public String getSBatchNo() {
            return sBatchNo;
        }

        public void setSBatchNo(String sBatchNo) {
            this.sBatchNo = sBatchNo;
        }

        public String getSBarCode() {
            return sBarCode;
        }

        public void setSBarCode(String sBarCode) {
            this.sBarCode = sBarCode;
        }

        public String getSReelNo() {
            return sReelNo;
        }

        public void setSReelNo(String sReelNo) {
            this.sReelNo = sReelNo;
        }

        public double getSLetCode() {
            return sLetCode;
        }

        public void setSLetCode(double sLetCode) {
            this.sLetCode = sLetCode;
        }

        public Object getSMatLevel() {
            return sMatLevel;
        }

        public void setSMatLevel(Object sMatLevel) {
            this.sMatLevel = sMatLevel;
        }

        public int getIQty() {
            return iQty;
        }

        public void setIQty(int iQty) {
            this.iQty = iQty;
        }

        public double getFProductWeight() {
            return fProductWeight;
        }

        public void setFProductWeight(double fProductWeight) {
            this.fProductWeight = fProductWeight;
        }

        public double getFProductWidth() {
            return fProductWidth;
        }

        public void setFProductWidth(double fProductWidth) {
            this.fProductWidth = fProductWidth;
        }

        public int getIManufacturerBscDataCustomerRecNo() {
            return iManufacturerBscDataCustomerRecNo;
        }

        public void setIManufacturerBscDataCustomerRecNo(int iManufacturerBscDataCustomerRecNo) {
            this.iManufacturerBscDataCustomerRecNo = iManufacturerBscDataCustomerRecNo;
        }

        public Object getSMachine() {
            return sMachine;
        }

        public void setSMachine(Object sMachine) {
            this.sMachine = sMachine;
        }

        public int getIBscDataProcessMRecNo() {
            return iBscDataProcessMRecNo;
        }

        public void setIBscDataProcessMRecNo(int iBscDataProcessMRecNo) {
            this.iBscDataProcessMRecNo = iBscDataProcessMRecNo;
        }

        public Object getSUnitID() {
            return sUnitID;
        }

        public void setSUnitID(Object sUnitID) {
            this.sUnitID = sUnitID;
        }

        public Object getFPurQtyM() {
            return fPurQtyM;
        }

        public void setFPurQtyM(Object fPurQtyM) {
            this.fPurQtyM = fPurQtyM;
        }

        public Object getIBscDataFlowerTypeRecNo() {
            return iBscDataFlowerTypeRecNo;
        }

        public void setIBscDataFlowerTypeRecNo(Object iBscDataFlowerTypeRecNo) {
            this.iBscDataFlowerTypeRecNo = iBscDataFlowerTypeRecNo;
        }

        public String getSTrayCode() {
            return sTrayCode;
        }

        public void setSTrayCode(String sTrayCode) {
            this.sTrayCode = sTrayCode;
        }

        public Object getIProPlanDRecNo() {
            return iProPlanDRecNo;
        }

        public void setIProPlanDRecNo(Object iProPlanDRecNo) {
            this.iProPlanDRecNo = iProPlanDRecNo;
        }

        public Object getISDContractDProcessDRecNo() {
            return iSDContractDProcessDRecNo;
        }

        public void setISDContractDProcessDRecNo(Object iSDContractDProcessDRecNo) {
            this.iSDContractDProcessDRecNo = iSDContractDProcessDRecNo;
        }

        public String getSSerial() {
            return sSerial;
        }

        public void setSSerial(String sSerial) {
            this.sSerial = sSerial;
        }

        public Object getIProPlanMRecNo() {
            return iProPlanMRecNo;
        }

        public void setIProPlanMRecNo(Object iProPlanMRecNo) {
            this.iProPlanMRecNo = iProPlanMRecNo;
        }

        public Object getSFieldName1() {
            return sFieldName1;
        }

        public void setSFieldName1(Object sFieldName1) {
            this.sFieldName1 = sFieldName1;
        }

        public Object getSFieldName2() {
            return sFieldName2;
        }

        public void setSFieldName2(Object sFieldName2) {
            this.sFieldName2 = sFieldName2;
        }

        public Object getFFieldName3() {
            return fFieldName3;
        }

        public void setFFieldName3(Object fFieldName3) {
            this.fFieldName3 = fFieldName3;
        }

        public Object getFFieldName4() {
            return fFieldName4;
        }

        public void setFFieldName4(Object fFieldName4) {
            this.fFieldName4 = fFieldName4;
        }

        public int getISDOrderMRecNoBatch() {
            return iSDOrderMRecNoBatch;
        }

        public void setISDOrderMRecNoBatch(int iSDOrderMRecNoBatch) {
            this.iSDOrderMRecNoBatch = iSDOrderMRecNoBatch;
        }

        public Object getDStockInDate() {
            return dStockInDate;
        }

        public void setDStockInDate(Object dStockInDate) {
            this.dStockInDate = dStockInDate;
        }

        public String getSName() {
            return sName;
        }

        public void setSName(String sName) {
            this.sName = sName;
        }

        public String getSColorID() {
            return sColorID;
        }

        public void setSColorID(String sColorID) {
            this.sColorID = sColorID;
        }

        public String getSContractNo() {
            return sContractNo;
        }

        public void setSContractNo(String sContractNo) {
            this.sContractNo = sContractNo;
        }

        public String getSOrderNo() {
            return sOrderNo;
        }

        public void setSOrderNo(String sOrderNo) {
            this.sOrderNo = sOrderNo;
        }

        public String getSOutOrderNo() {
            return sOutOrderNo;
        }

        public void setSOutOrderNo(String sOutOrderNo) {
            this.sOutOrderNo = sOutOrderNo;
        }

        public String getSBerChID() {
            return sBerChID;
        }

        public void setSBerChID(String sBerChID) {
            this.sBerChID = sBerChID;
        }
    }
}
