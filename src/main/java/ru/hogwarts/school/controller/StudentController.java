package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final FacultyService facultyService;

    public StudentController(StudentService studentService, FacultyService facultyService) {
        this.studentService = studentService;
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> findStudents(@RequestParam(required = false) int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentService.findByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/betweenAge")
    public ResponseEntity<Collection<Student>> findStudentsByAge(@RequestParam(required = false) int from,
                                                                 @RequestParam(required = false) int to) {
        if (from > 0 && to > 0 && from < to) {
            return ResponseEntity.ok(studentService.findByAgeBetween(from, to));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/faculty")
    public FacultyDTO findFaculty(@PathVariable long id) {
        return studentService.findFaculty(id);
    }

    @GetMapping("/all")
    public Collection<Student> all() {
        return studentService.getAll();
    }

    @GetMapping("/count")
    public int countStudents() {
        return studentService.numberOfStudents();
    }

    @GetMapping("/averageAgeOfStudents")
    public double averageAgeOfStudents() {
        return studentService.averageAgeOfStudents();
    }

    @GetMapping("/getLastFiveStudents")
    public Collection<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }

    @GetMapping("/getNameBegin")
    public Map<String, List<Student>> getNameBegin() {
        return studentService.getNameBegin();
    }

    @GetMapping("/averageAgeOfStudentsStream")
    public double averageAgeOfStudentsStream() {
        return studentService.averageAgeOfStudentsStream();
    }

    @GetMapping("step4")
    public int step4() {
        return studentService.step4();
    }
}
