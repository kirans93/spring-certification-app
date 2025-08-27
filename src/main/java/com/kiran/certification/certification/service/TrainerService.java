package com.kiran.certification.certification.service;

import java.util.List;

import com.kiran.certification.certification.model.Trainer;

public interface TrainerService {

	 Trainer createTrainer(Trainer trainer);
	    Trainer getTrainerById(Long id);
	    List<Trainer> getAllTrainers();
	    Trainer updateTrainer(Long id, Trainer trainer);
	    void deleteTrainer(Long id);
}
