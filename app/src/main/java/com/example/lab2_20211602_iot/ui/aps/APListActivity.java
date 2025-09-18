package com.example.lab2_20211602_iot.ui.aps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.lab2_20211602_iot.data.model.Device;
import com.example.lab2_20211602_iot.data.repository.DeviceRepository;
import com.example.lab2_20211602_iot.databinding.ActivityApListBinding;
import com.example.lab2_20211602_iot.ui.aps.adapter.APAdapter;


public class APListActivity extends AppCompatActivity implements APAdapter.OnItemClick {
    private ActivityApListBinding binding;
    private DeviceRepository repo;
    private APAdapter adapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Access Points");
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        repo = new DeviceRepository(this);
        adapter = new APAdapter(this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(adapter);

        repo.getAps().observe(this, list -> {
            adapter.submit(list);
            binding.empty.setVisibility(list==null || list.isEmpty()? View.VISIBLE : View.GONE);
        });

        binding.fabAdd.setOnClickListener(v ->
                startActivity(new Intent(this, APFormActivity.class)));
    }

    @Override public void onItemClick(Device d) {
        Intent i = new Intent(this, APFormActivity.class);
        i.putExtra("id", d.id);
        startActivity(i);
    }
}
