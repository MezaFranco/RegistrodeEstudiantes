package com.admarchivos.registrodeestudiantes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.admarchivos.registrodeestudiantes.R;

/**
 * Actividad principal de la aplicación - Pantalla de bienvenida
 * Sirve como punto de entrada y navegación a otras funcionalidades
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Metodo llamado cuando la actividad es creada por primera vez
     * Se encarga de inicializar la interfaz de usuario y configurar los eventos
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Establece el layout XML que define la interfaz de usuario
        setContentView(R.layout.activity_main);

        // Obtiene referencia al botón del layout usando su ID
        Button btnGoToRegister = findViewById(R.id.btnGoToRegister);

        // Configura el evento click del botón usando
        // Intent explícito para navegar a RegisterActivity
        btnGoToRegister.setOnClickListener(v -> {
            // Crea un Intent explícito indicando la actividad origen y destino
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);

            startActivity(intent);

            // Nota: No se llama a finish() para mantener esta actividad en el stack
            // y permitir volver atrás con el botón de retroceso
        });
    }
}