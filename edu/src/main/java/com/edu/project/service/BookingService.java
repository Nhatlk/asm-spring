package com.edu.project.service;

import com.edu.project.entity.BookingForm;
import com.edu.project.entity.BookingRecord;
import com.edu.project.entity.Consultant;
import com.edu.project.entity.ConsultationPackage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookingService {

    private final List<BookingRecord> bookings = new ArrayList<>();
    private long nextId = 1;

    private ConsultationService consultationService;
    BookingService(ConsultationService cs){
      this.consultationService = cs;
    }

    // Tạo sẵn 1 lịch mẫu để trang /booking/list có sẵn dữ liệu khi mới vào
    public List<BookingRecord> getAllBookings() {
        if (bookings.isEmpty()) {
            ConsultationPackage samplePkg = consultationService.getPackageById(1);
            Consultant sampleCon = consultationService.getAllConsultants().get(0);
            bookings.add(new BookingRecord(
                    nextId++,
                    "Nguyễn Minh Nhật",
                    "nhatnm@fpt.edu.vn",
                    "0901234567",
                    samplePkg,
                    sampleCon,
                    LocalDate.now().plusDays(3),
                    "09:00 - 10:00",
                    "Em muốn nhờ thầy định hướng lộ trình làm dự án Spring Boot MVC.",
                    "CV_NguyenMinhNhat.pdf",
                    "CONFIRMED",
                    new Date()
            ));
        }
        return bookings;
    }

    public BookingRecord createBooking(BookingForm form) {
        // 1. Xử lý lưu file đính kèm (nếu người dùng có chọn file)
        String savedFileName = null;
        MultipartFile file = form.getAttachment();
        if (file != null && !file.isEmpty()) {
            try {
                Path uploadDir = Paths.get("uploads");
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }
                savedFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Files.copy(file.getInputStream(), uploadDir.resolve(savedFileName), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                savedFileName = file.getOriginalFilename();
            }
        }

        // 2. Tra cứu đối tượng Gói tư vấn và Chuyên viên
        ConsultationPackage pkg = consultationService.getPackageById(form.getConsultationPackageId());
        Consultant con = consultationService.getAllConsultants().stream()
                .filter(c -> c.getId().equals(form.getConsultantId()))
                .findFirst()
                .orElse(pkg != null ? pkg.getConsultant() : null);

        // 3. Tạo bản ghi lịch đặt mới và thêm lên đầu danh sách
        BookingRecord record = new BookingRecord(
                nextId++,
                form.getFullName(),
                form.getEmail(),
                form.getPhone(),
                pkg,
                con,
                form.getPreferredDate(),
                form.getPreferredTime(),
                form.getNote(),
                savedFileName,
                "NEW",
                new Date()
        );
        getAllBookings(); // Đảm bảo dữ liệu mẫu đã khởi tạo
        bookings.add(0, record);
        return record;
    }
}