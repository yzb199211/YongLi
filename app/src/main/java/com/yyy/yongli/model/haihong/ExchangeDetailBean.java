package com.yyy.yongli.model.haihong;

import java.util.List;

public class ExchangeDetailBean {

    /**
     * success : true
     * message : null
     * tables : [[{"iRecNo":79387,"iMainRecNo":828,"iSerial":1,"iBscDataMatRecNo":9585,"iBscDataColorRecNo":47966,"fProductWidth":142,"fProductWeight":230,"sReelNo":"40","fPurQty":19.6,"fQty":56.6,"sLetCode":61.9,"sRemark":null,"sBarCode":"00458870","sBatchNo":"19072987","fPrice":null,"sUserID":null,"dInputDate":"2019-08-16T15:52:43","iOutSdOrderMRecNo":4222,"iInSdOrderMRecNo":4222,"iOutBscDataCustomerRecNo":0,"iInBscDataCustomerRecNo":0,"iOutBscDataStockDRecNo":0,"iInBscDataStockDRecNo":31,"iBscDataFlowerTypeRecNo":0,"sOutPileNo":null,"iBscDataProcessesMRecNo":null,"sSerial":"1","iOutSDContractDProcessDRecNo":0,"iInSDContractDProcessDRecNo":0,"sTrayCode":"017970","iQty":null,"fTotal":null,"sFieldName1":null,"sFieldName2":null,"fFieldName3":0,"fFieldName4":0,"iOutSDOrderMRecNoBatch":9708,"iInSDOrderMRecNoBatch":9708,"sCode":"F090301020045","sName":"1毛超柔烂花狐狸纹","sColorID":"17-0902","sColorName":"1#米黄","sOutBerChID":null,"sInBerChID":"A1-3","sOutCustShortName":null,"sInCustShortName":null,"sFlowerType":null,"sProcessesCode":null,"sProcessesName":null,"sOutOrderNoProcess":null,"sOutContractNoProcess":null,"sInOrderNoProcess":null,"sInContractNoProcess":null,"sOutOrderNo":"HHZ19098-3","sInOrderNo":"HHZ19098-3","iOutBscDataStockMRecNo":192,"iInBscDataStockMRecNo":192},{"iRecNo":79388,"iMainRecNo":828,"iSerial":2,"iBscDataMatRecNo":9585,"iBscDataColorRecNo":47966,"fProductWidth":142,"fProductWeight":230,"sReelNo":"1","fPurQty":19,"fQty":54.8,"sLetCode":59.93,"sRemark":null,"sBarCode":"00458591","sBatchNo":"19072988","fPrice":null,"sUserID":null,"dInputDate":"2019-08-16T15:52:43","iOutSdOrderMRecNo":4222,"iInSdOrderMRecNo":4222,"iOutBscDataCustomerRecNo":0,"iInBscDataCustomerRecNo":0,"iOutBscDataStockDRecNo":0,"iInBscDataStockDRecNo":31,"iBscDataFlowerTypeRecNo":0,"sOutPileNo":null,"iBscDataProcessesMRecNo":null,"sSerial":"1","iOutSDContractDProcessDRecNo":0,"iInSDContractDProcessDRecNo":0,"sTrayCode":"017970","iQty":null,"fTotal":null,"sFieldName1":null,"sFieldName2":null,"fFieldName3":0,"fFieldName4":0,"iOutSDOrderMRecNoBatch":9708,"iInSDOrderMRecNoBatch":9708,"sCode":"F090301020045","sName":"1毛超柔烂花狐狸纹","sColorID":"17-0902","sColorName":"1#米黄","sOutBerChID":null,"sInBerChID":"A1-3","sOutCustShortName":null,"sInCustShortName":null,"sFlowerType":null,"sProcessesCode":null,"sProcessesName":null,"sOutOrderNoProcess":null,"sOutContractNoProcess":null,"sInOrderNoProcess":null,"sInContractNoProcess":null,"sOutOrderNo":"HHZ19098-3","sInOrderNo":"HHZ19098-3","iOutBscDataStockMRecNo":192,"iInBscDataStockMRecNo":192}]]
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
         * iRecNo : 79387
         * iMainRecNo : 828
         * iSerial : 1
         * iBscDataMatRecNo : 9585
         * iBscDataColorRecNo : 47966
         * fProductWidth : 142.0
         * fProductWeight : 230.0
         * sReelNo : 40
         * fPurQty : 19.6
         * fQty : 56.6
         * sLetCode : 61.9
         * sRemark : null
         * sBarCode : 00458870
         * sBatchNo : 19072987
         * fPrice : null
         * sUserID : null
         * dInputDate : 2019-08-16T15:52:43
         * iOutSdOrderMRecNo : 4222
         * iInSdOrderMRecNo : 4222
         * iOutBscDataCustomerRecNo : 0
         * iInBscDataCustomerRecNo : 0
         * iOutBscDataStockDRecNo : 0
         * iInBscDataStockDRecNo : 31
         * iBscDataFlowerTypeRecNo : 0
         * sOutPileNo : null
         * iBscDataProcessesMRecNo : null
         * sSerial : 1
         * iOutSDContractDProcessDRecNo : 0
         * iInSDContractDProcessDRecNo : 0
         * sTrayCode : 017970
         * iQty : null
         * fTotal : null
         * sFieldName1 : null
         * sFieldName2 : null
         * fFieldName3 : 0.0
         * fFieldName4 : 0.0
         * iOutSDOrderMRecNoBatch : 9708
         * iInSDOrderMRecNoBatch : 9708
         * sCode : F090301020045
         * sName : 1毛超柔烂花狐狸纹
         * sColorID : 17-0902
         * sColorName : 1#米黄
         * sOutBerChID : null
         * sInBerChID : A1-3
         * sOutCustShortName : null
         * sInCustShortName : null
         * sFlowerType : null
         * sProcessesCode : null
         * sProcessesName : null
         * sOutOrderNoProcess : null
         * sOutContractNoProcess : null
         * sInOrderNoProcess : null
         * sInContractNoProcess : null
         * sOutOrderNo : HHZ19098-3
         * sInOrderNo : HHZ19098-3
         * iOutBscDataStockMRecNo : 192
         * iInBscDataStockMRecNo : 192
         */

