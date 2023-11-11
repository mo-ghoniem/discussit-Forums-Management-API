package com.example.discussit.service;

import com.example.discussit.dto.SubredditDto;
import com.example.discussit.exceptions.SpringDiscussitException;
import com.example.discussit.mapper.SubredditMapper;
import com.example.discussit.model.Subreddit;
import com.example.discussit.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.time.Instant.now;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class SubdiscussionService {

    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit subreddit = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(subreddit.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll(){
        return subredditRepository
                .findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public SubredditDto getSubreddit(Long id){
        Subreddit subreddit = subredditRepository
                .findById(id)
                .orElseThrow(() -> new SpringDiscussitException("Subreddit not found with id -" + id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }

}
