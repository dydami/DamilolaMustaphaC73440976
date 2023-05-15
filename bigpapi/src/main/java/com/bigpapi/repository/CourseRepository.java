package com.bigpapi.repository;

import com.bigpapi.model.Course;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

  @Query(" SELECT c FROM Course c WHERE LOWER(c.title) like LOWER(:title)")
  List<Course> searchCourseByTitle(@Param("title") String string);

//  List<Course> findCoursesByStudentId(Integer id);

}
