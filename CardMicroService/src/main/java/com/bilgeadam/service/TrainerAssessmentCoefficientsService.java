package com.bilgeadam.service;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.GetGraduationProjectResponseDto;
import com.bilgeadam.dto.response.GetTrainerAssessmentCoefficientsResponseDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.ITrainerAssessmentCoefficientsMapper;
import com.bilgeadam.repository.ITrainerAssessmentCoefficientsRepository;
import com.bilgeadam.repository.entity.GraduationProject;
import com.bilgeadam.repository.entity.TrainerAssessmentCoefficients;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainerAssessmentCoefficientsService extends ServiceManager<TrainerAssessmentCoefficients, String> {
    private final JwtTokenManager jwtTokenManager;
    private final ITrainerAssessmentCoefficientsRepository iTrainerAssessmentCoefficientsRepository;
    private final ITrainerAssessmentCoefficientsMapper iTrainerAssessmentCoefficientsMapper;
    public TrainerAssessmentCoefficientsService(ITrainerAssessmentCoefficientsRepository iTrainerAssessmentCoefficientsRepository, JwtTokenManager jwtTokenManager, ITrainerAssessmentCoefficientsRepository iTrainerAssessmentCoefficientsRepository1, ITrainerAssessmentCoefficientsMapper iTrainerAssessmentCoefficientsMapper) {
        super(iTrainerAssessmentCoefficientsRepository);
        this.jwtTokenManager = jwtTokenManager;
        this.iTrainerAssessmentCoefficientsRepository = iTrainerAssessmentCoefficientsRepository1;
        this.iTrainerAssessmentCoefficientsMapper = iTrainerAssessmentCoefficientsMapper;
    }
    public TrainerAssessmentCoefficients updateTrainerAssessmentCoefficients(UpdateTrainerAssessmentCoefficientsRequestDto dto){
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if(studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        if (dto.getBehaviorInClassCoefficient() > 100 || dto.getBehaviorInClassCoefficient() < 0) {
            throw new CardServiceException(ErrorType.GRADUATION_NUMBER_RANGE);
        }
        if (dto.getInstructorGradeCoefficient() > 100 || dto.getInstructorGradeCoefficient() < 0) {
            throw new CardServiceException(ErrorType.GRADUATION_NUMBER_RANGE);
        }
        if (dto.getCameraOpeningGradeCoefficient() > 100 || dto.getCameraOpeningGradeCoefficient() < 0) {
            throw new CardServiceException(ErrorType.GRADUATION_NUMBER_RANGE);
        }
        if (dto.getCourseInterestLevelCoefficient() > 100 || dto.getCourseInterestLevelCoefficient() < 0) {
            throw new CardServiceException(ErrorType.GRADUATION_NUMBER_RANGE);
        }
        if (dto.getDailyHomeworkGradeCoefficient() > 100 || dto.getDailyHomeworkGradeCoefficient() < 0) {
            throw new CardServiceException(ErrorType.GRADUATION_NUMBER_RANGE);
        }
        if((dto.getBehaviorInClassCoefficientPercentage()+ dto.getInstructorGradeCoefficientPercentage()+
                dto.getCameraOpeningGradeCoefficientPercentage()+ dto.getCourseInterestLevelCoefficientPercentage()+
                dto.getDailyHomeworkGradeCoefficientPercentage()) != 100)
            throw new CardServiceException(ErrorType.TOTAL_PERCENTAGE);
        double averageScore = ((dto.getBehaviorInClassCoefficient() * dto.getBehaviorInClassCoefficientPercentage())+
                (dto.getDailyHomeworkGradeCoefficientPercentage() * dto.getDailyHomeworkGradeCoefficient())+
                (dto.getCourseInterestLevelCoefficient() * dto.getCourseInterestLevelCoefficientPercentage())+
                (dto.getCameraOpeningGradeCoefficientPercentage() * dto.getCameraOpeningGradeCoefficient())+
                (dto.getInstructorGradeCoefficient() * dto.getInstructorGradeCoefficientPercentage()))/100 ;
        TrainerAssessmentCoefficients trainerAssessmentCoefficients = iTrainerAssessmentCoefficientsRepository.findByStudentId(studentId.get());
        trainerAssessmentCoefficients.setAverageScore(averageScore);
        trainerAssessmentCoefficients.setBehaviorInClassCoefficient(dto.getBehaviorInClassCoefficient());
        trainerAssessmentCoefficients.setInstructorGradeCoefficient(dto.getInstructorGradeCoefficient());
        trainerAssessmentCoefficients.setCourseInterestLevelCoefficient(dto.getCourseInterestLevelCoefficient());
        trainerAssessmentCoefficients.setCameraOpeningGradeCoefficient(dto.getCameraOpeningGradeCoefficient());
        trainerAssessmentCoefficients.setDailyHomeworkGradeCoefficient(dto.getDailyHomeworkGradeCoefficient());
        trainerAssessmentCoefficients.setBehaviorInClassCoefficientPercentage(dto.getBehaviorInClassCoefficientPercentage());
        trainerAssessmentCoefficients.setInstructorGradeCoefficientPercentage(dto.getInstructorGradeCoefficientPercentage());
        trainerAssessmentCoefficients.setCourseInterestLevelCoefficientPercentage(dto.getCourseInterestLevelCoefficientPercentage());
        trainerAssessmentCoefficients.setCameraOpeningGradeCoefficientPercentage(dto.getCameraOpeningGradeCoefficientPercentage());
        trainerAssessmentCoefficients.setDailyHomeworkGradeCoefficientPercentage(dto.getDailyHomeworkGradeCoefficientPercentage());
        update(trainerAssessmentCoefficients);
        return trainerAssessmentCoefficients;
    }

    public MessageResponse deleteTrainerAssessmentCoefficients(String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if(studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        iTrainerAssessmentCoefficientsRepository.deleteByStudentId(studentId.get());
        return new MessageResponse("Bitirme Projesi başarı ile silindi.");
    }

    public MessageResponse createTrainerAssessmentCoefficients(SaveTrainerAssessmentRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if(studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        if (dto.getBehaviorInClassCoefficient() > 100 || dto.getBehaviorInClassCoefficient() < 0) {
            throw new CardServiceException(ErrorType.GRADUATION_NUMBER_RANGE);
        }
        if (dto.getInstructorGradeCoefficient() > 100 || dto.getInstructorGradeCoefficient() < 0) {
            throw new CardServiceException(ErrorType.GRADUATION_NUMBER_RANGE);
        }
        if (dto.getCameraOpeningGradeCoefficient() > 100 || dto.getCameraOpeningGradeCoefficient() < 0) {
            throw new CardServiceException(ErrorType.GRADUATION_NUMBER_RANGE);
        }
        if (dto.getCourseInterestLevelCoefficient() > 100 || dto.getCourseInterestLevelCoefficient() < 0) {
            throw new CardServiceException(ErrorType.GRADUATION_NUMBER_RANGE);
        }
        if (dto.getDailyHomeworkGradeCoefficient() > 100 || dto.getDailyHomeworkGradeCoefficient() < 0) {
            throw new CardServiceException(ErrorType.GRADUATION_NUMBER_RANGE);
        }

        if((dto.getBehaviorInClassCoefficientPercentage()+ dto.getInstructorGradeCoefficientPercentage()+
                dto.getCameraOpeningGradeCoefficientPercentage()+ dto.getCourseInterestLevelCoefficientPercentage()+
                dto.getDailyHomeworkGradeCoefficientPercentage()) != 100)
            throw new CardServiceException(ErrorType.TOTAL_PERCENTAGE);
        double averageScore = ((dto.getBehaviorInClassCoefficient() * dto.getBehaviorInClassCoefficientPercentage())+
                (dto.getDailyHomeworkGradeCoefficientPercentage() * dto.getDailyHomeworkGradeCoefficient())+
                (dto.getCourseInterestLevelCoefficient() * dto.getCourseInterestLevelCoefficientPercentage())+
                (dto.getCameraOpeningGradeCoefficientPercentage() * dto.getCameraOpeningGradeCoefficient())+
                (dto.getInstructorGradeCoefficient() * dto.getInstructorGradeCoefficientPercentage()))/100 ;
        TrainerAssessmentCoefficients trainerAssessmentCoefficients = iTrainerAssessmentCoefficientsMapper.toCreateTrainerAssessmentCoefficients(dto);
        trainerAssessmentCoefficients.setAverageScore(averageScore);
        trainerAssessmentCoefficients.setStudentId(studentId.get());
        save(trainerAssessmentCoefficients);
        return new MessageResponse("Eğitmen görüşü başarılı ile kaydedildi.");
    }

    public GetTrainerAssessmentCoefficientsResponseDto getTrainerAssessmentCoefficientsResponseDto(String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if(studentId.isEmpty())
        { throw new CardServiceException(ErrorType.INVALID_TOKEN);}
        TrainerAssessmentCoefficients project=iTrainerAssessmentCoefficientsRepository.findByStudentId(studentId.get());
        GetTrainerAssessmentCoefficientsResponseDto getTrainerAssessmentCoefficientsResponseDto = iTrainerAssessmentCoefficientsMapper.toTrainerAssessmentCoefficients(project);
        return getTrainerAssessmentCoefficientsResponseDto;
    }
}