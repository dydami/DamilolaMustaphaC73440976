package com.bigpapi.service;

import com.bigpapi.model.StudentDetails;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

//import org.springframework.security.core.userdetails.UserDetailsService;


@Service
public interface StudentService extends UserDetailsService {

  StudentDetails createStudent(StudentDetails student);

  StudentDetails findByEmail(String email);

  List<StudentDetails> fetchAllStudents();

  StudentDetails findById(Integer id);

  StudentDetails updateStudent(StudentDetails studentDetails);

  void deleteStudent(Integer id);


}
