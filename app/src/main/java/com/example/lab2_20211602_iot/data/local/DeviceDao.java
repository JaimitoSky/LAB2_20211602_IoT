package com.example.lab2_20211602_iot.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.lab2_20211602_iot.data.model.Device;
import com.example.lab2_20211602_iot.data.model.DeviceType;
import java.util.List;

@Dao
public interface DeviceDao {
    @Query("SELECT * FROM devices WHERE type=:type ORDER BY id DESC")
    LiveData<List<Device>> getByType(DeviceType type);

    @Query("SELECT * FROM devices WHERE id=:id LIMIT 1")
    Device findById(long id);

    @Insert long insert(Device d);
    @Update int update(Device d);
    @Delete int delete(Device d);

    @Query(
            "SELECT * FROM devices " +
                    "ORDER BY " +
                    "CASE status " +
                    " WHEN 'OPERATIVO' THEN 0 " +
                    " WHEN 'REPARACION' THEN 1 " +
                    " ELSE 2 END, " +
                    "brand COLLATE NOCASE, model COLLATE NOCASE")
    androidx.lifecycle.LiveData<java.util.List<com.example.lab2_20211602_iot.data.model.Device>>
    getAllOrderByStatus();



}
