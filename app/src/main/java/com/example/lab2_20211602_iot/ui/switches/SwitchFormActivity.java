package com.example.lab2_20211602_iot.ui.switches;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lab2_20211602_iot.R;
import com.example.lab2_20211602_iot.data.model.Device;
import com.example.lab2_20211602_iot.data.model.DeviceStatus;
import com.example.lab2_20211602_iot.data.model.DeviceType;
import com.example.lab2_20211602_iot.data.repository.DeviceRepository;
import com.example.lab2_20211602_iot.databinding.ActivitySwitchFormBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

public class SwitchFormActivity extends AppCompatActivity {
    private ActivitySwitchFormBinding binding;
    private DeviceRepository repo;
    private Device current;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySwitchFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        repo = new DeviceRepository(this);

        // Spinners
        ArrayAdapter<CharSequence> est = ArrayAdapter.createFromResource(
                this, R.array.device_status, android.R.layout.simple_spinner_item);
        est.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spStatus.setAdapter(est);

        ArrayAdapter<CharSequence> tipo = ArrayAdapter.createFromResource(
                this, R.array.switch_type, android.R.layout.simple_spinner_item);
        tipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spType.setAdapter(tipo);

        long id = getIntent().getLongExtra("id", -1);
        if (id != -1) {
            current = repo.findById(id);
            getSupportActionBar().setTitle("Actualizar Switch");
            fillForm(current);
        } else {
            getSupportActionBar().setTitle("Crear Switch");
        }
        invalidateOptionsMenu();
    }

    private void fillForm(Device d){
        binding.tilBrand.getEditText().setText(d.brand);
        binding.tilModel.getEditText().setText(d.model);
        binding.tilPorts.getEditText().setText(d.ports==null? "" : String.valueOf(d.ports));
        binding.spType.setSelection((d.manageable!=null && d.manageable)?0:1);
        int idx = d.status==DeviceStatus.OPERATIVO ?0 : d.status==DeviceStatus.REPARACION?1:2;
        binding.spStatus.setSelection(idx);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(current==null? R.menu.menu_form_create : R.menu.menu_form_edit, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) { save(); return true; }
        if (id == R.id.action_delete) {
            new MaterialAlertDialogBuilder(this)
                    .setMessage("¿Está seguro que desea Borrar?")
                    .setNegativeButton("CANCELAR", null)
                    .setPositiveButton("ACEPTAR", (d, w) -> delete())
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save(){
        String brand = binding.tilBrand.getEditText().getText().toString().trim();
        String model = binding.tilModel.getEditText().getText().toString().trim();
        String portsStr = binding.tilPorts.getEditText().getText().toString().trim();

        if (brand.isEmpty()) { binding.tilBrand.setError("Requerido"); return; }
        if (model.isEmpty()) { binding.tilModel.setError("Requerido"); return; }
        int ports;
        try { ports = Integer.parseInt(portsStr); }
        catch (Exception e){ binding.tilPorts.setError("Numérico"); return; }

        boolean manageable = binding.spType.getSelectedItemPosition()==0;
        DeviceStatus status =
                binding.spStatus.getSelectedItemPosition()==0? DeviceStatus.OPERATIVO:
                        binding.spStatus.getSelectedItemPosition()==1? DeviceStatus.REPARACION: DeviceStatus.BAJA;

        if (current == null){
            Device d = new Device(DeviceType.SWITCH, brand, model,
                    null, ports, manageable, status);
            repo.insert(d);
            Snackbar.make(binding.getRoot(), "Switch creado", Snackbar.LENGTH_SHORT).show();
        } else {
            current.brand = brand; current.model = model;
            current.ports = ports; current.manageable = manageable; current.status = status;
            repo.update(current);
            Snackbar.make(binding.getRoot(), "Cambios guardados", Snackbar.LENGTH_SHORT).show();
        }
        finish();
    }

    private void delete(){
        if (current != null){
            repo.delete(current);
            Snackbar.make(binding.getRoot(), "Switch eliminado", Snackbar.LENGTH_SHORT).show();
            finish();
        }
    }
}
