package com.edu.project.controller;

import com.edu.project.entity.BookingForm;
import com.edu.project.entity.Consultant;
import com.edu.project.entity.ConsultationPackage;
import com.edu.project.service.BookingService;
import com.edu.project.service.ConsultationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private ConsultationService consultationService;
    private BookingService bookingService;

    BookingController(ConsultationService cs , BookingService bs){
        this.consultationService = cs;
        this.bookingService = bs;
    }

    // Cung cấp sẵn danh sách Gói tư vấn, Chuyên viên, Khung giờ cho thẻ <select> trong Form
    @ModelAttribute("packages")
    public List<ConsultationPackage> populatePackages() {
        return consultationService.getAllPackages();
    }

    @ModelAttribute("consultants")
    public List<Consultant> populateConsultants() {
        return consultationService.getAllConsultants();
    }

    @ModelAttribute("timeSlots")
    public List<String> populateTimeSlots() {
        return List.of(
                "08:00 - 09:00",
                "09:30 - 10:30",
                "14:00 - 15:00",
                "15:30 - 16:30",
                "19:30 - 20:30 (Online)"
        );
    }

    // 1. Mở form đặt lịch (GET /booking/form)
    @GetMapping("/form")
    public String showForm(@RequestParam(value = "packageId", required = false) Integer packageId,
                        Model model) {
        BookingForm form = new BookingForm();
        // Nếu người dùng bấm "Đặt lịch" từ 1 gói cụ thể, tự động chọn sẵn gói và chuyên viên đó
        if (packageId != null) {
            form.setConsultationPackageId(packageId);
            ConsultationPackage pkg = consultationService.getPackageById(packageId);
            if (pkg != null && pkg.getConsultant() != null) {
                form.setConsultantId(pkg.getConsultant().getId());
            }
        }
        model.addAttribute("bookingForm", form);
        return "booking/form";
    }

    // 2. Xử lý gửi form đặt lịch + Validation + Upload file (POST /booking/save)
    @PostMapping("/save")
    public String saveBooking(@Valid @ModelAttribute("bookingForm") BookingForm bookingForm,
                            BindingResult result,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        // Nếu có lỗi Validation -> Giữ nguyên dữ liệu đã nhập và trả lại trang form
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Thông tin đặt lịch chưa hợp lệ, vui lòng kiểm tra các mục báo đỏ!");
            return "booking/form";
        }

        // Nếu hợp lệ -> Lưu lịch đặt và redirect sang /booking/list
        bookingService.createBooking(bookingForm);
        redirectAttributes.addFlashAttribute("successMessage",
                "Đặt lịch tư vấn thành công cho sinh viên: " + bookingForm.getFullName() + "!");
        return "redirect:/booking/list";
    }

    // 3. Hiển thị danh sách lịch đã đặt sau khi redirect (GET /booking/list)
    @GetMapping("/list")
    public String listBookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "booking/list";
    }
}