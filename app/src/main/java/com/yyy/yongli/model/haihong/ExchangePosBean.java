package com.yyy.yongli.model.haihong;

import com.yyy.yongli.wheel.interfaces.IPickerViewData;

import java.util.List;

public class ExchangePosBean {

    /**
     * success : true
     * message : null
     * tables : [[{"iRecNo":134,"sStockName":"成品内贸仓"},{"iRecNo":193,"sStockName":"半成品仓"},{"iRecNo":192,"sStockName":"成品外贸仓"},{"iRecNo":196,"sStockName":"底布仓"},{"iRecNo":266,"sStockName":"内贸现货仓"},{"iRecNo":373,"sStockName":"次品仓"},{"iRecNo":379,"sStockName":"成品库存仓"},{"iRecNo":380,"sStockName":"成品样布仓"},{"iRecNo":374,"sStockName":"回修仓"},{"iRecNo":268,"sStockName":"面料仓"},{"iRecNo":383,"sStockName":"成品面布仓"}]]
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

    public static class TablesBean implements IPickerViewData {
        public TablesBean(int iRecNo, String sStockName) {
            this.iRecNo = iRecNo;
            this.sStockName = sStockName;
        }

        /**
         * iRecNo : 134
         * sStockName : 成品内贸仓
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
}
