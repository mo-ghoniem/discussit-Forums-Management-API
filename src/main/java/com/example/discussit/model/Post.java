package com.example.discussit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postID_generator")
    @SequenceGenerator(name="postID_generator", sequenceName = "post_id_seq")
    private Long postId;
    @NotBlank(message = "Post Name cannot be empty or Null")
    private String postTitle;
    @Nullable
    @Lob
    private String description;
    @Nullable
    private String url;
    private Integer voteCount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private AppUser user;
    private Instant dateCreated;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Subreddit subreddit;

}