        private int iRecNo;
        private int iMainRecNo;
        private int iSerial;
        private int iBscDataMatRecNo;
        private int iBscDataColorRecNo;
        private double fProductWidth;
        private double fProductWeight;
        private String sReelNo;
        private double fPurQty;
        private double fQty;
        private double sLetCode;
        private Object sRemark;
        private String sBarCode;
        private String sBatchNo;
        private Object fPrice;
        private Object sUserID;
        private String dInputDate;
        private int iOutSdOrderMRecNo;
        private int iInSdOrderMRecNo;
        private int iOutBscDataCustomerRecNo;
        private int iInBscDataCustomerRecNo;
        private int iOutBscDataStockDRecNo;
        private int iInBscDataStockDRecNo;
        private int iBscDataFlowerTypeRecNo;
        private Object sOutPileNo;
        private Object iBscDataProcessesMRecNo;
        private String sSerial;
        private int iOutSDContractDProcessDRecNo;
        private int iInSDContractDProcessDRecNo;
        private String sTrayCode;
        private Object iQty;
        private Object fTotal;
        private Object sFieldName1;
        private Object sFieldName2;
        private double fFieldName3;
        private double fFieldName4;
        private int iOutSDOrderMRecNoBatch;
        private int iInSDOrderMRecNoBatch;
        private String sCode;
        private String sName;
        private String sColorID;
        private String sColorName;
        private String sOutBerChID;
        private String sInBerChID;
        private Object sOutCustShortName;
        private Object sInCustShortName;
        private Object sFlowerType;
        private Object sProcessesCode;
        private Object sProcessesName;
        private Object sOutOrderNoProcess;
        private Object sOutContractNoProcess;
        private Object sInOrderNoProcess;
        private Object sInContractNoProcess;
        private String sOutOrderNo;
        private String sInOrderNo;
        private int iOutBscDataStockMRecNo;
        private int iInBscDataStockMRecNo;
        private int iQtyNew;

        public int getiQtyNew() {
            return iQtyNew;
        }

        public void setiQtyNew(int iQtyNew) {
            this.iQtyNew = iQtyNew;
        }

        public int getIRecNo() {
            return iRecNo;
        }

        public void setIRecNo(int iRecNo) {
            this.iRecNo = iRecNo;
        }

        public int getIMainRecNo() {
            return iMainRecNo;
        }

        public void setIMainRecNo(int iMainRecNo) {
            this.iMainRecNo = iMainRecNo;
        }

        public int getISerial() {
            return iSerial;
        }

        public void setISerial(int iSerial) {
            this.iSerial = iSerial;
        }

        public int getIBscDataMatRecNo() {
            return iBscDataMatRecNo;
        }

        public void setIBscDataMatRecNo(int iBscDataMatRecNo) {
            this.iBscDataMatRecNo = iBscDataMatRecNo;
        }

        public int getIBscDataColorRecNo() {
            return iBscDataColorRecNo;
        }

        public void setIBscDataColorRecNo(int iBscDataColorRecNo) {
            this.iBscDataColorRecNo = iBscDataColorRecNo;
        }

        public double getFProductWidth() {
            return fProductWidth;
        }

