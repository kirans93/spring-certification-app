package com.kiran.certification.certification.service;

import java.util.List;

import com.kiran.certification.certification.model.Training;

public interface TrainingService {

	Training assignTraining(Long studentId, Long trainerId, Training training);
    Training getTrainingById(Long id);
    List<Training> getAllTrainings();
    Training updateCompletionStatus(Long trainingId, boolean status);
    void deleteTraining(Long id);
}
