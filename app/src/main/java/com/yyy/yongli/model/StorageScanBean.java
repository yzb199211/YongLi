package com.yyy.yongli.model;

public class StorageScanBean {
    private int iBscDataStockDRecNo;
    private int type = 1;
    private int count = 1;
    private String sBerChID = "";
    private String sColorName;
    private String sSizeName;
    private String sStyleNo;
    private String sBarCode;
    private String sElements;
    private String sBatchNo;//批次
    private float fTC;
    private float fTS;

    public String getsElements() {
        return sElements;
    }

    public void setsElements(String sElements) {
        this.sElements = sElements;
    }

    public String getsBatchNo() {
        return sBatchNo;
    }

    public void setsBatchNo(String sBatchNo) {
        this.sBatchNo = sBatchNo;
    }

    public float getfTC() {
        return fTC;
    }

    public void setfTC(float fTc) {
        this.fTC = fTC;
    }

    public float getfTS() {
        return fTS;
    }

    public void setfTS(float fTS) {
        this.fTS = fTS;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getiBscDataStockDRecNo() {
        return iBscDataStockDRecNo;
    }

    public void setiBscDataStockDRecNo(int iBscDataStockDRecNo) {
        this.iBscDataStockDRecNo = iBscDataStockDRecNo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getsBerChID() {
        return sBerChID;
    }

    public void setsBerChID(String sBerChID) {
        this.sBerChID = sBerChID;
    }

    public String getsColorName() {
        return sColorName;
    }

    public void setsColorName(String sColorName) {
        this.sColorName = sColorName;
    }

    public String getsSizeName() {
        return sSizeName;
    }

    public void setsSizeName(String sSizeName) {
        this.sSizeName = sSizeName;
    }

    public String getsStyleNo() {
        return sStyleNo;
    }

    public void setsStyleNo(String sStyleNo) {
        this.sStyleNo = sStyleNo;
    }

    public String getsBarCode() {
        return sBarCode;
    }

    public void setsBarCode(String sBarCode) {
        this.sBarCode = sBarCode;
    }
}
