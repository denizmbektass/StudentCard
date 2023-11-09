package com.bilgeadam.service;

import com.bilgeadam.dto.request.SaveCareerEducationRequestDto;
import com.bilgeadam.dto.request.UpdateCareerEducationRequestDto;
import com.bilgeadam.dto.response.GetCareerEducationResponseDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.ICareerEducationMapper;
import com.bilgeadam.repository.ICareerEducationRepository;
import com.bilgeadam.repository.entity.CareerEducation;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CareerEducationService extends ServiceManager<CareerEducation, String> {
    private final ICareerEducationRepository careerEducationRepository;
    private final JwtTokenManager jwtTokenManager;

    public CareerEducationService(ICareerEducationRepository careerEducationRepository, JwtTokenManager
            jwtTokenManager) {
        super(careerEducationRepository);
        this.careerEducationRepository = careerEducationRepository;
        this.jwtTokenManager = jwtTokenManager;
    }

    public Integer getCareerEducationCount(String studentId) {
        int careerEducationCount = Integer.MAX_VALUE;
        CareerEducation careerEducation;
        if (!(studentId == null)) {
            careerEducation = careerEducationRepository.findByStudentId(studentId);
            if (careerEducation == null) {
                return 0;
            }
            if (careerEducation != null) {
                return 1;
            }
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        return careerEducationCount;
    }

    public boolean saveCareerEducation(SaveCareerEducationRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isPresent()) {
            CareerEducation newCareerEducation = ICareerEducationMapper.INSTANCE
                    .fromSaveCareerEducationRequestDtoToCareerEducation(dto);
            newCareerEducation.setStudentId(studentId.get());
            save(newCareerEducation);
            return true;
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
    }

    public boolean updateCareerEducation(UpdateCareerEducationRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isPresent()) {
            CareerEducation careerEducation = careerEducationRepository.findByStudentId(studentId.get());
            careerEducation.setCentralHrContinuityPoint(dto.getCentralHrContinuityPoint());
            careerEducation.setCentralHrActivityPoint(dto.getCentralHrActivityPoint());
            careerEducation.setBoostContinuityPoint(dto.getBoostContinuityPoint());
            careerEducation.setBoostActivityPoint(dto.getBoostActivityPoint());
            careerEducation.setResumeDeliveryPoint(dto.getResumeDeliveryPoint());
            careerEducation.setResumeConveniencePoint(dto.getResumeConveniencePoint());
            careerEducation.setResumeUpToDatePoint(dto.getResumeUpToDatePoint());
            update(careerEducation);
            return true;
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }

    }

    public GetCareerEducationResponseDto getCareerEducation(String studentId) {
        GetCareerEducationResponseDto responseDto;
        if (!studentId.equals("")) {
            if (careerEducationRepository.findByStudentId(studentId) != null) {
                CareerEducation careerEducation = careerEducationRepository.findByStudentId(studentId);
                responseDto = ICareerEducationMapper.INSTANCE
                        .fromCareerEducationToGetCareerEducationResponseDto(careerEducation);
            } else {
                throw new CardServiceException(ErrorType.CAREER_EDUCATION_NOT_FOUND);
            }
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        return responseDto;
    }

    public Double getCareerEducationAveragePoint(String studentId) {
        if (studentId == null || studentId.isEmpty()) {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        int totalQuestionCount = 7;
        double careerEducationAveragePoint = 0;
        if (careerEducationRepository.findByStudentId(studentId) != null) {
            CareerEducation careerEducation = careerEducationRepository.findByStudentId(studentId);
            if (careerEducation != null) {
                careerEducationAveragePoint = ((double) careerEducation.getCentralHrContinuityPoint() / totalQuestionCount)
                        + ((double) careerEducation.getCentralHrActivityPoint() / totalQuestionCount)
                        + ((double) careerEducation.getBoostContinuityPoint() / totalQuestionCount)
                        + ((double) careerEducation.getBoostActivityPoint() / totalQuestionCount)
                        + ((double) careerEducation.getResumeDeliveryPoint() / totalQuestionCount)
                        + ((double) careerEducation.getResumeConveniencePoint() / totalQuestionCount)
                        + ((double) careerEducation.getResumeUpToDatePoint() / totalQuestionCount);
            } else {
                return null;
            }
        }else{
            return null;
        }
        return careerEducationAveragePoint;
    }
}
