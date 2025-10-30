package com.admarchivos.registrodeestudiantes.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.admarchivos.registrodeestudiantes.R;
import com.admarchivos.registrodeestudiantes.adapters.StudentAdapter;
import com.admarchivos.registrodeestudiantes.models.Student;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity para mostrar la lista de estudiantes registrados
 * Permite visualizar todos los estudiantes guardados en SharedPreferences
 */
public class StudentListActivity extends AppCompatActivity {

    // Componentes de la UI
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private List<Student> studentList;

    /**
     * Metodo llamado cuando la actividad es creada
     * Configura la interfaz y carga los datos
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

        setContentView(R.layout.activity_student_list);

        // Inicializar componentes de la UI
        initViews();

        // Configurar el RecyclerView para mostrar la lista
        setupRecyclerView();

        // Cargar estudiantes desde SharedPreferences
        loadStudentsFromSharedPreferences();

        // Configurar botón de regreso
        Button btnBack = findViewById(R.id.btnBackToRegister);
        btnBack.setOnClickListener(v -> goBackToRegister());
    }

    /**
     * Inicializa las vistas y variables necesarias
     * - Encuentra el RecyclerView en el layout
     * - Inicializa la lista de estudiantes vacía
     */
    private void initViews() {
        recyclerView = findViewById(R.id.recyclerViewStudents);
        studentList = new ArrayList<>();
    }

    /**
     * Configura el RecyclerView con su adapter y layout manager
     * - Crea el adapter con la lista de estudiantes
     * - Establece el LinearLayoutManager para disposición vertical
     * - Asigna el adapter al RecyclerView
     */
    private void setupRecyclerView() {
        adapter = new StudentAdapter(studentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    /**
     * Carga la lista de estudiantes desde SharedPreferences
     * - Obtiene el JSON guardado con la lista de estudiantes
     * - Convierte el JSON a lista de objetos Student usando Gson
     * - Actualiza la lista y notifica al adapter
     */
    private void loadStudentsFromSharedPreferences() {
        // Obtener SharedPreferences donde se guardan los datos
        SharedPreferences sharedPreferences = getSharedPreferences("StudentPrefs", MODE_PRIVATE);

        // Obtener el JSON de estudiantes, si no existe retorna array vacío "[]"
        String studentsJson = sharedPreferences.getString("students_list", "[]");

        // Configurar Gson para convertir JSON a objetos Java
        Gson gson = new Gson();

        // Crear el TypeToken para que Gson sepa convertir a List<Student>
        Type type = new TypeToken<List<Student>>(){}.getType();

        // Convertir el JSON a lista de estudiantes
        List<Student> loadedStudents = gson.fromJson(studentsJson, type);

        // Si se cargaron estudiantes, actualizar la lista
        if (loadedStudents != null) {
            // Limpiar lista actual y agregar todos los estudiantes cargados
            studentList.clear();
            studentList.addAll(loadedStudents);

            // Notificar al adapter que los datos cambiaron
            adapter.notifyDataSetChanged();
        }

        // Si loadedStudents es null, la lista permanece vacía
    }

    /**
     * Regresa a la actividad de registro
     * - Crea un intent para abrir RegisterActivity
     * - Inicia la actividad y finaliza la actual
     */
    private void goBackToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish(); // Cierra esta actividad para liberar memoria
    }
}