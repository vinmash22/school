SELECT s.name, s.age, f.name FROM student s INNER JOIN faculty f ON s.faculty_id = f.id;
SELECT s.name FROM student s INNER JOIN avatar a on s.id = a.student_id