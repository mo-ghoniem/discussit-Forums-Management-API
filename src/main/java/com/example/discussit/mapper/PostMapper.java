package com.example.discussit.mapper;

import com.example.discussit.dto.PostRequest;
import com.example.discussit.dto.PostResponse;
import com.example.discussit.model.AppUser;
import com.example.discussit.model.Post;
import com.example.discussit.model.Subreddit;
import com.example.discussit.repository.CommentRepository;
import com.example.discussit.repository.VoteRepository;
import com.example.discussit.service.AuthService;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private AuthService authService;

    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "dateCreated", expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", source = "appUser")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "postTitle", source = "postRequest.postTitle")
    @Mapping(target = "voteCount", constant = "0")
    public abstract Post mapToPost(PostRequest postRequest, Subreddit subreddit, AppUser appUser);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "postTitle", source = "postTitle")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "url", source = "url")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
   public abstract PostResponse mapToDto(Post post);


    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getDateCreated().toEpochMilli());
    }
}
