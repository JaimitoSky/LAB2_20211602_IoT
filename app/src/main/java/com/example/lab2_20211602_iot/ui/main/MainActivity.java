package com.example.lab2_20211602_iot.ui.main;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lab2_20211602_iot.databinding.ActivityMainBinding;
import com.example.lab2_20211602_iot.ui.routers.RouterListActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("AppIoT - Lab 2");

        binding.btnRouters.setOnClickListener(v ->
                startActivity(new Intent(this, RouterListActivity.class)));

        binding.btnSwitches.setOnClickListener(v ->
                startActivity(new Intent(this, com.example.lab2_20211602_iot.ui.switches.SwitchListActivity.class)));
        binding.btnAps.setOnClickListener(v ->
                startActivity(new Intent(this, com.example.lab2_20211602_iot.ui.aps.APListActivity.class)));

        binding.btnReporte.setOnClickListener(v -> {/* aun falta programar pes */});
    }
}