        public void setFProductWidth(double fProductWidth) {
            this.fProductWidth = fProductWidth;
        }

        public double getFProductWeight() {
            return fProductWeight;
        }

        public void setFProductWeight(double fProductWeight) {
            this.fProductWeight = fProductWeight;
        }

        public String getSReelNo() {
            return sReelNo;
        }

        public void setSReelNo(String sReelNo) {
            this.sReelNo = sReelNo;
        }

        public double getFPurQty() {
            return fPurQty;
        }

        public void setFPurQty(double fPurQty) {
            this.fPurQty = fPurQty;
        }

        public double getFQty() {
            return fQty;
        }

        public void setFQty(double fQty) {
            this.fQty = fQty;
        }

        public double getSLetCode() {
            return sLetCode;
        }

        public void setSLetCode(double sLetCode) {
            this.sLetCode = sLetCode;
        }

        public Object getSRemark() {
            return sRemark;
        }

        public void setSRemark(Object sRemark) {
            this.sRemark = sRemark;
        }

        public String getSBarCode() {
            return sBarCode;
        }

        public void setSBarCode(String sBarCode) {
            this.sBarCode = sBarCode;
        }

        public String getSBatchNo() {
            return sBatchNo;
        }

        public void setSBatchNo(String sBatchNo) {
            this.sBatchNo = sBatchNo;
        }

        public Object getFPrice() {
            return fPrice;
        }

        public void setFPrice(Object fPrice) {
            this.fPrice = fPrice;
        }

        public Object getSUserID() {
            return sUserID;
        }

        public void setSUserID(Object sUserID) {
            this.sUserID = sUserID;
        }

        public String getDInputDate() {
            return dInputDate;
        }

        public void setDInputDate(String dInputDate) {
            this.dInputDate = dInputDate;
        }

        public int getIOutSdOrderMRecNo() {
            return iOutSdOrderMRecNo;
        }

        public void setIOutSdOrderMRecNo(int iOutSdOrderMRecNo) {
            this.iOutSdOrderMRecNo = iOutSdOrderMRecNo;
        }

        public int getIInSdOrderMRecNo() {
            return iInSdOrderMRecNo;
        }

        public void setIInSdOrderMRecNo(int iInSdOrderMRecNo) {
            this.iInSdOrderMRecNo = iInSdOrderMRecNo;
        }

        public int getIOutBscDataCustomerRecNo() {
            return iOutBscDataCustomerRecNo;
        }

        public void setIOutBscDataCustomerRecNo(int iOutBscDataCustomerRecNo) {
            this.iOutBscDataCustomerRecNo = iOutBscDataCustomerRecNo;
        }

        public int getIInBscDataCustomerRecNo() {
            return iInBscDataCustomerRecNo;
        }

        public void setIInBscDataCustomerRecNo(int iInBscDataCustomerRecNo) {
            this.iInBscDataCustomerRecNo = iInBscDataCustomerRecNo;
        }

        public int getIOutBscDataStockDRecNo() {
            return iOutBscDataStockDRecNo;
        }

        public void setIOutBscDataStockDRecNo(int iOutBscDataStockDRecNo) {
            this.iOutBscDataStockDRecNo = iOutBscDataStockDRecNo;
        }

        public int getIInBscDataStockDRecNo() {
            return iInBscDataStockDRecNo;
        }

        public void setIInBscDataStockDRecNo(int iInBscDataStockDRecNo) {
            this.iInBscDataStockDRecNo = iInBscDataStockDRecNo;
        }

        public int getIBscDataFlowerTypeRecNo() {
            return iBscDataFlowerTypeRecNo;
        }

        public void setIBscDataFlowerTypeRecNo(int iBscDataFlowerTypeRecNo) {
            this.iBscDataFlowerTypeRecNo = iBscDataFlowerTypeRecNo;
        }

        public Object getSOutPileNo() {
            return sOutPileNo;
        }

        public void setSOutPileNo(Object sOutPileNo) {
            this.sOutPileNo = sOutPileNo;
        }

        public Object getIBscDataProcessesMRecNo() {
            return iBscDataProcessesMRecNo;
        }

        public void setIBscDataProcessesMRecNo(Object iBscDataProcessesMRecNo) {
            this.iBscDataProcessesMRecNo = iBscDataProcessesMRecNo;
        }

        public String getSSerial() {
            return sSerial;
        }

        public void setSSerial(String sSerial) {
            this.sSerial = sSerial;
        }

        public int getIOutSDContractDProcessDRecNo() {
            return iOutSDContractDProcessDRecNo;
        }

        public void setIOutSDContractDProcessDRecNo(int iOutSDContractDProcessDRecNo) {
            this.iOutSDContractDProcessDRecNo = iOutSDContractDProcessDRecNo;
        }

