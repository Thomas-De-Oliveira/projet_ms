package com.project.student.dto;

import com.project.student.entity.Student;

public class StudentWithSchoolDto {
    private Student student;
    private SchoolDto school;

    public StudentWithSchoolDto(Student student, SchoolDto school) {
        this.student = student;
        this.school = school;
    }

    // Getters et setters (ou vous pouvez utiliser Lombok pour générer les méthodes automatiquement)
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public SchoolDto getSchool() {
        return school;
    }

    public void setSchool(SchoolDto school) {
        this.school = school;
    }
}
