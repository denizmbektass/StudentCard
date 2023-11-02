package com.bilgeadam.service;

import com.bilgeadam.dto.request.*;

import com.bilgeadam.dto.response.*;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.IInterviewMapper;
import com.bilgeadam.repository.IInterviewRepository;
import com.bilgeadam.repository.entity.Interview;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InterviewService extends ServiceManager<Interview, String> {
    private final IInterviewRepository interviewRepository;
    private final JwtTokenManager jwtTokenManager;


    public InterviewService(IInterviewRepository interviewRepository, JwtTokenManager jwtTokenManager) {
        super(interviewRepository);
        this.interviewRepository = interviewRepository;
        this.jwtTokenManager = jwtTokenManager;
    }

    public List<InterviewForTranscriptResponseDto> findAllInterviewsDtos(String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        List<Interview> interviews = findAll().stream().filter(x -> x.getEStatus() == EStatus.ACTIVE && x.getStudentId().equals(studentId.get()))
                .collect(Collectors.toList());
        List<InterviewForTranscriptResponseDto> interviewForTranscriptResponseDtos = new ArrayList<>();
        interviews.forEach(x -> {
            InterviewForTranscriptResponseDto dto = IInterviewMapper.INSTANCE.toInterviewForTranscriptResponseDto(x);
            interviewForTranscriptResponseDtos.add(dto);
        });
        return interviewForTranscriptResponseDtos;
    }

    public boolean saveCandidateInterview(SaveInterviewRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isPresent()) {
            Interview newInterview = IInterviewMapper.INSTANCE.fromSaveInterviewRequestDtoToInterview(dto);
            newInterview.setStudentId(studentId.get());
            save(newInterview);
            return true;
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
    }

    public GetCandidateInterviewResponseDto getCandidateInterview(String studentId) {
        GetCandidateInterviewResponseDto responseDto;
        if (!studentId.equals("")) {
            if (interviewRepository.findAllByStudentId(studentId).size() > 0) {
                Interview candidateInterview = interviewRepository.findAllByStudentId(studentId).get(0);
                responseDto = IInterviewMapper.INSTANCE.fromInterviewToGetCandidateInterviewResponseDto(candidateInterview);
            } else {
                throw new CardServiceException(ErrorType.CANDIDATE_INTERVIEW_NOT_FOUND);
            }
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        return responseDto;
    }

    public Boolean updateCandidateInterview(UpdateCandidateInterviewRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isPresent()) {
            Interview candidateInterview = interviewRepository.findAllByStudentId(studentId.get()).get(0);
            candidateInterview.setCommunicationSkillsPoint(dto.getCommunicationSkillsPoint());
            candidateInterview.setWorkExperiencePoint(dto.getWorkExperiencePoint());
            candidateInterview.setUniversityPoint(dto.getUniversityPoint());
            candidateInterview.setUniversityProgramPoint(dto.getUniversityProgramPoint());
            candidateInterview.setAgePoint(dto.getAgePoint());
            candidateInterview.setPersonalityEvaluationPoint(dto.getPersonalityEvaluationPoint());
            candidateInterview.setEnglishLevelPoint(dto.getEnglishLevelPoint());
            candidateInterview.setGraduationPeriodPoint(dto.getGraduationPeriodPoint());
            candidateInterview.setMilitaryServicePoint(dto.getMilitaryServicePoint());
            candidateInterview.setMotivationPoint(dto.getMotivationPoint());
            candidateInterview.setResidencyPoint(dto.getResidencyPoint());
            candidateInterview.setSoftwareEducationPoint(dto.getSoftwareEducationPoint());
            update(candidateInterview);
            return true;
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
    }

    public Integer getCandidateInterviewCount(String studentId) {
        Integer candidateInterviewCount = Integer.MAX_VALUE;
        List<Interview> candidateInterviewList;
        if (!studentId.equals("")) {
            candidateInterviewList = interviewRepository.findAllByStudentId(studentId);
            if (candidateInterviewList.size() == 0) {
                return 0;
            }
            if (candidateInterviewList.size() > 0) {
                return 1;
            }
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        return candidateInterviewCount;
    }

    public Double getCandidateInterviewAveragePoint(String studentId) {
        if (studentId == null || studentId.isEmpty()) {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        int totalQuestionCount = 12;
        Double candidateInterviewAveragePoint;
        Interview candidateInterview = interviewRepository.findByStudentId(studentId);
        if (candidateInterview != null) {
            candidateInterviewAveragePoint = ((double) candidateInterview.getCommunicationSkillsPoint() / totalQuestionCount) +
                    ((double) candidateInterview.getWorkExperiencePoint() / totalQuestionCount) +
                    ((double) candidateInterview.getUniversityPoint() / totalQuestionCount) +
                    ((double) candidateInterview.getUniversityProgramPoint() / totalQuestionCount) +
                    ((double) candidateInterview.getAgePoint() / totalQuestionCount) +
                    ((double) candidateInterview.getPersonalityEvaluationPoint() / totalQuestionCount) +
                    ((double) candidateInterview.getEnglishLevelPoint() / totalQuestionCount) +
                    ((double) candidateInterview.getGraduationPeriodPoint() / totalQuestionCount) +
                    ((double) candidateInterview.getMilitaryServicePoint() / totalQuestionCount) +
                    ((double) candidateInterview.getMotivationPoint() / totalQuestionCount) +
                    ((double) candidateInterview.getResidencyPoint() / totalQuestionCount) +
                    ((double) candidateInterview.getSoftwareEducationPoint() / totalQuestionCount);
        } else {
           return null;
        }
        return candidateInterviewAveragePoint;
    }
}