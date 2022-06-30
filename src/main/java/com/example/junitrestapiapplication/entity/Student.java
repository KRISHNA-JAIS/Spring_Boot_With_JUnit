package com.example.junitrestapiapplication.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @Id
    @Column(name = "id", nullable = false)
    private Long studentId;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String subject;


}

