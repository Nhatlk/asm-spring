package com.edu.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edu.project.entity.ConsultationCategory;
import com.edu.project.entity.ConsultationPackage;
import com.edu.project.service.ConsultationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller 
@RequestMapping ("/consultation")
public class ConsultationController {
  
  private final ConsultationService consultationService;
  ConsultationController(ConsultationService cs){
    this.consultationService = cs;
  }
  
  @ModelAttribute ("categorie")
  public List<ConsultationCategory> populateCategories(){
    return consultationService.getAllCategories();
  }

  @GetMapping("/list")
  public String list(Model model) {
    
    model.addAttribute("packages", consultationService.getAllPackages());
    model.addAttribute("filterMessage", "Hiển thị toàn bộ gói tư vấn");
    return "consultation/list";
  }

  @GetMapping("/detail/{id}")
  public String detail(@PathVariable("id") Integer id , Model model) {
    
    ConsultationPackage pkg = consultationService.getPackageById(id);
    if(pkg == null){
      return "redirect:/consultation/list";
    }
    model.addAttribute("pkg", pkg);
    model.addAttribute("relatedPackage",
        consultationService.getRelatedPackages(pkg.getCategory().getId(), pkg.getId())
    );
    return "consultation/detail";
  }

  @GetMapping("/search")
  public String search(@RequestParam(value = "keyword", defaultValue = "") String keyword, 
                                    Model model) {
      model.addAttribute("packages",consultationService.searchByKeyword(keyword));
      model.addAttribute("keyword", keyword);
      model.addAttribute("filterMessage", "Kết quả tìm kiếm cho từ khóa: \"" + keyword + "\"");
      return "consultation/list";
  }

  @GetMapping("/search-price")
  public String searchByPrice(@RequestParam("min") Optional<Double> min,
                              @RequestParam("max") Optional<Double> max,
                              Model model) {
      Double minVal = min.orElse(0.0);
      Double maxVal = max.orElse(Double.MAX_VALUE);
      model.addAttribute("packages", consultationService.searchByPriceRange(minVal, maxVal));
      model.addAttribute("min", min.orElse(null));
      model.addAttribute("max", max.orElse(null));
      model.addAttribute("filterMessage", "Lọc gói tư vấn theo khoảng giá");
  
      return "consultation/list";
  }
  
  

}
