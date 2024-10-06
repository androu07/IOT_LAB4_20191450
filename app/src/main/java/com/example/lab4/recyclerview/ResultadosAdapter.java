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

public class ResultadosAdapter extends RecyclerView.Adapter<ResultadosAdapter.ResultadosViewHolder> {

    private List<Resultado> resultadosList;

    public ResultadosAdapter(List<Resultado> resultadosList) {
        this.resultadosList = resultadosList;
    }

    @NonNull
    @Override
    public ResultadosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resultados, parent, false);
        return new ResultadosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultadosViewHolder holder, int position) {
        Resultado resultado = resultadosList.get(position);
        holder.bind(resultado);
    }

    @Override
    public int getItemCount() {
        return resultadosList.size();
    }

    static class ResultadosViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewNombreCompetencia, textViewRonda, textViewEquipos, textViewResultado, textViewFecha;
        private ImageView imageViewLogoCompetencia;

        public ResultadosViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombreCompetencia = itemView.findViewById(R.id.textViewNombreCompetencia);
            textViewRonda = itemView.findViewById(R.id.textViewRonda);
            textViewEquipos = itemView.findViewById(R.id.textViewEquipos);
            textViewResultado = itemView.findViewById(R.id.textViewResultado);
            textViewFecha = itemView.findViewById(R.id.textViewFecha);
            imageViewLogoCompetencia = itemView.findViewById(R.id.imageViewLogoCompetencia);
        }

        public void bind(Resultado resultado) {
            textViewNombreCompetencia.setText(resultado.getStrLeague());
            textViewRonda.setText("Ronda: " + resultado.getIntRound());
            textViewEquipos.setText(resultado.getStrHomeTeam() + " vs " + resultado.getStrAwayTeam());
            textViewResultado.setText("Resultado: " + resultado.getIntHomeScore() + " - " + resultado.getIntAwayScore());
            textViewFecha.setText("Fecha: " + resultado.getDateEventLocal());

            Glide.with(itemView.getContext())
                    .load(resultado.getStrLeagueBadge())
                    .into(imageViewLogoCompetencia);
        }
    }
}
