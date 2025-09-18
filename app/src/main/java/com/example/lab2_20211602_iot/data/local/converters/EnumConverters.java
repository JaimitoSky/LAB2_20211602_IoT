package com.example.lab2_20211602_iot.data.local.converters;

import androidx.room.TypeConverter;

import com.example.lab2_20211602_iot.data.model.APFrequency;
import com.example.lab2_20211602_iot.data.model.DeviceStatus;
import com.example.lab2_20211602_iot.data.model.DeviceType;

public class EnumConverters {
    @TypeConverter public static String fromType(DeviceType t){ return t==null?null:t.name(); }
    @TypeConverter public static DeviceType toType(String s){ return s==null?null:DeviceType.valueOf(s); }

    @TypeConverter public static String fromStatus(DeviceStatus s){ return s==null?null:s.name(); }
    @TypeConverter public static DeviceStatus toStatus(String s){ return s==null?null:DeviceStatus.valueOf(s); }


    @TypeConverter public static String fromApFreq(APFrequency f){ return f==null?null:f.name(); }
    @TypeConverter public static APFrequency toApFreq(String s){ return s==null?null:APFrequency.valueOf(s); }
}
