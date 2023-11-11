package com.example.discussit.repository;

import com.example.discussit.model.Post;
import com.example.discussit.model.AppUser;
import com.example.discussit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, AppUser currentUser);
}
