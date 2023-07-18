package ru.hogwarts.school.service;

import java.util.ArrayList;
import java.util.Collection;


import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findByColor(String color) {
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> findByName(String name) {
        return facultyRepository.findByNameIgnoreCase(name);
    }

    public Collection<StudentDTO> findStudentByFaculty(long id) {
        return facultyRepository.findById(id)
                .map(f -> {
                    var studentDtos = new ArrayList<StudentDTO>();
                    for (Student student : f.getStudents()) {
                        var facDto = new FacultyDTO(student.getFaculty().getId(), student.getFaculty().getName(), student.getFaculty().getColor());
                        var dto = new StudentDTO(student.getId(), student.getName(), student.getAge(), facDto);
                        studentDtos.add(dto);
                    }
                    return studentDtos;
                })
                .orElse(new ArrayList<>());
    }

    public Collection<Faculty> getAll() {
        return facultyRepository.findAll();
    }
}

