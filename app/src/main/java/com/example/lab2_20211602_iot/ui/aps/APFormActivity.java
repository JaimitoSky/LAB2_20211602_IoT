package com.example.lab2_20211602_iot.ui.aps;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lab2_20211602_iot.R;
import com.example.lab2_20211602_iot.data.model.APFrequency;
import com.example.lab2_20211602_iot.data.model.Device;
import com.example.lab2_20211602_iot.data.model.DeviceStatus;
import com.example.lab2_20211602_iot.data.repository.DeviceRepository;
import com.example.lab2_20211602_iot.databinding.ActivityApFormBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

public class APFormActivity extends AppCompatActivity {
    private ActivityApFormBinding binding;
    private DeviceRepository repo;
    private Device current;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Spinners
        ArrayAdapter<CharSequence> est = ArrayAdapter.createFromResource(
                this, R.array.device_status, android.R.layout.simple_spinner_item);
        est.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spStatus.setAdapter(est);

        ArrayAdapter<CharSequence> freqs = ArrayAdapter.createFromResource(
                this, R.array.ap_frequency, android.R.layout.simple_spinner_item);
        freqs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spFrequency.setAdapter(freqs);

        repo = new DeviceRepository(this);

        long id = getIntent().getLongExtra("id", -1);
        if (id != -1) {
            current = repo.findById(id);
            getSupportActionBar().setTitle("Actualizar AP");
            fillForm(current);
        } else {
            getSupportActionBar().setTitle("Crear AP");
        }
        invalidateOptionsMenu();
    }

    private void fillForm(Device d){
        binding.tilBrand.getEditText().setText(d.brand);
        binding.tilModel.getEditText().setText(d.model);
        binding.tilRange.getEditText().setText(d.rangeMeters==null? "" : String.valueOf(d.rangeMeters));
        binding.spFrequency.setSelection(d.frequency==APFrequency.GHZ_2_4?0 : d.frequency==APFrequency.GHZ_5?1 : 2);
        int idx = d.status==DeviceStatus.OPERATIVO?0 : d.status==DeviceStatus.REPARACION?1 : 2;
        binding.spStatus.setSelection(idx);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(current==null? R.menu.menu_form_create : R.menu.menu_form_edit, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_save){ save(); return true; }
        if (item.getItemId()==R.id.action_delete){
            new MaterialAlertDialogBuilder(this)
                    .setMessage("¿Está seguro que desea Borrar?")
                    .setNegativeButton("CANCELAR", null)
                    .setPositiveButton("ACEPTAR", (d,w)->delete())
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save(){
        String brand = binding.tilBrand.getEditText().getText().toString().trim();
        String model = binding.tilModel.getEditText().getText().toString().trim();
        String rangeStr = binding.tilRange.getEditText().getText().toString().trim();

        if (brand.isEmpty()) { binding.tilBrand.setError("Requerido"); return; }
        if (model.isEmpty()) { binding.tilModel.setError("Requerido"); return; }
        int range;
        try { range = Integer.parseInt(rangeStr); }
        catch (Exception e){ binding.tilRange.setError("Numérico"); return; }

        APFrequency freq = binding.spFrequency.getSelectedItemPosition()==0 ? APFrequency.GHZ_2_4
                : binding.spFrequency.getSelectedItemPosition()==1 ? APFrequency.GHZ_5
                : APFrequency.DUAL_BAND;
        DeviceStatus status = binding.spStatus.getSelectedItemPosition()==0? DeviceStatus.OPERATIVO
                : binding.spStatus.getSelectedItemPosition()==1? DeviceStatus.REPARACION
                : DeviceStatus.BAJA;

        if (current == null){
            Device d = Device.ap(brand, model, freq, range, status);
            repo.insert(d);
            Snackbar.make(binding.getRoot(), "AP creado", Snackbar.LENGTH_SHORT).show();
        } else {
            current.brand = brand; current.model = model;
            current.frequency = freq; current.rangeMeters = range; current.status = status;
            repo.update(current);
            Snackbar.make(binding.getRoot(), "Cambios guardados", Snackbar.LENGTH_SHORT).show();
        }
        finish();
    }

    private void delete(){
        if (current != null) {
            repo.delete(current);
            Snackbar.make(binding.getRoot(), "AP eliminado", Snackbar.LENGTH_SHORT).show();
            finish();
        }
    }
}
