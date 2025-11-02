package com.admarchivos.registrodeestudiantes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import com.google.android.material.button.MaterialButton;
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
        // Hacer status bar transparente
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        // Para texto/iconos claros en status bar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        // Establece el layout XML que define la interfaz de usuario
        setContentView(R.layout.activity_main);

        // Obtiene referencia al botón del layout usando su ID CORREGIDO
        MaterialButton btnGoToRegister = findViewById(R.id.btnGoToRegister);

        // Configura el evento táctil del botón con retroalimentación visual
        btnGoToRegister.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Efecto visual: escalar el botón y cambiar color al presionar
                        v.animate()
                                .scaleX(0.95f)
                                .scaleY(0.95f)
                                .setDuration(100)
                                .start();
                        // Cambiar a drawable presionado con bordes redondeados
                        v.setBackgroundResource(R.drawable.button_gradient_pressed);
                        return true;

                    case MotionEvent.ACTION_UP:
                        // Efecto visual: restaurar tamaño y color del botón
                        v.animate()
                                .scaleX(1.0f)
                                .scaleY(1.0f)
                                .setDuration(100)
                                .start();
                        // Restaurar color original
                        v.setBackgroundResource(R.drawable.button_gradient);

                        // Navegación con delay para mejor experiencia
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Crea un Intent explícito indicando la actividad origen y destino
                                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);

                                startActivity(intent);

                                // Nota: No se llama a finish() para mantener esta actividad en el stack
                                // y permitir volver atrás con el botón de retroceso
                            }
                        }, 150);

                        return true;

                    case MotionEvent.ACTION_CANCEL:
                        // Restaurar tamaño y color si el usuario desliza fuera del botón
                        v.animate()
                                .scaleX(1.0f)
                                .scaleY(1.0f)
                                .setDuration(100)
                                .start();
                        // Restaurar color original
                        v.setBackgroundResource(R.drawable.button_gradient);
                        return true;
                }
                return false;
            }
        });
    }
}