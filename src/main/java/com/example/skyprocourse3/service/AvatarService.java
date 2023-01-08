package com.example.skyprocourse3.service;

import com.example.skyprocourse3.model.Avatar;
import com.example.skyprocourse3.model.Student;
import com.example.skyprocourse3.repository.AvatarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarService {

    Logger logger = LoggerFactory.getLogger(AvatarService.class);

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    public Avatar createAvatar(MultipartFile avatar, Student student) throws IOException {
        logger.info("Was invoked method for create student");
        Path path = Path.of("src/main/resources/avatars/", student.getId() + "." +
                Objects.requireNonNull(avatar.getContentType()).split("/")[1]);
        Files.createDirectories(path.getParent());
        Files.deleteIfExists(path);

        try(InputStream inputStream = avatar.getInputStream();
            OutputStream outputStream = Files.newOutputStream(path, CREATE_NEW);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            OutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
            bufferedInputStream.transferTo(bufferedOutputStream);
        } catch (IOException e) {
            logger.error("Error while creating avatar", e);
            throw new IOException("Error while creating avatar", e);
        }
        Avatar newAvatar = createAvatarBody(avatar, student, path);
        return avatarRepository.save(newAvatar);
    }

    public Avatar getAvatarById(Long id) {
        logger.info("Was invoked method for get avatar by id");
        return avatarRepository.findById(id).orElse(null);
    }

    public Collection<Avatar> getAllAvatars(Integer page, Integer size) {
        logger.info("Was invoked method for get all avatars");
        if (page == null || size == null) {
            return avatarRepository.findAll();
        }
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return avatarRepository.findAll(pageRequest).getContent();
    }

    public Avatar getAvatarByStudentId(Long student_id) {
        logger.info("Was invoked method for get avatar by student id");
        return avatarRepository.getAvatarByStudentId(student_id);
    }

    public Avatar updateAvatar(Avatar avatarToUpdate, Avatar updateBody) {
        logger.info("Was invoked method for update avatar");
        avatarToUpdate.setData(updateBody.getData());
        avatarToUpdate.setMediaType(updateBody.getMediaType());
        avatarToUpdate.setFileSize(updateBody.getFileSize());
        return avatarRepository.save(avatarToUpdate);
    }

    public void deleteAvatar(Long id) {
        logger.info("Was invoked method for delete avatar");
        avatarRepository.deleteById(id);
    }

    public Resource loadAsResource(Avatar avatar) {
        logger.info("Was invoked method for load avatar as resource");
        String filename = avatar.getStudent().getId() + "." + avatar.getMediaType().split("/")[1];
        try {
            Path rootLocation = Path.of("src/main/resources/avatars/");

            Path file = rootLocation.resolve(filename).normalize();

            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                logger.error("file with name: " + filename + " not found", new FileNotFoundException());
                throw new FileNotFoundException("file with name: " + filename + " not found");
            }
        }
        catch (MalformedURLException | FileNotFoundException e) {
            logger.error("Could not read file: " + filename, e);
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    private Avatar createAvatarBody(MultipartFile avatar, Student student, Path path) throws IOException {
        Avatar avatarBody = new Avatar();
        avatarBody.setStudent(student);
        avatarBody.setData(avatar.getBytes());
        avatarBody.setMediaType(avatar.getContentType());
        avatarBody.setFileSize(avatar.getSize());
        avatarBody.setFilePath(path.toString());
        return avatarBody;
    }
}
