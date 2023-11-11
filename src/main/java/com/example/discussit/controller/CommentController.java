package com.example.discussit.controller;

import com.example.discussit.dto.CommentDto;
import com.example.discussit.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto){
        commentService.createComment(commentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("by-user/{username}")
    public ResponseEntity<List<CommentDto>> getCommentByUser(@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByUser(username));
    }

    @GetMapping("by-post/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentByPost(@PathVariable Long postId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByPost(postId));
    }
}
