package com.harsh.projectmanagementsystem.modal;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String token;
    private String email ;

    private Long projectId;
 
}
