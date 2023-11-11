package com.example.discussit.service;

import com.example.discussit.dto.CommentDto;
import com.example.discussit.exceptions.PostNotFoundException;
import com.example.discussit.exceptions.SpringDiscussitException;
import com.example.discussit.mapper.CommentMapper;
import com.example.discussit.model.AppUser;
import com.example.discussit.model.NotificationEmail;
import com.example.discussit.model.Post;
import com.example.discussit.repository.CommentRepository;
import com.example.discussit.repository.PostRepository;
import com.example.discussit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final AuthService authService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    private static final String POST_URL = "";

    public void createComment(CommentDto commentDto){
        Post post = postRepository.findById(commentDto
                .getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post with ID number: " +
                        commentDto.getPostId().toString() + " not found."));
        commentRepository.save(commentMapper.map(commentDto, post, authService.getCurrentUser()));
        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    public List<CommentDto> getCommentsByPost(Long postId){
        Post post = postRepository
                .findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post with ID number: " +
                        postId.toString() + " not found."));
        return commentRepository.findByPost(post).stream().map(commentMapper::mapToDto).collect(toList());
    }

    public List<CommentDto> getCommentsByUser(String username){
        AppUser user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new SpringDiscussitException("Username" + username + "not found"));
        return commentRepository
                .findAllByUser(user)
                .stream().map(commentMapper::mapToDto)
                .collect(toList());
    }

    private void sendCommentNotification(String message, AppUser user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() +
                " Commented on your post", user.getEmail(), message));
    }
}
