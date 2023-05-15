package com.bigpapi.repository;

import com.bigpapi.model.StudentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends JpaRepository<StudentDetails, Integer> {


  StudentDetails findByEmail(String email);

}

