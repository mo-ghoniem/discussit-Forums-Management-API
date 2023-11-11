package com.example.discussit.repository;

import com.example.discussit.dto.CommentDto;
import com.example.discussit.model.Comment;
import com.example.discussit.model.Post;
import com.example.discussit.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(AppUser user);

}
