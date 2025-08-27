package com.kiran.certification.certification.service;

import java.util.List;

import com.kiran.certification.certification.model.Student;

public interface StudentService {

	    Student createStudent(Student student);
	    Student getStudentById(Long id);
	    List<Student> getAllStudents();
	    Student updateStudent(Long id, Student student);
	    void deleteStudent(Long id);
}
