package com.yyy.yongli.model;

import com.yyy.yongli.lookup.LookUpBean;

import java.util.List;

public class StorageBean {

    private List<StorageStockMBean> BscDataStockM;
    private List<LookUpBean> BscDataCustomer;
    private List<LookUpBean> SDSendM;

    public List<LookUpBean> getSDSendM() {
        return SDSendM;
    }

    public void setSDSendM(List<LookUpBean> SDSendM) {
        this.SDSendM = SDSendM;
    }

    public List<StorageStockMBean> getBscDataStockM() {
        return BscDataStockM;
    }

    public void setBscDataStockM(List<StorageStockMBean> BscDataStockM) {
        this.BscDataStockM = BscDataStockM;
    }

    public List<LookUpBean> getBscDataCustomer() {
        return BscDataCustomer;
    }

    public void setBscDataCustomer(List<LookUpBean> BscDataCustomer) {
        this.BscDataCustomer = BscDataCustomer;
    }

}

