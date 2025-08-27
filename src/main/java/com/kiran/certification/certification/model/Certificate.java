package com.kiran.certification.certification.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String certificateId;

    private LocalDate issueDate;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    public Certificate() {}

    public Certificate(Long id, String certificateId, LocalDate issueDate, 
                       Student student, Training training, Trainer trainer) {
        this.id = id;
        this.certificateId = certificateId;
        this.issueDate = issueDate;
        this.student = student;
        this.training = training;
        this.trainer = trainer;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCertificateId() { return certificateId; }
    public void setCertificateId(String certificateId) { this.certificateId = certificateId; }

    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Training getTraining() { return training; }
    public void setTraining(Training training) { this.training = training; }

    public Trainer getTrainer() { return trainer; }
    public void setTrainer(Trainer trainer) { this.trainer = trainer; }

    @Override
    public String toString() {
        return "Certificate [id=" + id + ", certificateId=" + certificateId + ", issueDate=" + issueDate + "]";
    }
}
