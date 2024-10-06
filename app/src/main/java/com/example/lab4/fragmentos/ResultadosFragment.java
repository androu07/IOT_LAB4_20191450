package com.example.lab4.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab4.R;
import com.example.lab4.recyclerview.Resultado;
import com.example.lab4.recyclerview.ResultadosAdapter;
import com.example.lab4.retrofit.ApiService;
import com.example.lab4.retrofit.ResultadosResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultadosFragment extends Fragment {

    private RecyclerView recyclerView;
    private ResultadosAdapter adapter;
    private List<Resultado> resultadosList;

    private EditText editTextLigaId;
    private EditText editTextSeason;
    private EditText editTextRonda;
    private Button btnBuscar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resultados, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewResultados);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        resultadosList = new ArrayList<>();
        adapter = new ResultadosAdapter(resultadosList);
        recyclerView.setAdapter(adapter);

        editTextLigaId = view.findViewById(R.id.editTextBuscarPorLigaID);
        editTextRonda = view.findViewById(R.id.editTextBuscarPorRonda);
        editTextSeason = view.findViewById(R.id.editTextBuscarPorSeason);
        btnBuscar = view.findViewById(R.id.btnBuscardorTriple);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ligaId = editTextLigaId.getText().toString().trim();
                String ronda = editTextRonda.getText().toString().trim();
                String season = editTextSeason.getText().toString().trim();

                if (ligaId.isEmpty() || season.isEmpty() || ronda.isEmpty()) {
                    Toast.makeText(getContext(), "Por favor complete todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                buscarResultados(ligaId, season, ronda);
            }
        });

        return view;
    }

    private void buscarResultados(String ligaId, String season, String ronda) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.thesportsdb.com/api/v1/json/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);
        Call<ResultadosResponse> call = api.getResultados(ligaId, ronda, season);

        call.enqueue(new Callback<ResultadosResponse>() {
            @Override
            public void onResponse(Call<ResultadosResponse> call, Response<ResultadosResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Resultado> resultados = response.body().getResultados();

                    if (resultados != null && !resultados.isEmpty()) {
                        resultadosList.clear();
                        resultadosList.addAll(resultados);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "Uno de los datos ingresados de la competencia no es correcta.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Uno de los datos ingresados de la competencia no es correcta.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResultadosResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Uno de los datos ingresados de la competencia no es correcta.", Toast.LENGTH_LONG).show();
            }
        });
    }
}