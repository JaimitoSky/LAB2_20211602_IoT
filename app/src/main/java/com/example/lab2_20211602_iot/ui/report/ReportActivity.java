package com.example.lab2_20211602_iot.ui.report;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.lab2_20211602_iot.data.model.Device;
import com.example.lab2_20211602_iot.data.model.DeviceStatus;
import com.example.lab2_20211602_iot.data.repository.DeviceRepository;
import com.example.lab2_20211602_iot.databinding.ActivityReportBinding;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ReportActivity extends AppCompatActivity {
    private ActivityReportBinding binding;
    private ReportAdapter adapter;
    private DeviceRepository repo;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Reportes");
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        adapter = new ReportAdapter();
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(adapter);

        repo = new DeviceRepository(this);
        repo.getAllGroupedByStatus().observe(this, list -> adapter.submit(buildRows(list)));
    }

    private List<ReportRow> buildRows(List<Device> devices){
        List<ReportRow> rows = new ArrayList<>();
        // Conteos por estado
        Map<DeviceStatus, Integer> counts = new EnumMap<>(DeviceStatus.class);
        for (DeviceStatus s : DeviceStatus.values()) counts.put(s, 0);
        for (Device d : devices) counts.put(d.status, counts.get(d.status) + 1);

        DeviceStatus[] order = { DeviceStatus.OPERATIVO, DeviceStatus.REPARACION, DeviceStatus.BAJA };
        for (DeviceStatus s : order){
            rows.add(ReportRow.header(s, counts.get(s)));
            for (Device d : devices) if (d.status == s) rows.add(ReportRow.item(d));
        }
        return rows;
    }
}
