package com.example.skyprocourse3.controller;

import com.example.skyprocourse3.model.Avatar;
import com.example.skyprocourse3.model.Student;
import com.example.skyprocourse3.service.AvatarService;
import com.example.skyprocourse3.service.StudentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@RestController
@RequestMapping("/api/v1/avatars")
public class AvatarController {

    private final AvatarService avatarService;
    private final StudentService studentService;

    public AvatarController(AvatarService avatarService, StudentService studentService) {
        this.avatarService = avatarService;
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Collection<Avatar>> getAllAvatars(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        return ResponseEntity.ok(avatarService.getAllAvatars(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Avatar> getAvatarById(@PathVariable Long id) {
        Avatar avatar = avatarService.getAvatarById(id);
        if (avatar == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(avatar);
    }

    @GetMapping("/{id}/download")
    public void downloadAvatarById(@PathVariable Long id, HttpServletResponse response) throws IOException {

        Avatar avatar = avatarService.getAvatarById(id);
        if (avatar == null) {
            response.sendError(404, "Avatar not found");
            return;
        }
        Path path = Path.of(avatar.getFilePath());

        try (
                InputStream inputStream = Files.newInputStream(path);
                OutputStream outputStream = response.getOutputStream();
                ) {
            response.setContentType(avatar.getMediaType());
            response.setContentLength(avatar.getData().length);
            response.setStatus(200);
            inputStream.transferTo(outputStream);
        } catch (IOException e) {
            response.sendError(500, "Internal server error");
        }
    }

    @PostMapping(value = "/students/{student_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createAvatar(@PathVariable Long student_id, @RequestParam MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        if (file.getSize() > 1024 * 1024) {
            return ResponseEntity.badRequest().body("File is too large");
        }

        Student student = studentService.getStudent(student_id);
        if (student == null) {
            return ResponseEntity.status(404).body("Student not found");
        }
        return ResponseEntity.ok(avatarService.createAvatar(file, student));
    }
}
