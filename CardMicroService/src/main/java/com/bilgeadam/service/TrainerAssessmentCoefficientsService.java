package com.bilgeadam.service;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.ITrainerAssessmentCoefficientsMapper;
import com.bilgeadam.repository.ITrainerAssessmentCoefficientsRepository;
import com.bilgeadam.repository.entity.TrainerAssessmentCoefficients;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class TrainerAssessmentCoefficientsService extends ServiceManager<TrainerAssessmentCoefficients, String> {

    private final ITrainerAssessmentCoefficientsRepository iTrainerAssessmentCoefficientsRepository;
    public TrainerAssessmentCoefficientsService(ITrainerAssessmentCoefficientsRepository iTrainerAssessmentCoefficientsRepository, ITrainerAssessmentCoefficientsRepository iTrainerAssessmentCoefficientsRepository1) {
        super(iTrainerAssessmentCoefficientsRepository);
        this.iTrainerAssessmentCoefficientsRepository = iTrainerAssessmentCoefficientsRepository1;
    }
    public TrainerAssessmentCoefficients updateTrainerAssessmentCoefficients(UpdateTrainerAssessmentCoefficientsRequestDto dto){
        System.out.println(dto);
        if (dto.getBehaviorInClassCoefficient()<0.0 || dto.getBehaviorInClassCoefficient()>1.0)
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_COEFFICIENTS_POINT_RANGE);
        if(dto.getCourseInterestLevelCoefficient()<0.0 || dto.getCourseInterestLevelCoefficient()>1.0)
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_COEFFICIENTS_POINT_RANGE);
        if(dto.getCameraOpeningGradeCoefficient()<0.0 || dto.getCameraOpeningGradeCoefficient()>1.0)
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_COEFFICIENTS_POINT_RANGE);
        if(dto.getInstructorGradeCoefficient()<0.0 || dto.getInstructorGradeCoefficient()>1.0)
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_COEFFICIENTS_POINT_RANGE);
        if(dto.getDailyHomeworkGradeCoefficient()<0.0 || dto.getDailyHomeworkGradeCoefficient()>1.0)
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_COEFFICIENTS_POINT_RANGE);

        TrainerAssessmentCoefficients trainerAssessmentCoefficients = ITrainerAssessmentCoefficientsMapper.INSTANCE.toUpdateTrainerAssessmentCoefficients(dto);
        double behaviorInClassCoefficient = dto.getBehaviorInClassCoefficient();
        double courseInterestLevelCoefficient = dto.getCourseInterestLevelCoefficient();
        double cameraOpeningGradeCoefficient = dto.getCameraOpeningGradeCoefficient();
        double instructorGradeCoefficient = dto.getInstructorGradeCoefficient();
        double dailyHomeworkGradeCoefficient = dto.getDailyHomeworkGradeCoefficient();

        double totalTrainerAssessmentCoefficients = behaviorInClassCoefficient + courseInterestLevelCoefficient +
                cameraOpeningGradeCoefficient + instructorGradeCoefficient +
                dailyHomeworkGradeCoefficient;

        if(totalTrainerAssessmentCoefficients != 1){
            throw new CardServiceException(ErrorType.TOTAL_TRAINER_ASSESSMENT_COEFFICIENTS_POINT_RANGE);
        }

        update(trainerAssessmentCoefficients);
        System.out.println("Eğitmen Puanı Katsayıları başarıyla değiştirildi..");
        System.out.println("Güncel Eğitmen Puanı Katsayıları: " + trainerAssessmentCoefficients);
        return trainerAssessmentCoefficients;
    }

    public TrainerAssessmentCoefficients deleteTrainerAssessmentCoefficients(String id) {
        Optional<TrainerAssessmentCoefficients> trainerAssessmentCoefficients = iTrainerAssessmentCoefficientsRepository.findById(id);
        if (trainerAssessmentCoefficients.isEmpty())
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_COEFFICIENTS_EMPTY);
        deleteById(id);
        trainerAssessmentCoefficients.get().setEStatus(EStatus.DELETED);
        update(trainerAssessmentCoefficients.get());
        return ITrainerAssessmentCoefficientsMapper.INSTANCE.toDeleteTrainerAssessmentCoefficients(trainerAssessmentCoefficients.get());
    }
}