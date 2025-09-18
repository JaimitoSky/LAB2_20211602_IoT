package com.example.lab2_20211602_iot.data.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.example.lab2_20211602_iot.data.local.AppDatabase;
import com.example.lab2_20211602_iot.data.local.DeviceDao;
import com.example.lab2_20211602_iot.data.model.Device;
import com.example.lab2_20211602_iot.data.model.DeviceType;
import java.util.List;

public class DeviceRepository {
    private final DeviceDao dao;

    public DeviceRepository(Context ctx) { this.dao = AppDatabase.get(ctx).deviceDao(); }
    public LiveData<List<Device>> getSwitches(){ return dao.getByType(DeviceType.SWITCH); }

    public LiveData<List<Device>> getRouters(){ return dao.getByType(DeviceType.ROUTER); }
    public long insert(Device d){ return dao.insert(d); }
    public int update(Device d){ return dao.update(d); }
    public int delete(Device d){ return dao.delete(d); }
    public Device findById(long id){ return dao.findById(id); }
    public LiveData<List<Device>> getAps(){ return dao.getByType(DeviceType.AP); }

    public LiveData<List<Device>> getAllGroupedByStatus() { return dao.getAllOrderByStatus(); }

}
