package com.admarchivos.registrodeestudiantes.models;

/**
 * Clase modelo que representa a un Estudiante en el sistema
 * Contiene los datos básicos de identificación y contacto del estudiante
 * Se utiliza para transferir datos entre actividades y para persistencia
 */
public class Student {

    // Atributos privados que definen las propiedades de un estudiante
    private String name;
    private String lastName;       // Apellido del estudiante
    private String email;
    private String studentCode;

    /**
     * Constructor para crear una nueva instancia de Student
     *
     * @param name Nombre del estudiante (no nulo)
     * @param lastName Apellido del estudiante (no nulo)
     * @param email Correo electrónico del estudiante (no nulo)
     * @param studentCode Código único del estudiante (no nulo)
     */
    public Student(String name, String lastName, String email, String studentCode) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.studentCode = studentCode;
    }

    // ========== MÉTODOS GETTER ==========
    // Proporcionan acceso de solo lectura a los atributos privados

    /**
     * @return El nombre del estudiante
     */
    public String getName() {
        return name;
    }

    /**
     * @return El apellido del estudiante
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return El correo electrónico del estudiante
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return El código único de identificación del estudiante
     */
    public String getStudentCode() {
        return studentCode;
    }

    //  MÉTODOS ADICIONALES

    /**
     * Genera el nombre completo del estudiante concatenando nombre y apellido
     * Útil para mostrar en listas o interfaces de usuario
     *
     * @return Nombre completo en formato "Nombre Apellido"
     */
    public String getFullName() {
        return name + " " + lastName;
    }

    // Nota: No se incluyen setters para mantener la inmutabilidad del objeto
    // Una vez creado un Student, sus datos no pueden modificarse
    // Esto previene cambios inconsistentes en los datos
}