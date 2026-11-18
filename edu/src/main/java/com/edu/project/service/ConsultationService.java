package com.edu.project.service;

import com.edu.project.entity.Consultant;
import com.edu.project.entity.ConsultationCategory;
import com.edu.project.entity.ConsultationPackage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultationService {

    private final List<ConsultationCategory> categories = new ArrayList<>();
    private final List<Consultant> consultants = new ArrayList<>();
    private final List<ConsultationPackage> packages = new ArrayList<>();

    public ConsultationService() {
        ConsultationCategory cat1 = new ConsultationCategory("CAT01", "Định hướng nghề nghiệp IT");
        ConsultationCategory cat2 = new ConsultationCategory("CAT02", "Review CV & Phỏng vấn");
        ConsultationCategory cat3 = new ConsultationCategory("CAT03", "Tư vấn lộ trình học tập");
        ConsultationCategory cat4 = new ConsultationCategory("CAT04", "Kỹ năng mềm & Tiếng Anh");
        categories.addAll(List.of(cat1, cat2, cat3, cat4));

        Consultant con1 = new Consultant(1, "ThS. Nguyễn Văn An", "annv@fpt.edu.vn", "Kiến trúc sư Phần mềm / Java Backend", true);
        Consultant con2 = new Consultant(2, "Chuyên gia Trần Thị Bình", "binhtt@fpt.edu.vn", "HR Manager / Tuyển dụng IT", true);
        Consultant con3 = new Consultant(3, "TS. Lê Hoàng Cường", "cuonglh@fpt.edu.vn", "Trí tuệ nhân tạo & Data Science", true);
        Consultant con4 = new Consultant(4, "Cô Phạm Minh Dung", "dungpm@fpt.edu.vn", "Kỹ năng giao tiếp & Phỏng vấn quốc tế", true);
        consultants.addAll(List.of(con1, con2, con3, con4));

        packages.add(new ConsultationPackage(1, "Tư vấn lộ trình Java Spring Boot 1-1", "pkg-java.jpg",
                cat1, con1, 350000.0, 60, true,
                "Đánh giá năng lực hiện tại và xây dựng lộ trình trở thành lập trình viên Java Backend thực chiến."));

        packages.add(new ConsultationPackage(2, "Sửa CV & Phỏng vấn thử vị trí Intern/Fresher", "pkg-cv.jpg",
                cat2, con2, 250000.0, 45, true,
                "Chỉnh sửa chi tiết CV chuẩn ATS và thực hành phỏng vấn thử 1-1 cùng HR Manager."));

        packages.add(new ConsultationPackage(3, "Định hướng chuyên ngành AI & Dữ liệu", "pkg-ai.jpg",
                cat1, con3, 500000.0, 90, true,
                "Giải đáp thắc mắc về cơ hội nghề nghiệp mảng AI, Data Analyst và hướng dẫn chọn đề tài tốt nghiệp."));

        packages.add(new ConsultationPackage(4, "Chiến lược học tốt và vượt qua kỳ Bảo vệ ASM", "pkg-study.jpg",
                cat3, con1, 150000.0, 45, true,
                "Hướng dẫn cách tổ chức source code, đọc hiểu luồng MVC và kỹ năng trả lời câu hỏi bảo vệ."));

        packages.add(new ConsultationPackage(5, "Luyện phỏng vấn Tiếng Anh chuyên ngành IT", "pkg-english.jpg",
                cat4, con4, 400000.0, 60, true,
                "Rèn luyện phản xạ trả lời các câu hỏi tình huống bằng tiếng Anh khi ứng tuyển công ty đa quốc gia."));

        packages.add(new ConsultationPackage(6, "Tư vấn chuyển ngành sang Lập trình Web", "pkg-web.jpg",
                cat3, con2, 300000.0, 60, false,
                "Dành cho sinh viên trái ngành hoặc muốn bắt đầu lại từ gốc với lộ trình cá nhân hóa."));
    }

    public List<ConsultationCategory> getAllCategories() { return categories; }
    public List<Consultant> getAllConsultants() { return consultants; }
    public List<ConsultationPackage> getAllPackages() { return packages; }

    public List<ConsultationPackage> getFeaturedPackages() {
        return packages.stream().filter(ConsultationPackage::getActive).limit(4).collect(Collectors.toList());
    }

    public ConsultationPackage getPackageById(Integer id) {
        return packages.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    public List<ConsultationPackage> getRelatedPackages(String categoryId, Integer excludeId) {
        return packages.stream()
                .filter(p -> p.getCategory().getId().equalsIgnoreCase(categoryId) && !p.getId().equals(excludeId))
                .collect(Collectors.toList());
    }

    public List<ConsultationPackage> getPackagesByCategory(String categoryId) {
        return packages.stream()
                .filter(p -> p.getCategory().getId().equalsIgnoreCase(categoryId))
                .collect(Collectors.toList());
    }

    public List<ConsultationPackage> searchByKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return packages;
        String kw = keyword.trim().toLowerCase();
        return packages.stream()
                .filter(p -> p.getName().toLowerCase().contains(kw) || p.getDescription().toLowerCase().contains(kw))
                .collect(Collectors.toList());
    }

    public List<ConsultationPackage> searchByPriceRange(Double min, Double max) {
        return packages.stream()
                .filter(p -> (min == null || p.getPrice() >= min) && (max == null || p.getPrice() <= max))
                .collect(Collectors.toList());
    }
}