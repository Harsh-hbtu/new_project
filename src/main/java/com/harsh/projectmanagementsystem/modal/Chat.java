package com.harsh.projectmanagementsystem.modal;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

// import jakarta.persistence.CascadeType;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.OneToOne;

// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;




@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @OneToOne
    private Project project;
    

    @JsonIgnore
    @OneToMany(mappedBy = "chat" , cascade = CascadeType.ALL, orphanRemoval =true)
    private List<Message> messages  = new ArrayList<>();

    @ManyToMany
    private List<User> users = new ArrayList<>();

}
