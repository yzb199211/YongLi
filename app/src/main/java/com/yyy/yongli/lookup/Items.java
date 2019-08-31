package com.yyy.yongli.lookup;

import com.yyy.yongli.view.ScanItem;
import com.yyy.yongli.view.SelectItem;

public class Items {
    SelectItem selectItem;
    ScanItem scanItem;
    int tag;

    String title;
    String data;

    public Items() {
    }

    public Items(SelectItem selectItem, int tag, String title, String data) {
        this.selectItem = selectItem;
        this.tag = tag;
        this.title = title;
        this.data = data;
    }

    public Items(ScanItem scanItem, int tag, String title, String data) {
        this.scanItem = scanItem;
        this.tag = tag;
        this.title = title;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public SelectItem getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(SelectItem selectItem) {
        this.selectItem = selectItem;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ScanItem getScanItem() {
        return scanItem;
    }

    public void setScanItem(ScanItem scanItem) {
        this.scanItem = scanItem;
    }
}