package com.edu.project.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.edu.project.service.ConsultationService;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller 
public class HomeController {
  private final ConsultationService consultationService;

  HomeController(ConsultationService consultationService) {
    this.consultationService = consultationService;
  }

  @GetMapping("/")
  public String root() {
      return "redirect:/home/index";
  }

  @GetMapping("/home/index")
  public String index(Model model) {
    model.addAttribute("systemName", "CarrerPath Booking Portal");
    model.addAttribute("shortDesc", "Nền tảng kết nối sinh viên FPT Polytechnic với đội ngũ cố vấn học tập và chuyên gia định hướng nghề nghiệp 1-1.");
    model.addAttribute("featurePackage", consultationService.getFeaturedPackages());
    model.addAttribute("consultants", consultationService.getAllConsultants());
    model.addAttribute("totalPackages", consultationService.getAllPackages().size());
    model.addAttribute("totalBookings", 120); // number mau~
    
    return "home/index";
  }
  @GetMapping("/home/about")
    public String about(Model model) {
        model.addAttribute("aboutTitle", "Giới thiệu về CareerPath Booking Portal");
        model.addAttribute("mission", "Giúp sinh viên định hình rõ lộ trình nghề nghiệp, chuẩn bị CV chuẩn chỉnh và tự tin chinh phục nhà tuyển dụng.");
        return "home/about";
    }

    
  @GetMapping("/home/help")
  public String help(Model model) {
    model.addAttribute("aboutTitle", "Giới thiệu về CarrerPath Booking Portal");
    model.addAttribute("supportEmail", "support.carrerpath@fpt.edu.vn");
      return "home/help";
  }

  @GetMapping("/system/status")
  @ResponseBody 
  public Map<String, Object>  systemStatus() {
      return Map.of(
        "system", "CarrerPath Booking Portal",
        "status", "ONLINE",
        "totalPackages", consultationService.getAllPackages().size(),
        "totalConsultants", consultationService.getAllConsultants().size()
      );
  }
}
