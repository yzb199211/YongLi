package com.yyy.yongli.model.haihong;

import java.util.List;

public class ExchangeBarcodeBean {

    /**
     * success : true
     * message : null
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
         * iRecNo : 590194
         * iBscDataStockMRecNo : 266
         * iBscDataStockDRecNo : 0
         * iBscDataColorRecNo : 54048
         * iSdOrderMRecNo : 3438
         * iBscDataCustomerRecNo : 0
         * iBscDataMatRecNo : 10267
         * iBscDataSizeRecNo : null
         * fQty : 44.9
         * fPurQty : 30.0
         * fTotal : null
         * sBatchNo : 19072518
         * sBarCode : 00458058
         * sReelNo : 32
         * sLetCode : 49.1
         * sMatLevel : null
         * iQty : 1
         * fProductWeight : 420.0
         * fProductWidth : 145.0
         * iManufacturerBscDataCustomerRecNo : 0
         * sMachine : null
         * iBscDataProcessMRecNo : null
         * sUnitID : null
         * fPurQtyM : null
         * iBscDataFlowerTypeRecNo : 0
         * sTrayCode : 017929
         * iProPlanDRecNo : null
         * iSDContractDProcessDRecNo : 0
         * sSerial : 38
         * iProPlanMRecNo : null
         * sFieldName1 :
         * sFieldName2 :
         * fFieldName3 : 0.00
         * fFieldName4 : 0.00
         * iSDOrderMRecNoBatch : 8252
         * dStockInDate : 2019-08-16T00:00:00
         * sName : 英国绒
         * sColorID : 19-0605
         * sContractNo : null
         * sOrderNo : HH19078
         * sOutOrderNo : HH19078
         * sBerChID :
         */
        private String sColorName;
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
        private Object fTotal;
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
        private Object iBscDataProcessMRecNo;
        private Object sUnitID;
        private Object fPurQtyM;
        private int iBscDataFlowerTypeRecNo;
        private String sTrayCode;
        private Object iProPlanDRecNo;
        private int iSDContractDProcessDRecNo;
        private String sSerial;
        private Object iProPlanMRecNo;
        private String sFieldName1;
        private String sFieldName2;
        private String fFieldName3;
        private String fFieldName4;
        private int iSDOrderMRecNoBatch;
        private String dStockInDate;
        private String sName;
        private String sColorID;
        private Object sContractNo;
        private String sOrderNo;
        private String sOutOrderNo;
        private String sBerChID;
        private int iQtyNew;

        public int getiQtyNew() {
            return iQtyNew;
        }

        public void setiQtyNew(int iQtyNew) {
            this.iQtyNew = iQtyNew;
        }

        public String getsColorName() {
            return sColorName;
        }

        public void setsColorName(String sColorName) {
            this.sColorName = sColorName;
        }

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

        public Object getFTotal() {
            return fTotal;
        }

        public void setFTotal(Object fTotal) {
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

        public Object getIBscDataProcessMRecNo() {
            return iBscDataProcessMRecNo;
        }

        public void setIBscDataProcessMRecNo(Object iBscDataProcessMRecNo) {
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

        public int getIBscDataFlowerTypeRecNo() {
            return iBscDataFlowerTypeRecNo;
        }

        public void setIBscDataFlowerTypeRecNo(int iBscDataFlowerTypeRecNo) {
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

        public int getISDContractDProcessDRecNo() {
            return iSDContractDProcessDRecNo;
        }

        public void setISDContractDProcessDRecNo(int iSDContractDProcessDRecNo) {
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

        public String getSFieldName1() {
            return sFieldName1;
        }

        public void setSFieldName1(String sFieldName1) {
            this.sFieldName1 = sFieldName1;
        }

        public String getSFieldName2() {
            return sFieldName2;
        }

        public void setSFieldName2(String sFieldName2) {
            this.sFieldName2 = sFieldName2;
        }

        public String getFFieldName3() {
            return fFieldName3;
        }

        public void setFFieldName3(String fFieldName3) {
            this.fFieldName3 = fFieldName3;
        }

        public String getFFieldName4() {
            return fFieldName4;
        }

        public void setFFieldName4(String fFieldName4) {
            this.fFieldName4 = fFieldName4;
        }

        public int getISDOrderMRecNoBatch() {
            return iSDOrderMRecNoBatch;
        }

        public void setISDOrderMRecNoBatch(int iSDOrderMRecNoBatch) {
            this.iSDOrderMRecNoBatch = iSDOrderMRecNoBatch;
        }

        public String getDStockInDate() {
            return dStockInDate;
        }

        public void setDStockInDate(String dStockInDate) {
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

        public Object getSContractNo() {
            return sContractNo;
        }

        public void setSContractNo(Object sContractNo) {
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
