package ru.hogwarts.school.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

@Service
public class FacultyService {
    Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.info("запустился метод добавления факультета");
        logger.debug("добавили факультет: {}", faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        logger.info("запустился метод поиска факультета по id");
        logger.error("ошибка");
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.info("запустился метод редактирования факультета");
        logger.error("ошибка");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.info("запустился метод удаления факультета");
        logger.warn("метод удалит данные");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findByColor(String color) {
        logger.info("запустился метод поиска факультета по цвету");
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> findByName(String name) {
        logger.info("запустился метод поиска факультета по названию");
        return facultyRepository.findByNameIgnoreCase(name);
    }

    public Collection<StudentDTO> findStudentByFaculty(long id) {
        logger.info("запустился метод вывода списка студентов факультета");
        return facultyRepository.findById(id)
                .map(f -> {
                    var studentDtos = new ArrayList<StudentDTO>();
                    for (Student student : f.getStudents()) {
                        var facDto = new FacultyDTO(f.getId(), f.getName(), f.getColor());
                        var dto = new StudentDTO(student.getId(), student.getName(), student.getAge(), facDto);
                        studentDtos.add(dto);
                    }
                    return studentDtos;
                })
                .orElse(new ArrayList<>());
    }

    public Collection<Faculty> getAll() {
        logger.info("запустился метод вывода списка факультетов");
        return facultyRepository.findAll();
    }

    public String getLongestNameFaculty() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElseThrow(() -> new IllegalArgumentException("not found any faculty"));
    }
}


