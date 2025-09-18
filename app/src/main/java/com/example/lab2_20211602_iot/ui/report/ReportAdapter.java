package com.example.lab2_20211602_iot.ui.report;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lab2_20211602_iot.data.model.DeviceStatus;
import com.example.lab2_20211602_iot.databinding.ItemReportDeviceBinding;
import com.example.lab2_20211602_iot.databinding.ItemReportHeaderBinding;
import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<ReportRow> data = new ArrayList<>();
    public void submit(List<ReportRow> rows){ data.clear(); if(rows!=null) data.addAll(rows); notifyDataSetChanged(); }

    private static final int T_HEADER = 0, T_ITEM = 1;

    @Override public int getItemViewType(int position) {
        return data.get(position).kind == ReportRow.Kind.HEADER ? T_HEADER : T_ITEM;
    }

    @NonNull @Override public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup p, int vt) {
        LayoutInflater inf = LayoutInflater.from(p.getContext());
        if(vt==T_HEADER) return new VHHeader(ItemReportHeaderBinding.inflate(inf, p, false));
        return new VHItem(ItemReportDeviceBinding.inflate(inf, p, false));
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder h, int pos) {
        ReportRow row = data.get(pos);
        if (h instanceof VHHeader) {
            VHHeader v = (VHHeader) h;
            String title = row.status == DeviceStatus.OPERATIVO ? "Operativos" :
                    row.status == DeviceStatus.REPARACION ? "En Reparación" : "Dados de Baja";
            v.b.txtHeader.setText(title + " (" + row.count + ")");
        } else {
            VHItem v = (VHItem) h;
            v.b.txtLine.setText(row.device.brand + " " + row.device.model);
        }
    }

    @Override public int getItemCount(){ return data.size(); }

    static class VHHeader extends RecyclerView.ViewHolder {
        ItemReportHeaderBinding b; VHHeader(ItemReportHeaderBinding b){ super(b.getRoot()); this.b=b; }
    }
    static class VHItem extends RecyclerView.ViewHolder {
        ItemReportDeviceBinding b; VHItem(ItemReportDeviceBinding b){ super(b.getRoot()); this.b=b; }
    }
}
