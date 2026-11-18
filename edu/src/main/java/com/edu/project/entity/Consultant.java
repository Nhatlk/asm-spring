package com.edu.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consultant {
    private Integer id;
    private String fullName;
    private String email;
    private String specialty;
    private Boolean active;
}