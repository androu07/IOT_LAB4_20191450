package com.example.lab4.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lab4.R;

import java.util.List;

public class PosicionesAdapter extends RecyclerView.Adapter<PosicionesAdapter.PosicionesViewHolder> {

    private List<Equipo> equiposList;

    public PosicionesAdapter(List<Equipo> equiposList) {
        this.equiposList = equiposList;
    }

    @NonNull
    @Override
    public PosicionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_posiciones, parent, false);
        return new PosicionesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PosicionesViewHolder holder, int position) {
        Equipo equipo = equiposList.get(position);
        holder.bind(equipo);
    }

    @Override
    public int getItemCount() {
        return equiposList.size();
    }

    static class PosicionesViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNombre, textViewRanking, textViewResultados, textViewGoles;
        private ImageView imageViewBadge;

        public PosicionesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewBadge = itemView.findViewById(R.id.imageViewBadge);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewRanking = itemView.findViewById(R.id.textViewRanking);
            textViewResultados = itemView.findViewById(R.id.textViewResultados);
            textViewGoles = itemView.findViewById(R.id.textViewGoles);
        }

        public void bind(Equipo equipo) {
            textViewNombre.setText(equipo.getStrTeam());
            textViewRanking.setText(String.valueOf(equipo.getIntRank()));
            textViewResultados.setText("V: " + equipo.getIntWin() + ", E: " + equipo.getIntDraw() + ", D: " + equipo.getIntLoss());
            textViewGoles.setText("GF: " + equipo.getIntGoalsFor() + ", GC: " + equipo.getIntGoalsAgainst() + ", DG: " + equipo.getIntGoalDifference());

            // Cargar el badge con Glide
            Glide.with(itemView.getContext())
                    .load(equipo.getStrBadge()) // URL del badge
                    .into(imageViewBadge);
        }
    }
}
