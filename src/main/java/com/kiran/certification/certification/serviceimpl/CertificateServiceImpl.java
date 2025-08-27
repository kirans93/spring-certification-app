package com.kiran.certification.certification.serviceimpl;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
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
    private final TemplateEngine templateEngine;   // ✅ added for HTML rendering

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

        if (!training.getCompletionStatus()) {
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
            certificate.setCertificateId(UUID.randomUUID().toString());
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
        Certificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificate not found with id: " + id));

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // ✅ Prepare Thymeleaf context
            Context context = new Context();
            context.setVariable("studentName", certificate.getStudent().getName());
            context.setVariable("trainingTitle", certificate.getTraining().getTitle());
            context.setVariable("trainerName", certificate.getTrainer().getName());
            context.setVariable("issueDate", certificate.getIssueDate().toString());
            context.setVariable("certificateId", certificate.getCertificateId());

            // ✅ Setup Thymeleaf template resolver
            ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
            resolver.setPrefix("templates/");   // looks inside resources/templates/
            resolver.setSuffix(".html");
            resolver.setTemplateMode("XHTML");  // safer for PDF rendering
            resolver.setCharacterEncoding("UTF-8");

            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(resolver);

            // ✅ Render HTML using template
            String htmlContent = templateEngine.process("certificate", context);

            // ✅ Convert HTML → PDF using Flying Saucer
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
