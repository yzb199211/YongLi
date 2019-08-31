package com.yyy.yongli.model;

import com.yyy.yongli.wheel.interfaces.IPickerViewData;

public class StorageStockMBean implements IPickerViewData {
    /**
     * iRecNo : 238
     * sStockName : 电商仓
     */

    private int iRecNo;
    private String sStockName;

    public int getIRecNo() {
        return iRecNo;
    }

    public void setIRecNo(int iRecNo) {
        this.iRecNo = iRecNo;
    }

    public String getSStockName() {
        return sStockName;
    }

    public void setSStockName(String sStockName) {
        this.sStockName = sStockName;
    }

    @Override
    public String getPickerViewText() {
        return sStockName;
    }
}