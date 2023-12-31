package ru.hogwarts.school.service;

import static java.nio.file.StandardOpenOption.CREATE_NEW;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class AvatarService {

    Logger logger = LoggerFactory.getLogger(AvatarService.class);
    @Value("${avatars.dir.path}")
    private String avatarsDir;
    //private final StudentService studentService;
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    public AvatarService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    public void uploadAvatar(long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Запустился метод загрузки аватара");
        logger.error("Ошибка");
        Student student = studentRepository.getById(studentId);
        Path filePath = Path.of(avatarsDir, studentId + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        logger.info("Сохраняем аватар");
        avatarRepository.save(avatar);
    }

    public Avatar findAvatar(long studentId) {
        logger.info("Находим аватар по id студента");
        logger.warn("аватара нет, нужно добавить");
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    private String getExtensions(String fileName) {
        logger.info("запустился метод вывода  файла");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public List<Avatar> downloadAvatars(int pageNumber, int pageSize){
        logger.info("Получаем все аватары постранично");
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }

}

