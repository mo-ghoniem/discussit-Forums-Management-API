package com.example.discussit.service;

import com.example.discussit.dto.VoteDto;
import com.example.discussit.exceptions.PostNotFoundException;
import com.example.discussit.exceptions.SpringDiscussitException;
import com.example.discussit.model.Post;
import com.example.discussit.model.Vote;
import com.example.discussit.repository.PostRepository;
import com.example.discussit.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.discussit.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final AuthService authService;
    private final PostRepository postRepository;

    @Transactional
    public void vote(VoteDto voteDto){
        Post post = postRepository
                .findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository
                .findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());

        if(voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())){
            throw new SpringDiscussitException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
        }

        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }

        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);

    }

    private Vote mapToVote(VoteDto voteDto, Post post){
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post).user(authService.getCurrentUser())
                .build();
    }
}
