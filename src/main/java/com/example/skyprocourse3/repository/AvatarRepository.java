package com.example.skyprocourse3.repository;

import com.example.skyprocourse3.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Avatar getAvatarByStudentId(Long student_id);
}
