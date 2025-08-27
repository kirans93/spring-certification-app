package com.kiran.certification.certification.service;

import java.util.List;

import com.kiran.certification.certification.model.Certificate;

public interface CertificateService {

	 Certificate generateCertificate(Long trainingId);
	    Certificate getCertificateById(Long id);
	    List<Certificate> getAllCertificates();
	    byte[] downloadCertificate(Long id); // later for PDF
}
