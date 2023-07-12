package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.repository.AvatarRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class AvatarService {
    @Value("${students.avatar.dir.path}")
    private String avatarsDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }
}
