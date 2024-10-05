package com.example.lab4;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.lab4.fragmentos.LigaFragment;
import com.example.lab4.fragmentos.PosicionesFragment;
import com.example.lab4.fragmentos.ResultadosFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.nav_ligas) {
                    selectedFragment = new LigaFragment();
                } else if (item.getItemId() == R.id.nav_posiciones) {
                    selectedFragment = new PosicionesFragment();
                } else if (item.getItemId() == R.id.nav_resultados) {
                    selectedFragment = new ResultadosFragment();
                }

                // Reemplazar el fragmento en el contenedor
                if (selectedFragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }

                return true;
            }
        });

        // Cargar el fragmento por defecto (puedes elegir cualquiera)
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_ligas);
        }
    }
}