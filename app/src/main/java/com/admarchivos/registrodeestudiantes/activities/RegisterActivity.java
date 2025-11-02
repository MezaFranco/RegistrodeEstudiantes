package com.admarchivos.registrodeestudiantes.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hacer status bar transparente
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        setContentView(R.layout.activity_register);

        initViews();

        // Obtener referencia a SharedPreferences con nombre "StudentPrefs"
        sharedPreferences = getSharedPreferences("StudentPrefs", MODE_PRIVATE);

        // Obtener referencias a los botones del layout
        MaterialButton btnSave = findViewById(R.id.btnSave);
        MaterialButton btnViewList = findViewById(R.id.btnViewList);

        // Configurar eventos táctiles igual que en MainActivity
        setupButtonWithEffects(btnSave, v -> saveStudent(), R.drawable.button_gradient_save_pressed, R.drawable.button_white_rounded);
        setupButtonWithEffects(btnViewList, v -> goToStudentList(), R.drawable.button_gradient_view_pressed, R.drawable.button_white_rounded);
    }

    /**
     * Configura un botón con efectos visuales y funcionalidad (igual que MainActivity)
     */
    private void setupButtonWithEffects(MaterialButton button, View.OnClickListener clickAction, int pressedDrawable, int normalDrawable) {
        button.setOnTouchListener(new View.OnTouchListener() {
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
                        // Cambiar a drawable presionado
                        v.setBackgroundResource(pressedDrawable);
                        return true;

                    case MotionEvent.ACTION_UP:
                        // Efecto visual: restaurar tamaño y color del botón
                        v.animate()
                                .scaleX(1.0f)
                                .scaleY(1.0f)
                                .setDuration(100)
                                .start();
                        // Restaurar color original
                        v.setBackgroundResource(normalDrawable);

                        // Ejecutar la acción del botón
                        clickAction.onClick(v);
                        return true;

                    case MotionEvent.ACTION_CANCEL:
                        // Restaurar tamaño y color si el usuario desliza fuera del botón
                        v.animate()
                                .scaleX(1.0f)
                                .scaleY(1.0f)
                                .setDuration(100)
                                .start();
                        // Restaurar color original
                        v.setBackgroundResource(normalDrawable);
                        return true;
                }
                return false;
            }
        });
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etStudentCode = findViewById(R.id.etStudentCode);

        // Aplicar filtros y validación en tiempo real
        setupRealTimeValidation();
    }

    /**
     * Configura la validación en tiempo real para nombre y apellido
     */
    private void setupRealTimeValidation() {
        // Aplicar filtro para solo letras y espacios
        InputFilter lettersFilter = getLettersAndSpacesOnlyFilter();
        etName.setFilters(new InputFilter[]{lettersFilter, new InputFilter.LengthFilter(30)});
        etLastName.setFilters(new InputFilter[]{lettersFilter, new InputFilter.LengthFilter(30)});

        // Validación en tiempo real para nombre
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    validateNameField(etName, s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Validación en tiempo real para apellido
        etLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    validateNameField(etLastName, s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    /**
     * Valida un campo de nombre/apellido en tiempo real
     */
    private void validateNameField(TextInputEditText field, String text) {
        if (!text.isEmpty() && !isValidName(text)) {
            field.setError("Solo se permiten letras y espacios");
        } else {
            field.setError(null);
        }
    }

    /**
     * Filtro que permite SOLO letras, espacios y caracteres acentuados
     */
    private InputFilter getLettersAndSpacesOnlyFilter() {
        return (source, start, end, dest, dstart, dend) -> {
            if (source.length() == 0) return null;

            StringBuilder filteredString = new StringBuilder();
            for (int i = start; i < end; i++) {
                char c = source.charAt(i);
                if (Character.isLetter(c) || c == ' ' ||
                        c == 'á' || c == 'é' || c == 'í' || c == 'ó' || c == 'ú' ||
                        c == 'Á' || c == 'É' || c == 'Í' || c == 'Ó' || c == 'Ú' ||
                        c == 'ñ' || c == 'Ñ' || c == 'ü' || c == 'Ü') {
                    filteredString.append(c);
                }
                // Los caracteres no permitidos se ignoran silenciosamente
            }
            return filteredString.toString();
        };
    }

    /**
     * Verifica si un texto contiene SOLO letras y espacios
     */
    private boolean isValidName(String text) {
        return text.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+$");
    }

    private void saveStudent() {
        // Obtener y limpiar los textos de los campos de entrada
        String name = etName.getText() != null ? etName.getText().toString().trim() : "";
        String lastName = etLastName.getText() != null ? etLastName.getText().toString().trim() : "";
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String studentCode = etStudentCode.getText() != null ? etStudentCode.getText().toString().trim() : "";

        // Validación básica - Verificar que ningún campo esté vacío
        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || studentCode.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar que nombre y apellido solo contengan letras y espacios
        if (!isValidName(name)) {
            etName.setError("Solo se permiten letras y espacios");
            etName.requestFocus();
            return;
        }

        if (!isValidName(lastName)) {
            etLastName.setError("Solo se permiten letras y espacios");
            etLastName.requestFocus();
            return;
        }

        // Validar formato de email
        if (!isValidEmail(email)) {
            etEmail.setError("Ingrese un email válido");
            etEmail.requestFocus();
            return;
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
     * Valida formato de email
     */
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
        return email.matches(emailPattern);
    }

    private List<Student> getStudentsFromSharedPreferences() {
        String studentsJson = sharedPreferences.getString("students_list", "[]");
        Gson gson = new Gson();
        Type type = new TypeToken<List<Student>>(){}.getType();
        List<Student> studentList = gson.fromJson(studentsJson, type);
        return studentList != null ? studentList : new ArrayList<>();
    }

    private void saveStudentsToSharedPreferences(List<Student> studentList) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String studentsJson = gson.toJson(studentList);
        editor.putString("students_list", studentsJson);
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