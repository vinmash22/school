package ru.hogwarts.school;

import net.minidev.json.JSONObject;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;

import java.util.*;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;
import static org.hamcrest.core.IsInstanceOf.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    FacultyRepository facultyRepository;
    @MockBean
    StudentRepository studentRepository;
    @SpyBean
    private StudentService studentService;
    @SpyBean
    private FacultyService facultyService;

    Long facultyId = 1L;
    String facultyName = "testFaculty";
    String facultyColor = "testColor";
    JSONObject facultyObject = new JSONObject();
    Faculty faculty = new Faculty();

    @BeforeEach
    public void init() throws Exception {
        facultyObject.put("id", facultyId);
        facultyObject.put("name", facultyName);
        facultyObject.put("color", facultyColor);

        faculty.setId(facultyId);
        faculty.setName(facultyName);
        faculty.setColor(facultyColor);
    }

    @Test
    public void saveFacultyTest() throws Exception {
        when(facultyService.addFaculty(faculty)).thenReturn(faculty);
        when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty") //send
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())//receive
                .andExpect(jsonPath("$.name").value(facultyName))
                .andExpect(jsonPath("$.color").value(facultyColor))
                .andExpect(jsonPath("$.id").value(facultyId));


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/1") //send
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())//receive
                .andExpect(jsonPath("$.name").value(facultyName))
                .andExpect(jsonPath("$.color").value(facultyColor))
                .andExpect(jsonPath("$.id").value(facultyId));


        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty") //send
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())//receive
                .andExpect(jsonPath("$.name").value(facultyName))
                .andExpect(jsonPath("$.color").value(facultyColor))
                .andExpect(jsonPath("$.id").value(facultyId));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/1") //send
                        .content(facultyObject.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());//receive
    }

    @Test
    void getFacultyByColorOrName() throws Exception {
        Collection<Faculty> faculties = new ArrayList<>(
                Arrays.asList(
                        new Faculty(1L, "bio", "green"),
                        new Faculty(2L, "it", "white"),
                        new Faculty(3L, "math", "red")
                )
        );

        when(facultyRepository.findByColor(faculty.getColor())).thenReturn(faculties);
        when(facultyRepository.findByNameIgnoreCase(faculty.getColor())).thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty?name=" + facultyName + "&color=" + facultyColor))
                .andExpect(status().isOk());
    }

    @Test
    void getAll() throws Exception {
        Collection<Faculty> faculties = new ArrayList<>(
                Arrays.asList(
                        new Faculty(1L, "bio", "green"),
                        new Faculty(2L, "it", "white"),
                        new Faculty(3L, "math", "red")
                )
        );

        when(facultyService.getAll()).thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(faculties.size()));
    }

    @Test
    void getStudents() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(facultyId);
        faculty.setName(facultyName);
        faculty.setColor(facultyColor);
        faculty.setStudents(List.of(new Student(1L, "1", 24)));
        when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/" + faculty.getId() + "/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("1"))
                .andExpect(jsonPath("$[0].age").value(24))
                .andExpect(jsonPath("$[0].id").value(1L));
    }
}