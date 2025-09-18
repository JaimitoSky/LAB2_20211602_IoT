package com.example.lab2_20211602_iot.data.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "devices")
public class Device {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public DeviceType type;
    public String brand;
    public String model;

    public Float speedGbps;

    public Integer ports;
    public Boolean manageable;
    public APFrequency frequency;
    public Integer rangeMeters;
    public DeviceStatus status;

    public Device() { }

    @Ignore
    public Device(DeviceType type,
                  String brand,
                  String model,
                  Float speedGbps,
                  Integer ports,
                  Boolean manageable,
                  DeviceStatus status) {
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.speedGbps = speedGbps;
        this.ports = ports;
        this.manageable = manageable;
        this.status = status;
    }


    @Ignore
    public static Device router(String brand, String model, float speedGbps, DeviceStatus status) {
        Device d = new Device();
        d.type = DeviceType.ROUTER;
        d.brand = brand;
        d.model = model;
        d.speedGbps = speedGbps;
        d.status = status;
        return d;
    }


    @Ignore
    public static Device sw(String brand, String model, int ports, boolean manageable, DeviceStatus status) {
        Device d = new Device();
        d.type = DeviceType.SWITCH;
        d.brand = brand;
        d.model = model;
        d.ports = ports;
        d.manageable = manageable;
        d.status = status;
        return d;
    }
    @Ignore
    public static Device ap(String brand, String model, APFrequency freq, int rangeMeters, DeviceStatus status){
        Device d = new Device();
        d.type = DeviceType.AP;
        d.brand = brand;
        d.model = model;
        d.frequency = freq;
        d.rangeMeters = rangeMeters;
        d.status = status;
        return d;
    }
}
