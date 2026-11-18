package com.edu.project.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingForm {

    @NotBlank(message = "Họ tên người đặt không được để trống")
    private String fullName;

    @NotBlank(message = "Email liên hệ không được để trống")
    @Email(message = "Định dạng Email không hợp lệ (VD: sinhvien@fpt.edu.vn)")
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^0\\d{9,10}$", message = "Số điện thoại phải bắt đầu bằng số 0 và gồm 10-11 chữ số")
    private String phone;

    @NotNull(message = "Vui lòng chọn Gói tư vấn")
    private Integer consultationPackageId;

    @NotNull(message = "Vui lòng chọn Chuyên viên phụ trách")
    private Integer consultantId;

    @NotNull(message = "Vui lòng chọn Ngày tư vấn mong muốn")
    @FutureOrPresent(message = "Ngày tư vấn không được chọn ngày trong quá khứ")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate preferredDate;

    @NotBlank(message = "Vui lòng chọn Khung giờ tư vấn")
    private String preferredTime;

    @Size(max = 255, message = "Ghi chú không được vượt quá 255 ký tự")
    private String note;

    // File đính kèm (CV / bảng điểm minh chứng - Không bắt buộc)
    private MultipartFile attachment;
}