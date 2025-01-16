package com.project.student.service;

import com.project.student.dto.SchoolDto;
import com.project.student.dto.StudentWithSchoolDto;
import com.project.student.entity.Student;
import com.project.student.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final RestTemplate restTemplate;
    private String schoolServiceUrl = "http://localhost:8080/schools/";

    public StudentService(StudentRepository studentRepository, RestTemplate restTemplate) {
        this.studentRepository = studentRepository;
        this.restTemplate = restTemplate;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findById(String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student with id " + id + " not found"));
    }

    public StudentWithSchoolDto findByIdWithSchool(String id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        ResponseEntity<SchoolDto> response = restTemplate.getForEntity(schoolServiceUrl + id, SchoolDto.class);

        Student student = studentOptional.orElseThrow(() -> new RuntimeException("Student with id " + id + " not found"));

        return new StudentWithSchoolDto(student, response.getBody());
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public Student update(String id, Student student) {
        Student existingStudent = findById(id);
        existingStudent.setName(student.getName());
        existingStudent.setGenre(student.getGenre());
        existingStudent.setSchoolId(student.getSchoolId());
        return studentRepository.save(existingStudent);
    }

    public void delete(String id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student with id " + id + " not found");
        }
        studentRepository.deleteById(id);
    }
}