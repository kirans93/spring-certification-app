package com.kiran.certification.certification.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiran.certification.certification.model.Trainer;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

	// Example custom method
	Trainer findByEmail(String email);

}
