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

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> studentList;

    public StudentAdapter(List<Student> studentList) {
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.bind(student);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivStudentIcon;
        private TextView tvFullName, tvEmail, tvStudentCode;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            // INICIALIZAR LAS VISTAS UNA SOLA VEZ
            ivStudentIcon = itemView.findViewById(R.id.ivStudentIcon);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvStudentCode = itemView.findViewById(R.id.tvStudentCode);
            // ELIMINÉ LAS LÍNEAS DUPLICADAS
        }

        public void bind(Student student) {
            // Configurar la imagen
            ivStudentIcon.setImageResource(R.drawable.graduated);

            // Configurar textos
            tvFullName.setText(student.getFullName());
            tvEmail.setText(student.getEmail());
            tvStudentCode.setText("Código: " + student.getStudentCode());
        }
    }
}