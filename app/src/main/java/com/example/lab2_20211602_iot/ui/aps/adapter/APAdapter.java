package com.example.lab2_20211602_iot.ui.aps.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lab2_20211602_iot.data.model.APFrequency;
import com.example.lab2_20211602_iot.data.model.Device;
import com.example.lab2_20211602_iot.databinding.ItemApBinding;
import java.util.ArrayList;
import java.util.List;

public class APAdapter extends RecyclerView.Adapter<APAdapter.VH> {
    public interface OnItemClick { void onItemClick(Device d); }
    private final OnItemClick listener;
    private final List<Device> data = new ArrayList<>();
    public APAdapter(OnItemClick l){ listener=l; }

    public void submit(List<Device> items){ data.clear(); if(items!=null) data.addAll(items); notifyDataSetChanged(); }

    static class VH extends RecyclerView.ViewHolder{
        ItemApBinding b; VH(ItemApBinding b){ super(b.getRoot()); this.b=b; }
    }

    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p, int v) {
        return new VH(ItemApBinding.inflate(LayoutInflater.from(p.getContext()), p, false));
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        Device d = data.get(pos);
        h.b.txtBrand.setText("Marca: " + d.brand);
        h.b.txtFreq.setText("Frecuencia: " + freqLabel(d.frequency));
        h.b.txtRange.setText("Alcance: " + (d.rangeMeters==null? "-" : d.rangeMeters + " metros"));
        String estado = d.status==null? "-" :
                d.status.name().equals("OPERATIVO")? "Operativo" :
                        d.status.name().equals("REPARACION")? "En reparación" : "Dado de baja";
        h.b.txtStatus.setText("Estado: " + estado);

        h.itemView.setOnClickListener(v -> listener.onItemClick(d));
    }

    @Override public int getItemCount(){ return data.size(); }

    private String freqLabel(APFrequency f){
        if (f==null) return "-";
        switch (f){
            case GHZ_2_4: return "2.4 GHz";
            case GHZ_5:   return "5 GHz";
            default:      return "Dual Band";
        }
    }
}
