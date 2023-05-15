package com.bigpapi.service;


import com.bigpapi.model.StudentDetails;
import com.bigpapi.repository.StudentRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class StudentServiceImpl implements StudentService {

  private StudentRepository studentRepository;
  private PasswordEncoder passwordEncoder;

  public StudentServiceImpl(StudentRepository studentRepository,
      PasswordEncoder passwordEncoder) {
    this.studentRepository = studentRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public StudentDetails createStudent(StudentDetails student) {
    StudentDetails studentCheck = studentRepository.findByEmail(student.getEmail());
    if (studentCheck != null) {
      throw new RuntimeException("Data exist");
    }
    if (student.getPassword() != null && !(student.getPassword()).equals("")) {
      student.setPassword(passwordEncoder.encode(student.getPassword()));
    }
    StudentDetails persistedStudent = studentRepository.save(student);
    return persistedStudent;
  }


  public List<StudentDetails> fetchAllStudents() {
    List<StudentDetails> studentDetails = studentRepository.findAll();
    return studentDetails != null ? studentDetails : new ArrayList<>();
  }

  public StudentDetails findByEmail(String email) {
    return studentRepository.findByEmail(email);
  }

  public StudentDetails findById(Integer id) {
    return studentRepository.findById(id).get();
  }

  @Override
  public StudentDetails updateStudent(StudentDetails student) {
    StudentDetails existingStudent = findById(student.getId());
    if (existingStudent != null) {
      existingStudent.setFirstName(student.getFirstName());
      existingStudent.setLastName(student.getLastName());
      existingStudent.setEmail(student.getEmail());
      existingStudent.setCourses(student.getCourses());
      studentRepository.save(existingStudent);
    }
    return student;
  }

  @Override
  public void deleteStudent(Integer id) {
    StudentDetails existingStudent = findById(id);
    if (existingStudent != null) {
      studentRepository.delete(existingStudent);
    }
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    StudentDetails student = studentRepository.findByEmail(username);
    if (student != null) {
      return new org.springframework.security.core.userdetails.User(student.getEmail(),
          student.getPassword(), new ArrayList<>());
    }
    throw new UsernameNotFoundException("user not found");
  }
}