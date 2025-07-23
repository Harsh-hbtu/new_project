package com.harsh.projectmanagementsystem.modal;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullname;

    private String email;

    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL)
    private List<Issue> assignedIssue = new ArrayList<>();

    private int projectSize;

}
