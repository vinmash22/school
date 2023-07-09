package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int from, int to);

    @Query("select s from Student s join Faculty f on s.faculty.id = f.id where f.id = :facultyId")
    Collection<Student> getStudentsByFacultyID(long facultyId);
}
