package com.example.lab4.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4.R;

import java.util.List;

public class LigaAdapter extends RecyclerView.Adapter<LigaAdapter.LigaViewHolder> {

    private List<Liga> ligas;

    public LigaAdapter(List<Liga> ligas) {
        this.ligas = ligas;
    }

    @NonNull
    @Override
    public LigaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_liga, parent, false);
        return new LigaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LigaViewHolder holder, int position) {
        Liga liga = ligas.get(position);
        holder.textViewLigaID.setText(liga.getId());
        holder.textViewLigaNombre.setText(liga.getName());
        holder.textViewLigaNombreAlt1.setText(liga.getAltName1());
        holder.textViewLigaNombreAlt2.setText(liga.getAltName2());
    }

    @Override
    public int getItemCount() {
        return ligas.size();
    }

    public static class LigaViewHolder extends RecyclerView.ViewHolder {

        TextView textViewLigaID, textViewLigaNombre, textViewLigaNombreAlt1, textViewLigaNombreAlt2;

        public LigaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewLigaID = itemView.findViewById(R.id.textViewLigaID);
            textViewLigaNombre = itemView.findViewById(R.id.textViewLigaNombre);
            textViewLigaNombreAlt1 = itemView.findViewById(R.id.textViewLigaNombreAlt1);
            textViewLigaNombreAlt2 = itemView.findViewById(R.id.textViewLigaNombreAlt2);
        }
    }

}
