package com.bigpapi.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "student")
public class StudentDetails implements Serializable {

  static final long serialVersionUID = 42L;


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;


  private String firstName;

  private String lastName;

  private String email;

  private String password;

  private String role;

  @ManyToMany
  @JoinTable(name = "student_course",
      joinColumns = @JoinColumn(name = "student_id"),
      inverseJoinColumns = @JoinColumn(name = "course_id"))
  private List<Course> courses;
}
