package com.example.discussit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userID_generator")
    @SequenceGenerator(name="userID_generator", sequenceName = "user_id_seq")
     private Long userId;
    @NotBlank(message = "Username is required")
     private String username;
    @NotBlank(message = "password is required")
     private String password;
    @Email
    @NotBlank(message = "Email is required")
     private String email;
     private Instant created;
     private boolean enabled;

}
