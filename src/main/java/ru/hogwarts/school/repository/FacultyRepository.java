package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty> findByColor(String color);
    Collection<Faculty> findByNameIgnoreCase(String name);

    @Query("select f from Faculty f join Student s on s.faculty.id = f.id where s.id = :studentId")
   Collection<Faculty> findFacultyByStudentId(long studentId);
}
