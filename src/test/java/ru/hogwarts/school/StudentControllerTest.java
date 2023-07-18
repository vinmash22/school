package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @AfterEach
   public void clearRepository() {
        studentRepository.deleteAll();
   }

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testGetStudentInfo() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + id, String.class))
                .isNotNull();
    }

    @Test
    public void testPostCreateStudent() throws Exception{
        Student student = new Student();
        student.setName("asdff");
        student.setAge(24);

        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student, String.class))
                .isNotNull();
    }

     @Test
    void findStudentsTest() {
        Student student1 = new Student();
        student1.setName("asdff");
        student1.setAge(24);
               Collection<Student> studentsExpected = new ArrayList<>();

        int age = 24;
               studentsExpected.add(restTemplate.postForObject("http://localhost:" + port + "/student", student1, Student.class));
               ResponseEntity<List<Student>> studentsActual = restTemplate.exchange(
                "http://localhost:" + port + "/student?age=" + age,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        assertEquals(studentsExpected, studentsActual.getBody());
    }

    @Test
    void getStudentFacultyTest() {
        Student student1 = new Student();
        student1.setName("asdff");
        student1.setAge(24);
        Faculty faculty = new Faculty();
        faculty.setName("testFaculty");
        faculty.setColor("testColor");
        Faculty facultyOut = restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        student1.setFaculty(facultyOut);
        Student studentOut = restTemplate.postForObject("http://localhost:" + port + "/student", student1, Student.class);
        Faculty facultyActual = restTemplate.getForObject("http://localhost:" + port + "/student/" + studentOut.getId() + "/faculty", Faculty.class);
        assertEquals(student1.getFaculty(), facultyActual);
    }

}

