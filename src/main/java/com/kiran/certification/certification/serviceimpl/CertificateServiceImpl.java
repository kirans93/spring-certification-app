package com.kiran.certification.certification.serviceimpl;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.kiran.certification.certification.model.Certificate;
import com.kiran.certification.certification.model.Student;
import com.kiran.certification.certification.model.Training;
import com.kiran.certification.certification.repo.CertificateRepository;
import com.kiran.certification.certification.repo.TrainingRepository;
import com.kiran.certification.certification.service.CertificateService;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final TrainingRepository trainingRepository;
    private final TemplateEngine templateEngine;

    public CertificateServiceImpl(CertificateRepository certificateRepository,
                                  TrainingRepository trainingRepository,
                                  TemplateEngine templateEngine) {
        this.certificateRepository = certificateRepository;
        this.trainingRepository = trainingRepository;
        this.templateEngine = templateEngine;
    }

    @Override
    public Certificate generateCertificate(Long trainingId) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new RuntimeException("Training not found with id: " + trainingId));

        if (!Boolean.TRUE.equals(training.getCompletionStatus())) {
            throw new RuntimeException("Cannot generate certificate: Training not completed yet");
        }

        if (training.getStudents() == null || training.getStudents().isEmpty()) {
            throw new RuntimeException("No students enrolled in this training, cannot generate certificates.");
        }

        List<Certificate> certificates = new ArrayList<>();
        for (Student student : training.getStudents()) {
            Certificate certificate = new Certificate();
            certificate.setTraining(training);
            certificate.setStudent(student);
            certificate.setTrainer(training.getTrainer());
            certificate.setIssueDate(LocalDate.now());
            certificate.setCertificateId("CERT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            certificates.add(certificateRepository.save(certificate));
        }

        return certificates.get(0);
    }

    @Override
    public Certificate getCertificateById(Long id) {
        return certificateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificate not found with id: " + id));
    }

    @Override
    public byte[] downloadCertificate(Long id) {
        Certificate certificate = getCertificateById(id);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Student student = certificate.getStudent();
            String studentName = (student != null ? student.getName() : "UNKNOWN");

            // Debug log
            System.out.println("DEBUG: Student Name = " + studentName);

            Context context = new Context();
            context.setVariable("companyName", "ProIntern Training");
            context.setVariable("recipientName", studentName);
            context.setVariable("courseFocus", certificate.getTraining().getTitle()); // ✅ Only title now
            context.setVariable("issueDate", certificate.getIssueDate().toString());
            context.setVariable("certificateId", certificate.getCertificateId());

            String htmlContent = templateEngine.process("certificate", context);

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(baos);

            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF: " + e.getMessage(), e);
        }
    }



    @Override
    public List<Certificate> getAllCertificates() {
        return certificateRepository.findAll();
    }
}
