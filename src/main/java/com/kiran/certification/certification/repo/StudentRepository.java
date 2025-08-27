package com.kiran.certification.certification.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiran.certification.certification.model.Student;


public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByEmail(String email);

}
