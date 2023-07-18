package ru.hogwarts.school;

import net.minidev.json.JSONObject;
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
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Optional;

import static org.hamcrest.core.IsInstanceOf.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;
   @MockBean
    private FacultyService facultyService;


    @Test
    public void createFaculty() throws Exception {

        Long facultyId = 1L;
        String facultyName = "testFaculty";
        String facultyColor = "testColor";
        JSONObject facultyObject = new JSONObject();


        facultyObject.put("id", facultyId);
        facultyObject.put("name", facultyName);
        facultyObject.put("color", facultyColor);

        Faculty faculty = new Faculty();
        faculty.setId(facultyId);
        faculty.setName(facultyName);
        faculty.setColor(facultyColor);


        when(facultyService.addFaculty(faculty)).thenReturn(faculty);
        when(facultyService.findFaculty(faculty.getId())).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty") //send
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())//receive
                .andExpect(jsonPath("$.name").value(facultyName))
               .andExpect(jsonPath("$.color").value(facultyColor))
               .andExpect(jsonPath("$.id").value(facultyId));
    }
}
