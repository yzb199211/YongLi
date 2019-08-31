package com.yyy.yongli.model.haihong;

import com.yyy.yongli.wheel.interfaces.IPickerViewData;

import java.util.List;

public class ExchangeInputPosBean {

    /**
     * success : true
     * message : null
     * tables : [[{"iRecNo":29,"sBerChID":"A1-1"},{"iRecNo":30,"sBerChID":"A1-2"},{"iRecNo":31,"sBerChID":"A1-3"},{"iRecNo":32,"sBerChID":"A1-4"},{"iRecNo":33,"sBerChID":"A1-5"},{"iRecNo":34,"sBerChID":"A2-1"},{"iRecNo":35,"sBerChID":"A2-2"},{"iRecNo":36,"sBerChID":"A2-3"},{"iRecNo":37,"sBerChID":"A2-4"},{"iRecNo":38,"sBerChID":"A2-5"},{"iRecNo":39,"sBerChID":"A2-6"},{"iRecNo":40,"sBerChID":"A3-1"},{"iRecNo":41,"sBerChID":"A3-2"},{"iRecNo":42,"sBerChID":"A3-3"},{"iRecNo":43,"sBerChID":"A3-4"},{"iRecNo":44,"sBerChID":"A3-5"},{"iRecNo":45,"sBerChID":"A4-1"},{"iRecNo":46,"sBerChID":"A4-2"},{"iRecNo":47,"sBerChID":"A4-3"},{"iRecNo":48,"sBerChID":"A4-4"},{"iRecNo":49,"sBerChID":"A4-5"},{"iRecNo":50,"sBerChID":"A4-6"},{"iRecNo":51,"sBerChID":"A5-1"},{"iRecNo":52,"sBerChID":"A5-2"},{"iRecNo":53,"sBerChID":"A5-3"},{"iRecNo":54,"sBerChID":"A5-4"},{"iRecNo":55,"sBerChID":"B1-1"},{"iRecNo":56,"sBerChID":"B1-2"},{"iRecNo":57,"sBerChID":"B1-3"},{"iRecNo":58,"sBerChID":"B1-4"},{"iRecNo":59,"sBerChID":"B1-5"},{"iRecNo":60,"sBerChID":"B1-6"},{"iRecNo":61,"sBerChID":"B2-1"},{"iRecNo":62,"sBerChID":"B2-2"},{"iRecNo":63,"sBerChID":"B2-3"},{"iRecNo":64,"sBerChID":"B2-4"},{"iRecNo":65,"sBerChID":"B2-5"},{"iRecNo":66,"sBerChID":"B2-6"},{"iRecNo":67,"sBerChID":"B3-1"},{"iRecNo":68,"sBerChID":"B3-2"},{"iRecNo":69,"sBerChID":"B3-3"},{"iRecNo":70,"sBerChID":"B3-4"},{"iRecNo":71,"sBerChID":"B3-5"},{"iRecNo":72,"sBerChID":"B3-6"},{"iRecNo":73,"sBerChID":"C1-1"},{"iRecNo":74,"sBerChID":"C1-2"},{"iRecNo":75,"sBerChID":"C1-3"},{"iRecNo":76,"sBerChID":"C1-4"},{"iRecNo":77,"sBerChID":"C1-5"},{"iRecNo":78,"sBerChID":"C1-6"},{"iRecNo":79,"sBerChID":"C2-1"},{"iRecNo":80,"sBerChID":"C2-2"},{"iRecNo":81,"sBerChID":"C2-3"},{"iRecNo":82,"sBerChID":"C2-4"},{"iRecNo":83,"sBerChID":"C2-5"},{"iRecNo":84,"sBerChID":"C2-6"},{"iRecNo":85,"sBerChID":"C3-1"},{"iRecNo":86,"sBerChID":"C3-2"},{"iRecNo":87,"sBerChID":"C3-3"},{"iRecNo":88,"sBerChID":"C3-4"},{"iRecNo":89,"sBerChID":"C3-5"},{"iRecNo":90,"sBerChID":"C3-6"},{"iRecNo":91,"sBerChID":"D1-1"},{"iRecNo":92,"sBerChID":"D2-1"},{"iRecNo":93,"sBerChID":"D3-1"},{"iRecNo":94,"sBerChID":"E1-1"},{"iRecNo":95,"sBerChID":"E1-2"},{"iRecNo":96,"sBerChID":"E1-3"},{"iRecNo":97,"sBerChID":"E1-4"},{"iRecNo":98,"sBerChID":"E1-5"},{"iRecNo":99,"sBerChID":"E2-1"},{"iRecNo":100,"sBerChID":"E2-2"},{"iRecNo":101,"sBerChID":"E2-3"},{"iRecNo":102,"sBerChID":"E2-4"},{"iRecNo":103,"sBerChID":"E2-5"},{"iRecNo":104,"sBerChID":"E2-6"},{"iRecNo":105,"sBerChID":"E2-7"},{"iRecNo":106,"sBerChID":"E2-8"},{"iRecNo":107,"sBerChID":"E2-9"},{"iRecNo":358,"sBerChID":"T1-1"}]]
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
        /**
         * iRecNo : 29
         * sBerChID : A1-1
         */

        private int iRecNo;
        private String sBerChID;

        public int getIRecNo() {
            return iRecNo;
        }

        public void setIRecNo(int iRecNo) {
            this.iRecNo = iRecNo;
        }

        public String getSBerChID() {
            return sBerChID;
        }

        public void setSBerChID(String sBerChID) {
            this.sBerChID = sBerChID;
        }

        @Override
        public String getPickerViewText() {
            return sBerChID;
        }
    }
}
