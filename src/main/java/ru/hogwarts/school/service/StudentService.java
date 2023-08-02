package ru.hogwarts.school.service;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

@Service
public class StudentService {
    Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        logger.info("Запустился метод по добавлению студента");
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        logger.info("Запустился метод поиска студента по id");
        logger.error("Нет студента с таким id = " + id);
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        logger.info("запустился метод редактирования студента");
        logger.warn("использование метода изменит данные студента");
        logger.debug("редактируем студента:{}", student);
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        logger.info("запустился метод удаления студента");
        studentRepository.deleteById(id);
    }

    public Collection<Student> findByAge(int age) {
        logger.info("запустился метод поиска студентов по возрасту");
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(int from, int to) {
        logger.info("запустился метод поиска студентов определенного возраста");
        return studentRepository.findByAgeBetween(from, to);
    }

    public Collection<Student> getAll() {
        logger.info("запустился метод вывода списка студентов");
        return studentRepository.findAll();
    }

    public FacultyDTO findFaculty(long id) {
        logger.info("запустился метод поиска факультета студента по его id");
        return studentRepository.findById(id).map(student -> {
            FacultyDTO dto = new FacultyDTO();
            dto.setId(student.getFaculty().getId());
            dto.setName(student.getFaculty().getName());
            dto.setColor(student.getFaculty().getColor());
            return dto;
        }).orElse(null);
    }

    public int numberOfStudents() {
        logger.info("запустился метод подсчета студентов");
        return studentRepository.numberOfStudents();
    }

    public double averageAgeOfStudents() {
        logger.info("запустился метод вывода среднего возраста студентов");
        return studentRepository.averageAgeOfStudents();
    }

    public List<Student> getLastFiveStudents() {
        logger.info("запустился метод вывода пяти последних студентов");
        return studentRepository.getLastFiveStudents();
    }

}
