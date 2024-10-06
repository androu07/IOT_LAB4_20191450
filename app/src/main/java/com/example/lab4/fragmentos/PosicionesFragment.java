package com.example.lab4.fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.lab4.recyclerview.Equipo;
import com.example.lab4.recyclerview.PosicionesAdapter;
import com.example.lab4.retrofit.ApiService;
import com.example.lab4.retrofit.PosicionesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PosicionesFragment extends Fragment {

    private EditText editTextBuscarPorLigaID, editTextBuscarPorTemporada;
    private RecyclerView recyclerViewPosiciones;
    private Button btnBuscar;
    private PosicionesAdapter adapter;
    private List<Equipo> equiposList = new ArrayList<>();

    // Retrofit
    private Retrofit retrofit;
    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posiciones, container, false);

        editTextBuscarPorLigaID = view.findViewById(R.id.editTextBuscarPorLigaID);
        editTextBuscarPorTemporada = view.findViewById(R.id.editTextBuscarPorTemporada);
        recyclerViewPosiciones = view.findViewById(R.id.recyclerViewPosiciones);
        btnBuscar = view.findViewById(R.id.btnBuscardorDoble);

        // Configurar RecyclerView
        recyclerViewPosiciones.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PosicionesAdapter(equiposList);
        recyclerViewPosiciones.setAdapter(adapter);

        // Inicializar Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.thesportsdb.com/api/v1/json/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Crear la instancia de ApiService
        apiService = retrofit.create(ApiService.class);

        btnBuscar.setOnClickListener(v -> {
            String idLiga = editTextBuscarPorLigaID.getText().toString().trim();
            String temporada = editTextBuscarPorTemporada.getText().toString().trim();
            if (idLiga.isEmpty() || temporada.isEmpty()) {
                Toast.makeText(getContext(), "Por favor, ingrese ambos valores.", Toast.LENGTH_SHORT).show();
            } else {
                obtenerPosiciones(idLiga, temporada);
            }
        });

        return view;
    }

    private void obtenerPosiciones(String idLiga, String temporada) {
        equiposList.clear();
        Log.d("PosicionesFragment", "idLiga: " + idLiga + ", temporada: " + temporada);
        Call<PosicionesResponse> call = apiService.getPosiciones(idLiga, temporada);
        call.enqueue(new Callback<PosicionesResponse>() {
            @Override
            public void onResponse(Call<PosicionesResponse> call, Response<PosicionesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Equipo> equipos = response.body().getTeams(); // Asegúrate de que este método existe
                    if (equipos != null && !equipos.isEmpty()) {
                        adapter = new PosicionesAdapter(equipos);
                        recyclerViewPosiciones.setAdapter(adapter);
                    } else {
                        Toast.makeText(getContext(), "El id o la temporada de liga ingresada no es correcta.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "El id o la temporada de liga ingresada no es correcta.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PosicionesResponse> call, Throwable t) {
                Toast.makeText(getContext(), "El id o la temporada de liga ingresada no es correcta.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}