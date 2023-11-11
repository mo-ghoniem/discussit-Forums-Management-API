package com.example.discussit.repository;

import com.example.discussit.model.Post;
import com.example.discussit.model.Subreddit;
import com.example.discussit.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);
    List<Post> findByUser(AppUser user);
}