        public int getIInSDContractDProcessDRecNo() {
            return iInSDContractDProcessDRecNo;
        }

        public void setIInSDContractDProcessDRecNo(int iInSDContractDProcessDRecNo) {
            this.iInSDContractDProcessDRecNo = iInSDContractDProcessDRecNo;
        }

        public String getSTrayCode() {
            return sTrayCode;
        }

        public void setSTrayCode(String sTrayCode) {
            this.sTrayCode = sTrayCode;
        }

        public Object getIQty() {
            return iQty;
        }

        public void setIQty(Object iQty) {
            this.iQty = iQty;
        }

        public Object getFTotal() {
            return fTotal;
        }

        public void setFTotal(Object fTotal) {
            this.fTotal = fTotal;
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

        public double getFFieldName3() {
            return fFieldName3;
        }

        public void setFFieldName3(double fFieldName3) {
            this.fFieldName3 = fFieldName3;
        }

        public double getFFieldName4() {
            return fFieldName4;
        }

        public void setFFieldName4(double fFieldName4) {
            this.fFieldName4 = fFieldName4;
        }

        public int getIOutSDOrderMRecNoBatch() {
            return iOutSDOrderMRecNoBatch;
        }

        public void setIOutSDOrderMRecNoBatch(int iOutSDOrderMRecNoBatch) {
            this.iOutSDOrderMRecNoBatch = iOutSDOrderMRecNoBatch;
        }

        public int getIInSDOrderMRecNoBatch() {
            return iInSDOrderMRecNoBatch;
        }

        public void setIInSDOrderMRecNoBatch(int iInSDOrderMRecNoBatch) {
            this.iInSDOrderMRecNoBatch = iInSDOrderMRecNoBatch;
        }

        public String getSCode() {
            return sCode;
        }

        public void setSCode(String sCode) {
            this.sCode = sCode;
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

        public String getSColorName() {
            return sColorName;
        }

        public void setSColorName(String sColorName) {
            this.sColorName = sColorName;
        }

        public String getSOutBerChID() {
            return sOutBerChID;
        }

        public void setSOutBerChID(String sOutBerChID) {
            this.sOutBerChID = sOutBerChID;
        }

        public String getSInBerChID() {
            return sInBerChID;
        }

        public void setSInBerChID(String sInBerChID) {
            this.sInBerChID = sInBerChID;
        }

        public Object getSOutCustShortName() {
            return sOutCustShortName;
        }

        public void setSOutCustShortName(Object sOutCustShortName) {
            this.sOutCustShortName = sOutCustShortName;
        }

        public Object getSInCustShortName() {
            return sInCustShortName;
        }

        public void setSInCustShortName(Object sInCustShortName) {
            this.sInCustShortName = sInCustShortName;
        }

        public Object getSFlowerType() {
            return sFlowerType;
        }

        public void setSFlowerType(Object sFlowerType) {
            this.sFlowerType = sFlowerType;
        }

        public Object getSProcessesCode() {
            return sProcessesCode;
        }

        public void setSProcessesCode(Object sProcessesCode) {
            this.sProcessesCode = sProcessesCode;
        }

        public Object getSProcessesName() {
            return sProcessesName;
        }

        public void setSProcessesName(Object sProcessesName) {
            this.sProcessesName = sProcessesName;
        }

        public Object getSOutOrderNoProcess() {
            return sOutOrderNoProcess;
        }

        public void setSOutOrderNoProcess(Object sOutOrderNoProcess) {
            this.sOutOrderNoProcess = sOutOrderNoProcess;
        }

        public Object getSOutContractNoProcess() {
            return sOutContractNoProcess;
        }

        public void setSOutContractNoProcess(Object sOutContractNoProcess) {
            this.sOutContractNoProcess = sOutContractNoProcess;
        }

        public Object getSInOrderNoProcess() {
            return sInOrderNoProcess;
        }

        public void setSInOrderNoProcess(Object sInOrderNoProcess) {
            this.sInOrderNoProcess = sInOrderNoProcess;
        }

        public Object getSInContractNoProcess() {
            return sInContractNoProcess;
        }

        public void setSInContractNoProcess(Object sInContractNoProcess) {
            this.sInContractNoProcess = sInContractNoProcess;
        }

        public String getSOutOrderNo() {
            return sOutOrderNo;
        }

        public void setSOutOrderNo(String sOutOrderNo) {
            this.sOutOrderNo = sOutOrderNo;
        }

        public String getSInOrderNo() {
            return sInOrderNo;
        }

        public void setSInOrderNo(String sInOrderNo) {
            this.sInOrderNo = sInOrderNo;
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
    }
}
