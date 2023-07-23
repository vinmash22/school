package ru.hogwarts.school.dto;

public class StudentDTO {
    private long id;
    private String name;
    private int age;
    private FacultyDTO faculty;

    public StudentDTO(long id, String name, int age, FacultyDTO faculty) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.faculty = faculty;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFaculty(FacultyDTO faculty) {
        this.faculty = faculty;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public FacultyDTO getFaculty() {
        return faculty;
    }
}
