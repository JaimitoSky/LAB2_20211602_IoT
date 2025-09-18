package com.example.lab2_20211602_iot.ui.switches.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lab2_20211602_iot.data.model.Device;
import com.example.lab2_20211602_iot.databinding.ItemSwitchBinding;
import java.util.ArrayList;
import java.util.List;

public class SwitchAdapter extends RecyclerView.Adapter<SwitchAdapter.VH> {

    public interface OnItemClick { void onItemClick(Device d); }
    private final OnItemClick listener;
    private final List<Device> data = new ArrayList<>();

    public SwitchAdapter(OnItemClick l){ this.listener = l; }

    public void submit(List<Device> items){
        data.clear();
        if(items!=null) data.addAll(items);
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder{
        ItemSwitchBinding b;
        VH(ItemSwitchBinding b){ super(b.getRoot()); this.b = b; }
    }

    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p, int v) {
        return new VH(ItemSwitchBinding.inflate(LayoutInflater.from(p.getContext()), p, false));
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        Device d = data.get(pos);
        h.b.txtBrand.setText("Marca: " + d.brand);
        h.b.txtModel.setText("Modelo: " + d.model);
        h.b.txtPorts.setText("Cantidad de Puertos: " + (d.ports==null? "-" : d.ports));
        String tipo = (d.manageable!=null && d.manageable) ? "Administrable" : "No Administrable";
        h.b.txtType.setText("Tipo: " + tipo);
        String estado =
                d.status==null ? "-" :
                        d.status.name().equals("OPERATIVO")? "Operativo" :
                                d.status.name().equals("REPARACION")? "En reparación" : "Dado de baja";
        h.b.txtStatus.setText("Estado: " + estado);

        h.itemView.setOnClickListener(v -> listener.onItemClick(d));
    }

    @Override public int getItemCount() { return data.size(); }
}
