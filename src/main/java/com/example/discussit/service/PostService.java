package com.example.discussit.service;

import com.example.discussit.dto.PostRequest;
import com.example.discussit.dto.PostResponse;
import com.example.discussit.exceptions.SpringDiscussitException;
import com.example.discussit.mapper.PostMapper;
import com.example.discussit.model.AppUser;
import com.example.discussit.model.Post;
import com.example.discussit.model.Subreddit;
import com.example.discussit.repository.PostRepository;
import com.example.discussit.repository.SubredditRepository;
import com.example.discussit.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public void savePost(PostRequest postRequest){
        Subreddit subreddit = subredditRepository
                .findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SpringDiscussitException("Subreddit with name " + postRequest.getSubredditName()
                        + " not found."));
        Post post = postMapper.mapToPost(postRequest, subreddit, authService.getCurrentUser());
        postRepository.save(post);
        System.out.println(postRequest.getPostTitle());
        System.out.println(post.getPostTitle());

    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id){
        Post post = postRepository
                .findById(id)
                .orElseThrow(() -> new SpringDiscussitException("Post with ID number " + id + " not found."));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId){
        Subreddit subreddit = subredditRepository
                .findById(subredditId)
                .orElseThrow(() -> new SpringDiscussitException("Subreddit with ID number " + subredditId
                        + " not found."));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username){
        AppUser user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new SpringDiscussitException("User with name  " + username + " not found."));
        List<Post> posts = postRepository.findByUser(user);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(){
        return postRepository
                .findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}
