package com.example.lab4.fragmentos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultadosFragment extends Fragment implements SensorEventListener {

    private RecyclerView recyclerView;
    private ResultadosAdapter adapter;
    private List<Resultado> resultadosList;

    private EditText editTextLigaId;
    private EditText editTextSeason;
    private EditText editTextRonda;
    private Button btnBuscar;

    // Acelerómetro
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private static final float THRESHOLD = 20.0f;
    private boolean isDialogShowing = false;

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

        // Inicializar el sensor
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

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

                controlSensorState();
            }

            @Override
            public void onFailure(Call<ResultadosResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Uno de los datos ingresados de la competencia no es correcta.", Toast.LENGTH_LONG).show();
            }
        });
    }

    // Controlar el estado del sensor
    private void controlSensorState() {
        if (resultadosList.isEmpty()) {
            sensorManager.unregisterListener(this);
        } else {
            if (accelerometer != null) {
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    // Gestión del sensor
    @Override
    public void onResume() {
        super.onResume();
        controlSensorState();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Desregistrar el sensor
        sensorManager.unregisterListener(this);
        isDialogShowing = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float acceleration = (float) Math.sqrt(Math.pow(event.values[0], 2) + Math.pow(event.values[1], 2) + Math.pow(event.values[2], 2));
            if (acceleration > THRESHOLD && !isDialogShowing) {
                isDialogShowing = true;
                mostrarDialogoConfirmacion();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No es necesario hacer nada aquí en este caso
    }

    private void mostrarDialogoConfirmacion() {
        new MaterialAlertDialogBuilder(getContext())
                .setTitle("Confirmación")
                .setMessage("¿Desea limpiar los resultados?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resultadosList.clear();
                        adapter.notifyDataSetChanged();
                        editTextLigaId.setText("");
                        editTextSeason.setText("");
                        editTextRonda.setText("");
                        isDialogShowing = false;
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isDialogShowing = false;
                    }
                })
                .show();
    }

}