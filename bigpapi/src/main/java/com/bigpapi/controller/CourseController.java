package com.bigpapi.controller;

import com.bigpapi.model.Course;
import com.bigpapi.model.StudentDetails;
import com.bigpapi.service.CourseService;
import com.bigpapi.service.StudentService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("")
public class CourseController {


  private CourseService courseService;
  private StudentService studentService;

  public CourseController(CourseService courseService,
      StudentService studentService) {
    this.courseService = courseService;
    this.studentService = studentService;
  }

  @GetMapping("/courses")
  public String fetchAllCourses(Model model) {
    List<Course> courses = courseService.fetchAllCourses();
    model.addAttribute("courses", courses);
    return "courses";
  }

  @GetMapping("/studentCourses")
  public String fetchAllStudentCourses(Model model,
      @AuthenticationPrincipal UserDetails userDetails) {
    if (userDetails == null || StringUtils.isEmpty(userDetails.getUsername())) {
      return "login";
    }
    String username = userDetails.getUsername();
    StudentDetails studentDetails = studentService.findByEmail(username);
    List<Course> courses = studentDetails.getCourses();
    model.addAttribute("courses", courses);
    return "courses";
  }


  @GetMapping("/searchCourse")
  public String searchCourse(@RequestParam("title") String title, Model model) {
    List<Course> courses = courseService.searchCourseByTitle("%" + title + "%");
    model.addAttribute("courses", courses);
    return "courses";
  }


  @GetMapping("/course/{id}")
  public String findCourse(@PathVariable("id") Integer id, Model model,
      @AuthenticationPrincipal UserDetails userDetails) {
    if (userDetails == null || StringUtils.isEmpty(userDetails.getUsername())) {
      return "login";
    }
    boolean enrolled = false;

    String username = userDetails.getUsername();
    StudentDetails studentDetails = studentService.findByEmail(username);
    Course course = courseService.findById(id);
    List<Course> courses = studentDetails.getCourses();
    if (!CollectionUtils.isEmpty(courses)) {
      for (Course studentCourse : courses) {
        if (course.getId() == studentCourse.getId()) {
          enrolled = true;
        }
      }
    }
    model.addAttribute("course", course);
    model.addAttribute("enrolled", enrolled);
    return "course";
  }


  @PostMapping("/enrollCourse/{id}")
  public String enrollCourse(@PathVariable("id") Integer id, Model model,
      @AuthenticationPrincipal UserDetails userDetails, HttpSession session) {
    if (userDetails == null || StringUtils.isEmpty(userDetails.getUsername())) {
      return "login";
    }
    Course course = courseService.findById(id);
    String username = userDetails.getUsername();
    StudentDetails studentDetails = studentService.findByEmail(username);
    List<Course> courses = studentDetails.getCourses();
    courses = courses == null ? new ArrayList<>() : courses;
    courses.add(course);
    studentService.updateStudent(studentDetails);
    model.addAttribute("course", course);
    model.addAttribute("enrolled", true);
    session
        .setAttribute("msg", "You are enrolled in the course. Please log in to the payment portal");
    return "course";
  }

}
