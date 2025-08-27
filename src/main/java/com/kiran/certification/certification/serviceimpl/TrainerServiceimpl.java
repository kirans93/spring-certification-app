package com.kiran.certification.certification.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kiran.certification.certification.model.Trainer;
import com.kiran.certification.certification.repo.TrainerRepository;
import com.kiran.certification.certification.service.TrainerService;

@Service
public class TrainerServiceimpl implements TrainerService {
	
	private final TrainerRepository trainerRepository;


    public TrainerServiceimpl(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    public Trainer createTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    @Override
    public Trainer getTrainerById(Long id) {
        return trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found with id: " + id));
    }

    @Override
    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    @Override
    public Trainer updateTrainer(Long id, Trainer updatedTrainer) {
        Trainer trainer = getTrainerById(id);
        trainer.setName(updatedTrainer.getName());
        trainer.setExpertise(updatedTrainer.getExpertise());
        return trainerRepository.save(trainer);
    }

    @Override
    public void deleteTrainer(Long id) {
        Trainer trainer = getTrainerById(id);
        trainerRepository.delete(trainer);
    }

}
