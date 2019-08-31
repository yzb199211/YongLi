package com.yyy.yongli.lookup;


import com.yyy.yongli.wheel.interfaces.IPickerViewData;

public class LookUpBean implements IPickerViewData {
    /**
     * iRecNo : 1257
     * sCustShortName : 艾露
     */

    private String sCode;
    private String sName;
    private String sClassID;

    public LookUpBean(String sCode, String sName, String sClassID) {
        this.sCode = sCode;
        this.sName = sName;
        this.sClassID = sClassID;
    }

    public LookUpBean() {
    }

    public String getsCode() {
        return sCode;
    }

    public void setsCode(String sCode) {
        this.sCode = sCode;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsClassID() {
        return sClassID;
    }

    public void setsClassID(String sClassID) {
        this.sClassID = sClassID;
    }

    @Override
    public String getPickerViewText() {
        return sName;
    }
}