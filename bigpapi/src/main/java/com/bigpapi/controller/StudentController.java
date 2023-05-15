package com.bigpapi.controller;

import com.bigpapi.model.StudentDetails;
import com.bigpapi.service.StudentService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class StudentController {

  private StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping
  public String fetchAllstudents(Model model) {
    List<StudentDetails> students = studentService.fetchAllStudents();
    model.addAttribute("students", students);
    return "students/index";
  }

  @GetMapping("/create")
  public String createStudentForm(Model model) {
    model.addAttribute("student", new StudentDetails());
    return "dashboard";
  }

  @PostMapping("/createStudent")
  public String createStudent(@ModelAttribute("student") StudentDetails student,
      BindingResult result, HttpSession session, Model model) {

    StudentDetails existingStudent = studentService.findByEmail(student.getEmail());

    if (StringUtils.isEmpty(student.getFirstName())) {
      session.setAttribute("msg", "First name cannot be empty");
    }

    if (StringUtils.isEmpty(student.getLastName())) {
      session.setAttribute("msg", "Last name cannot be empty");
    }

    if (StringUtils.isEmpty(student.getEmail())) {
      session.setAttribute("msg", "Email cannot be empty");
    }

    if (StringUtils.isEmpty(student.getPassword())) {
      session.setAttribute("msg", "Password cannot be empty");
    }

    if (existingStudent != null) {
      session.setAttribute("msg", "Email already exists");
    }

    if (session.getAttribute("msg") != null) {
      model.addAttribute("student", student);
      return "register";

    } else {
      StudentDetails studentDetails = studentService.createStudent(student);
      if (studentDetails != null) {
        session.setAttribute("msg", "Register Successfully");
      } else {
        session.setAttribute("msg", "Something went wrong on server");
      }
    }
    model.addAttribute("student", new StudentDetails());
    return "redirect:/register";
  }

  @GetMapping("/{id}")
  public String fetchStudentById(@PathVariable Integer id, Model model) {
    StudentDetails student = studentService.findById(id);
    if (student != null) {
      model.addAttribute("student", student);
      return "students/details";
    } else {
      return "redirect:/students";
    }
  }

  @GetMapping("/viewStudentProfile")
  public String editStudentForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
    if (userDetails == null || StringUtils.isEmpty(userDetails.getUsername())) {
      return "login";
    }
    String username = userDetails.getUsername();
    StudentDetails student = studentService.findByEmail(username);
    if (student != null) {
      model.addAttribute("student", student);
      return "studentprofile";
    }
    return "login";

  }

  @PostMapping("/editStudentProfile/{id}")
  public String editStudentProfile(Model model, @PathVariable Integer id) {
    StudentDetails student = studentService.findById(id);
    if (student != null) {
      model.addAttribute("student", student);
      return "updatestudentprofile";
    }
    return "login";

  }

  @PostMapping("/updateStudent/{id}")
  public String editStudent(@PathVariable Integer id,
      @ModelAttribute("student") StudentDetails student, Model model, HttpSession session) {

    student.setId(id);
    if (StringUtils.isEmpty(student.getFirstName())) {
      session.setAttribute("msg", "First name cannot be empty");
    }

    if (StringUtils.isEmpty(student.getLastName())) {
      session.setAttribute("msg", "Last name cannot be empty");
    }

    if (session.getAttribute("msg") != null) {
      model.addAttribute("student", student);
      return "updatestudentprofile";

    }
    StudentDetails updatedStudent = studentService.updateStudent(student);
    model.addAttribute("student", updatedStudent);
    return "studentprofile";
  }

  @PostMapping("/{id}/delete")
  public String deleteStudent(@PathVariable Integer id) {
    studentService.deleteStudent(id);
    return "redirect:/students";
  }

}
