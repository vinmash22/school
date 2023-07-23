package ru.hogwarts.school.repository;

import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int from, int to);
@Query (value= "SELECT COUNT(*) FROM student", nativeQuery = true)
    int numberOfStudents();
@Query(value = "SELECT AVG(age) from student", nativeQuery = true)
double averageAgeOfStudents();

    @Query(value = "SELECT * from student ORDER BY Id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFiveStudents();

}
