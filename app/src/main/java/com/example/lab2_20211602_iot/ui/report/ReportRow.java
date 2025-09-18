package com.example.lab2_20211602_iot.ui.report;

import com.example.lab2_20211602_iot.data.model.Device;
import com.example.lab2_20211602_iot.data.model.DeviceStatus;

public class ReportRow {
    public enum Kind { HEADER, ITEM }
    public Kind kind;

    // HEADER
    public DeviceStatus status;
    public int count;

    // ITEM
    public Device device;

    public static ReportRow header(DeviceStatus s, int count){
        ReportRow r = new ReportRow();
        r.kind = Kind.HEADER; r.status = s; r.count = count;
        return r;
    }
    public static ReportRow item(Device d){
        ReportRow r = new ReportRow();
        r.kind = Kind.ITEM; r.device = d;
        return r;
    }
}
