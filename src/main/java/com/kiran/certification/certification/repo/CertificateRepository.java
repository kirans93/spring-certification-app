package com.kiran.certification.certification.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiran.certification.certification.model.Certificate;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

	// Find certificate by student
    List<Certificate> findByStudentId(Long studentId);

    // Find certificate by training
    List<Certificate> findByTrainingId(Long trainingId);
}
