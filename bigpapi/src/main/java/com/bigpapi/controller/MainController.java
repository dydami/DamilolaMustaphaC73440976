package com.bigpapi.controller;

import com.bigpapi.model.StudentDetails;
import com.bigpapi.service.StudentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

  private final StudentService studentService;

  public MainController(StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/register")
  public String register(Model model) {
    model.addAttribute("student", new StudentDetails());
    return "register";
  }


  @GetMapping("/dashboard")
  public String showDashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {

    String username = userDetails.getUsername();
    StudentDetails studentDetails = studentService.findByEmail(username);
    // Add any necessary data to the model
    model.addAttribute("username", studentDetails.getFirstName());
    return "dashboard";
  }

}

