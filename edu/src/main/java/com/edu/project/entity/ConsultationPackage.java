package com.edu.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationPackage {
    private Integer id;
    private String name;
    private String image;
    private ConsultationCategory category;
    private Consultant consultant;
    private Double price;
    private Integer duration;
    private Boolean active;
    private String description;
}