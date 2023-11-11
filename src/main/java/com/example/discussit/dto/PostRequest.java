package com.example.discussit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {

    private Long postId;
    private String subredditName;
    private String postTitle;
    private String url;
    private String description;
}
