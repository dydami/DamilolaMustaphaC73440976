package com.bigpapi.service;

import com.bigpapi.model.Course;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface CourseService {

  List<Course> fetchAllCourses();

  Course findById(Integer id);

  List<Course> searchCourseByTitle(String string);

//  List<Course> findCoursesByStudentId(Integer id);


}
