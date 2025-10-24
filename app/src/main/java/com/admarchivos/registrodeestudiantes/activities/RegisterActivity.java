package com.admarchivos.registrodeestudiantes.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.admarchivos.registrodeestudiantes.R;
import com.admarchivos.registrodeestudiantes.models.Student;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Actividad para registrar nuevos estudiantes
 * Permite capturar datos del estudiante y guardarlos en SharedPreferences
 */
public class RegisterActivity extends AppCompatActivity {

    // Campos de entrada de texto para los datos del estudiante
    private TextInputEditText etName, etLastName, etEmail, etStudentCode;

    // SharedPreferences para persistencia de datos
    private SharedPreferences sharedPreferences;

    /**
     * Metodo llamado cuando la actividad es creada
     * Inicializa la interfaz y configura los listeners de botones
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();

        // Obtener referencia a SharedPreferences con nombre "StudentPrefs"
        sharedPreferences = getSharedPreferences("StudentPrefs", MODE_PRIVATE);

        // Obtener referencias a los botones del layout
        Button btnSave = findViewById(R.id.btnSave);
        Button btnViewList = findViewById(R.id.btnViewList);

        // Configurar listeners para los botones
        btnSave.setOnClickListener(v -> saveStudent());
        btnViewList.setOnClickListener(v -> goToStudentList());
    }

    /**
     * Inicializa las vistas encontrando los elementos del layout por su ID
     * Asigna las referencias a las variables de instancia
     */
    private void initViews() {
        etName = findViewById(R.id.etName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etStudentCode = findViewById(R.id.etStudentCode);
    }

    /**
     * Guarda un nuevo estudiante en SharedPreferences
     * Valida los campos, crea el objeto Student y lo agrega a la lista existente
     */
    private void saveStudent() {
        // Obtener y limpiar los textos de los campos de entrada
        String name = etName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String studentCode = etStudentCode.getText().toString().trim();

        // Validación básica - Verificar que ningún campo esté vacío
        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || studentCode.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return; // Detener ejecución si hay campos vacíos
        }

        // Crear nuevo objeto Student con los datos capturados
        Student newStudent = new Student(name, lastName, email, studentCode);

        // Obtener lista actual de estudiantes desde SharedPreferences
        List<Student> studentList = getStudentsFromSharedPreferences();

        studentList.add(newStudent);

        saveStudentsToSharedPreferences(studentList);


        Toast.makeText(this, "Estudiante registrado exitosamente", Toast.LENGTH_SHORT).show();

        clearFields();
    }

    /**
     * Obtiene la lista de estudiantes desde SharedPreferences
     * Convierte el JSON almacenado a una lista de objetos Student
     *
     * @return Lista de estudiantes, lista vacía si no hay datos
     */
    private List<Student> getStudentsFromSharedPreferences() {
        // Obtener el JSON de estudiantes, usar "[]" como valor por defecto (array vacío)
        String studentsJson = sharedPreferences.getString("students_list", "[]");

        // Crear instancia de Gson para conversión JSON-Objeto
        Gson gson = new Gson();

        // Crear TypeToken para que Gson sepa el tipo específico (List<Student>)
        Type type = new TypeToken<List<Student>>(){}.getType();

        // Convertir JSON a lista de objetos Student
        List<Student> studentList = gson.fromJson(studentsJson, type);

        // Retornar la lista o una nueva lista vacía si es null
        return studentList != null ? studentList : new ArrayList<>();
    }

    /**
     * Guarda la lista de estudiantes en SharedPreferences
     * Convierte la lista a formato JSON para almacenamiento
     *
     * @param studentList Lista de estudiantes a guardar
     */
    private void saveStudentsToSharedPreferences(List<Student> studentList) {
        // Obtener editor para modificar SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Convertir lista de estudiantes a formato JSON
        Gson gson = new Gson();
        String studentsJson = gson.toJson(studentList);

        // Guardar el JSON en SharedPreferences con la clave "students_list"
        editor.putString("students_list", studentsJson);

        // Aplicar cambios (apply() es asíncrono, commit() es síncrono)
        editor.apply();
    }

    private void clearFields() {
        etName.setText("");
        etLastName.setText("");
        etEmail.setText("");
        etStudentCode.setText("");
    }

    private void goToStudentList() {
        Intent intent = new Intent(this, StudentListActivity.class);
        startActivity(intent);
    }
}