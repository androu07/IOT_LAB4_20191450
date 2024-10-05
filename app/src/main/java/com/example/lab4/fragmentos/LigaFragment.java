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
import com.example.lab4.recyclerview.Liga;
import com.example.lab4.recyclerview.LigaAdapter;
import com.example.lab4.retrofit.ApiService;
import com.example.lab4.retrofit.LigaResponse;
import com.example.lab4.retrofit.LigaResponsePorPais;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LigaFragment extends Fragment {

    private EditText editTextBuscarPorPais;
    private RecyclerView recyclerViewLigas;
    private Button btnBuscarPorPais;
    private LigaAdapter adapter;
    private List<Liga> leaguesList = new ArrayList<>();

    //Retrofit
    private Retrofit retrofit;
    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_liga, container, false);

        editTextBuscarPorPais = view.findViewById(R.id.editTextBuscarPorPais);
        recyclerViewLigas = view.findViewById(R.id.recyclerViewLigas);
        btnBuscarPorPais = view.findViewById(R.id.btnBuscarPorPais);

        // Configurar RecyclerView
        recyclerViewLigas.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LigaAdapter(leaguesList);
        recyclerViewLigas.setAdapter(adapter);

        // Inicializar Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.thesportsdb.com/api/v1/json/3/")  // Base URL de la API
                .addConverterFactory(GsonConverterFactory.create())      // Para convertir JSON en objetos Java
                .build();

        // Crear la instancia de ApiService
        apiService = retrofit.create(ApiService.class);

        btnBuscarPorPais.setOnClickListener(v -> {
            String country = editTextBuscarPorPais.getText().toString().trim();
            if (country.isEmpty()) {
                obtenerLigasGeneral();
            } else {
                obtenerLigasPorPais(country);
            }
        });

        return view;

    }

    // Método para obtener las ligas en general
    private void obtenerLigasGeneral() {
        leaguesList.clear();
        Call<LigaResponse> call = apiService.getAllLeagues();
        call.enqueue(new Callback<LigaResponse>() {
            @Override
            public void onResponse(Call<LigaResponse> call, Response<LigaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Liga> fetchedLeagues = response.body().getLeagues();
                    if (fetchedLeagues != null) {
                        leaguesList.clear();
                        leaguesList.addAll(fetchedLeagues);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "No se encontraron ligas.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Error al obtener las ligas.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LigaResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para obtener ligas por país
    private void obtenerLigasPorPais(String country) {
        leaguesList.clear();
        Call<LigaResponsePorPais> call = apiService.getLeaguesByCountry(country);
        call.enqueue(new Callback<LigaResponsePorPais>() {
            @Override
            public void onResponse(Call<LigaResponsePorPais> call, Response<LigaResponsePorPais> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Liga> fetchedLeagues = response.body().getLeagues();
                    if (fetchedLeagues != null && !fetchedLeagues.isEmpty()) {
                        leaguesList.addAll(fetchedLeagues);
                        adapter.notifyDataSetChanged();  // Notificar el cambio
                    } else {
                        Toast.makeText(getContext(), "No se encontraron ligas para el país: " + country, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "No se encontraron ligas para el país ingresado.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LigaResponsePorPais> call, Throwable t) {
                Toast.makeText(getContext(), "Error en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}