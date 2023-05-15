package com.bigpapi.service;

import com.bigpapi.model.Course;
import com.bigpapi.repository.CourseRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CourseServiceImpl implements CourseService {

  private CourseRepository courseRepository;


  public CourseServiceImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  public List<Course> fetchAllCourses() {
    List<Course> courses = courseRepository.findAll();
    return courses != null ? courses : new ArrayList<>();
  }


  public Course findById(Integer id) {
    return courseRepository.findById(id).get();
  }

  @Override
  public List<Course> searchCourseByTitle(String title) {
    return courseRepository.searchCourseByTitle(title);
  }

//  @Override
//  public List<Course> findCoursesByStudentId(Integer id) {
//    return courseRepository.findCoursesByStudentId(id);
//  }

}
