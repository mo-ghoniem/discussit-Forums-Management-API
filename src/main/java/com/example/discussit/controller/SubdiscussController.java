package com.example.discussit.controller;

import com.example.discussit.dto.SubredditDto;
import com.example.discussit.model.Subreddit;
import com.example.discussit.service.SubdiscussionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/subdiscussions")
@AllArgsConstructor
public class SubdiscussController {

    private final SubdiscussionService subdiscussionService;

    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody @Valid SubredditDto subredditDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(subdiscussionService.save(subredditDto));
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubreddits(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subdiscussionService
                        .getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getAllSubreddit(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subdiscussionService
                        .getSubreddit(id));
    }
}
