package com.example.lab2_20211602_iot.ui.switches;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.lab2_20211602_iot.data.model.Device;
import com.example.lab2_20211602_iot.data.repository.DeviceRepository;
import com.example.lab2_20211602_iot.databinding.ActivitySwitchListBinding;
import com.example.lab2_20211602_iot.ui.switches.adapter.SwitchAdapter;

public class SwitchListActivity extends AppCompatActivity implements SwitchAdapter.OnItemClick {
    private ActivitySwitchListBinding binding;
    private DeviceRepository repo;
    private SwitchAdapter adapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySwitchListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Switches");
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        repo = new DeviceRepository(this);

        adapter = new SwitchAdapter(this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(adapter);

        repo.getSwitches().observe(this, list -> {
            adapter.submit(list);
            binding.empty.setVisibility(list == null || list.isEmpty() ? View.VISIBLE : View.GONE);
        });

        binding.fabAdd.setOnClickListener(v ->
                startActivity(new Intent(this, SwitchFormActivity.class)));
    }

    @Override public void onItemClick(Device d) {
        Intent i = new Intent(this, SwitchFormActivity.class);
        i.putExtra("id", d.id);
        startActivity(i);
    }
}
