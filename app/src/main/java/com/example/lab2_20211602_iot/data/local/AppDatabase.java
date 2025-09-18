package com.example.lab2_20211602_iot.data.local;

import android.content.Context;
import androidx.room.*;
import com.example.lab2_20211602_iot.data.local.converters.EnumConverters;
import com.example.lab2_20211602_iot.data.model.Device;

@Database(entities = {Device.class}, version = 2, exportSchema = false)
@TypeConverters({EnumConverters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract DeviceDao deviceDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase get(Context ctx){
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(ctx.getApplicationContext(),
                                    AppDatabase.class, "lab2_db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
