package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateApplicationProcessRequestDto;
import com.bilgeadam.dto.request.UpdateApplicationProcessRequestDto;
import com.bilgeadam.dto.response.GetApplicationProcessResponseDto;
import com.bilgeadam.exceptions.ApplicationProcessException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.IApplicationProcessMapper;
import com.bilgeadam.repository.IApplicationProcessRepository;
import com.bilgeadam.repository.entity.ApplicationProcess;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationProcessService extends ServiceManager<ApplicationProcess, String> {
    private final IApplicationProcessRepository applicationProcessRepository;
    private final JwtTokenManager jwtTokenManager;
    private final IApplicationProcessMapper applicationProcessMapper;

    public ApplicationProcessService(IApplicationProcessRepository applicationProcessRepository, JwtTokenManager jwtTokenManager, IApplicationProcessMapper applicationProcessMapper) {
        super(applicationProcessRepository);
        this.applicationProcessRepository = applicationProcessRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.applicationProcessMapper = applicationProcessMapper;
    }

    public Boolean save(CreateApplicationProcessRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isEmpty()) {
            throw new ApplicationProcessException(ErrorType.INVALID_TOKEN);
        }
        Optional<ApplicationProcess> optionalApplicationProcess = applicationProcessRepository.findOptionalByStudentId(studentId.get());
        if (optionalApplicationProcess.isPresent()) {
            throw new ApplicationProcessException(ErrorType.STUDENT_APPLICATION_PROCESS_ALREADY_EXIST);
        }
        if (dto.getJobApplicationScore() > 100 || dto.getJobApplicationScore() < 0) {
            throw new ApplicationProcessException(ErrorType.APPLICATION_PROCESS_SCORE_NUMBER_RANGE);
        }
        if (dto.getInformationSharingScore() > 100 || dto.getInformationSharingScore() < 0) {
            throw new ApplicationProcessException(ErrorType.APPLICATION_PROCESS_SCORE_NUMBER_RANGE);
        }
        if (dto.getReferralParticipationScore() > 100 || dto.getReferralParticipationScore() < 0) {
            throw new ApplicationProcessException(ErrorType.APPLICATION_PROCESS_SCORE_NUMBER_RANGE);
        }
        if (dto.getCompanyFitScore() > 100 || dto.getCompanyFitScore() < 0) {
            throw new ApplicationProcessException(ErrorType.APPLICATION_PROCESS_SCORE_NUMBER_RANGE);
        }
        if (dto.getCareerTeamAssessment() > 100 || dto.getCareerTeamAssessment() < 0) {
            throw new ApplicationProcessException(ErrorType.APPLICATION_PROCESS_SCORE_NUMBER_RANGE);
        }
        ApplicationProcess applicationProcess = applicationProcessMapper.fromCreateApplicationProcessRequestDtoToApplicationProcess(dto);
        applicationProcess.setStudentId(studentId.get());
        save(applicationProcess);
        calculateApplicationProcessRate(applicationProcess.getStudentId());
        return true;
    }
    public GetApplicationProcessResponseDto findApplicationProcessById(String studentId){
        Optional<ApplicationProcess> optionalApplicationProcess = applicationProcessRepository.findOptionalByStudentId(studentId);
        if (optionalApplicationProcess.isPresent()) {
            GetApplicationProcessResponseDto applicationProcessResponseDto = applicationProcessMapper.fromApplicationProcessToGetApplicationProcessResponseDto(optionalApplicationProcess.get());
            return applicationProcessResponseDto;
        } else {
            throw new ApplicationProcessException(ErrorType.APPLICATION_PROCESS_NOT_FOUND);
        }
    }
    public Boolean update(UpdateApplicationProcessRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isEmpty()) {
            throw new ApplicationProcessException(ErrorType.INVALID_TOKEN);
        }
        if (dto.getJobApplicationScore() > 100 || dto.getJobApplicationScore() < 0) {
            throw new ApplicationProcessException(ErrorType.APPLICATION_PROCESS_SCORE_NUMBER_RANGE);
        }
        if (dto.getInformationSharingScore() > 100 || dto.getInformationSharingScore() < 0) {
            throw new ApplicationProcessException(ErrorType.APPLICATION_PROCESS_SCORE_NUMBER_RANGE);
        }
        if (dto.getReferralParticipationScore() > 100 || dto.getReferralParticipationScore() < 0) {
            throw new ApplicationProcessException(ErrorType.APPLICATION_PROCESS_SCORE_NUMBER_RANGE);
        }
        if (dto.getCompanyFitScore() > 100 || dto.getCompanyFitScore() < 0) {
            throw new ApplicationProcessException(ErrorType.APPLICATION_PROCESS_SCORE_NUMBER_RANGE);
        }
        if (dto.getCareerTeamAssessment() > 100 || dto.getCareerTeamAssessment() < 0) {
            throw new ApplicationProcessException(ErrorType.APPLICATION_PROCESS_SCORE_NUMBER_RANGE);
        }
        Optional<ApplicationProcess> optionalApplicationProcess = applicationProcessRepository.findOptionalByStudentId(studentId.get());
        if (optionalApplicationProcess.isPresent()) {
            update(applicationProcessMapper.fromUpdateApplicationProcessRequestDtoToApplicationProcess(dto, optionalApplicationProcess.get()));
            calculateApplicationProcessRate(optionalApplicationProcess.get().getStudentId());
            return true;
        }
        throw new ApplicationProcessException(ErrorType.APPLICATION_PROCESS_NOT_FOUND);
    }

    public Boolean delete(String studentId) {
        Optional<ApplicationProcess> optionalApplicationProcess = applicationProcessRepository.findOptionalByStudentId(studentId);
        if (optionalApplicationProcess.isPresent()) {
            applicationProcessRepository.deleteApplicationProcessByStudentId(studentId);
            return true;
        } else {
            throw new ApplicationProcessException(ErrorType.APPLICATION_PROCESS_NOT_FOUND);
        }
    }

    public Double calculateApplicationProcessRate(String studentId) {
        double weight = 0.05;
        Optional<ApplicationProcess> optionalApplicationProcess = applicationProcessRepository.findOptionalByStudentId(studentId);
        if (optionalApplicationProcess.isPresent()) {
            double score = (optionalApplicationProcess.get().getJobApplicationScore() + optionalApplicationProcess.get().getInformationSharingScore() + optionalApplicationProcess.get().getReferralParticipationScore() + optionalApplicationProcess.get().getCompanyFitScore() + optionalApplicationProcess.get().getCareerTeamAssessment()) * weight;
            optionalApplicationProcess.get().setTotalScore(score);
            save(optionalApplicationProcess.get());
            return score;
        } else {
            return null;
        }
    }
}
