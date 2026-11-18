package com.edu.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRecord {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private ConsultationPackage consultationPackage;
    private Consultant consultant;
    private LocalDate preferredDate;
    private String preferredTime;
    private String note;
    private String attachmentFileName;
    private String status;      // NEW / CONFIRMED / CANCELLED
    private Date createdAt;
}