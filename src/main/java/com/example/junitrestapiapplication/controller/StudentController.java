package com.example.junitrestapiapplication.controller;

import com.example.junitrestapiapplication.entity.Student;
import com.example.junitrestapiapplication.repository.StudentRepository;
import com.example.junitrestapiapplication.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;


    /**
     * This methods add student to database  .
     *
     * @param student the student  .
     */
    @PostMapping("/student/add")
    public Student createStudentRecord(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    /**
     * This method will fetch all the students from database  .
     *
     * @return list of students  .
     */
    @GetMapping("/student/get")
    public List<Student> getAllStudentRecord() {
        return studentRepository.findAll();
    }

    /**
     * this method fetch student with the help of it's id  .
     *
     * @param studentId the Id  .
     * @return student  .
     */
    @GetMapping("/student/get/{studentId}")
    public Student getStudentById(@PathVariable("studentId") Long studentId) {

        return studentRepository.findById(studentId).get();

    }

    /**
     *   This method update the Student present in Database  .
     *
     * @param student updated student  .
     * @return student  .
     */
    @PutMapping("/student/update")
    public Student updateStudent(@RequestBody Student student) {

        if(student == null || student.getStudentId() == null) {
            throw new NullPointerException("Book record or Id must not be Null");
        }
        Optional<Student> optionalBook = studentRepository.findById(student.getStudentId());
        if(!optionalBook.isPresent()) {
            throw new NullPointerException("Student id" + student.getStudentId() + "Does not the exist");
        }

        Student existingStudentRecord = optionalBook.get();
        existingStudentRecord.setFirstName(student.getFirstName());
        existingStudentRecord.setLastName(student.getLastName());
        existingStudentRecord.setSubject(student.getSubject());
        return studentRepository.save(existingStudentRecord);

    }

    /**
     *   This method will delete Student from the Database  .
     *
     * @param id the Id  .
     */
    @DeleteMapping("/student/delete/{id}")
    public void deleteStudentById(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
    }
}
