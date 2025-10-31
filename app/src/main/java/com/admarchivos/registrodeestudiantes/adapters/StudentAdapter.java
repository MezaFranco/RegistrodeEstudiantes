package com.admarchivos.registrodeestudiantes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.admarchivos.registrodeestudiantes.R;
import com.admarchivos.registrodeestudiantes.models.Student;
import java.util.List;

// Adaptador para RecyclerView que maneja la lista de estudiantes
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    // Lista de estudiantes que se mostrará en el RecyclerView
    private List<Student> studentList;

    // Constructor que recibe la lista de estudiantes
    public StudentAdapter(List<Student> studentList) {
        this.studentList = studentList;
    }

    // Metodo llamado cuando se necesita crear un nuevo ViewHolder
    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout del item de estudiante
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    // Metodo llamado para enlazar los datos de un estudiante con una vista específica
    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        // Obtener el estudiante en la posición actual
        Student student = studentList.get(position);
        // Enlazar los datos del estudiante con el ViewHolder
        holder.bind(student);
    }

    // Metodo que retorna el número total de elementos en la lista
    @Override
    public int getItemCount() {
        return studentList.size();
    }

    // ViewHolder que representa cada item de estudiante en el RecyclerView
    static class StudentViewHolder extends RecyclerView.ViewHolder {
        // Views del layout item_student
        private ImageView ivStudentIcon;
        private TextView tvFullName, tvEmail, tvStudentCode;

        // Constructor del ViewHolder - inicializa las views
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            // INICIALIZAR LAS VISTAS UNA SOLA VEZ
            ivStudentIcon = itemView.findViewById(R.id.ivStudentIcon);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvStudentCode = itemView.findViewById(R.id.tvStudentCode);

        }

        // Metodo para enlazar los datos del estudiante con las views
        public void bind(Student student) {
            // Configurar la imagen - usa una imagen predeterminada para todos los estudiantes
            ivStudentIcon.setImageResource(R.drawable.graduated);

            // Configurar textos con la información del estudiante
            tvFullName.setText(student.getFullName());
            tvEmail.setText(student.getEmail());
            tvStudentCode.setText("Código: " + student.getStudentCode());
        }
    }
}