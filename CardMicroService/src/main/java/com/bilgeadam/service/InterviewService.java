package com.bilgeadam.service;

import com.bilgeadam.dto.request.*;

import com.bilgeadam.dto.response.*;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.exceptions.InterviewServiceException;
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

    public CreateInterviewResponseDto createInterview(CreateInterviewRequestDto dto) {
        //if check ile böyle bir student var mı eklenebilir user micro service oluşturulduktan sonra.
        if (dto.getInterviewType().isBlank())
            throw new CardServiceException(ErrorType.INTERVIEW_TYPE_EMPTY);
        if (dto.getScore() < 0 || dto.getScore() > 100)
            throw new CardServiceException(ErrorType.INTERVIEW_SCORE_NUMBER_RANGE);
        if (dto.getDescription().isBlank())
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_EMPTY);
        if (dto.getScore() == null)
            throw new InterviewServiceException(ErrorType.POINT_EMPTY);
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getToken());
        Interview interview = IInterviewMapper.INSTANCE.toInterview(dto);
        interview.setStudentId(studentId.get());
        save(interview);
        return IInterviewMapper.INSTANCE.toCreateInterviewResponseDto(interview);
    }

    public UpdateInterviewResponseDto updateInterview(UpdateInterviewRequestDto dto) {
        Optional<Interview> interview = interviewRepository.findById(dto.getInterviewId());
        if (interview.isEmpty()) {
            throw new CardServiceException(ErrorType.INTERVIEW_NOT_FOUND);
        }
        if (dto.getDescription().isBlank())
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_EMPTY);
        if (dto.getScore() == null)
            throw new CardServiceException(ErrorType.POINT_EMPTY);
        if (dto.getInterviewType().isBlank())
            throw new CardServiceException(ErrorType.INTERVIEW_TYPE_EMPTY);
        if (dto.getName().isBlank())
            throw new CardServiceException(ErrorType.INTERVIEW_NAME_EMPTY);
        Interview toUpdateInterview = interview.get();
        toUpdateInterview.setDescription(dto.getDescription());
        toUpdateInterview.setName(dto.getName());
        toUpdateInterview.setScore(dto.getScore());
        toUpdateInterview.setInterviewType((dto.getInterviewType()));
        update(toUpdateInterview);
        return IInterviewMapper.INSTANCE.toUpdateInterviewResponseDto(interview.get());
    }

    public DeleteInterviewResponseDto deleteInterview(String id) {
        Optional<Interview> interview = interviewRepository.findById(id);
        if (interview.isEmpty()) {
            throw new CardServiceException(ErrorType.INTERVIEW_NOT_FOUND);
        }
        interview.get().setEStatus(EStatus.DELETED);
        update(interview.get());
        return IInterviewMapper.INSTANCE.toDeleteInterviewResponseDto(interview.get());
    }

    public Integer getInterviewNote(String studentId) {
        return (int) Math.floor(interviewRepository.findAllByStudentId(studentId).stream()
                .mapToLong(x -> x.getScore()).average().orElse(0));
    }

    public List<Interview> findAllInterviews(TokenRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getToken());
        return findAll().stream().filter(x -> x.getEStatus() == EStatus.ACTIVE && x.getStudentId().equals(studentId.get()))
                .collect(Collectors.toList());
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

    public Integer getCandidateInterviewNumber(String studentId) {
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
        Double candidateInterviewAveragePoint = 0.0;
        Interview candidateInterview = interviewRepository.findAllByStudentId(studentId).get(0);
        if (!studentId.equals("")) {
            candidateInterviewAveragePoint = ((double) candidateInterview.getCommunicationSkillsPoint() / 12) +
                    ((double) candidateInterview.getWorkExperiencePoint() / 12) +
                    ((double) candidateInterview.getUniversityPoint() / 12) +
                    ((double) candidateInterview.getUniversityProgramPoint() / 12) +
                    ((double) candidateInterview.getAgePoint() / 12) +
                    ((double) candidateInterview.getPersonalityEvaluationPoint() / 12) +
                    ((double) candidateInterview.getEnglishLevelPoint() / 12) +
                    ((double) candidateInterview.getGraduationPeriodPoint() / 12) +
                    ((double) candidateInterview.getMilitaryServicePoint() / 12) +
                    ((double) candidateInterview.getMotivationPoint() / 12) +
                    ((double) candidateInterview.getResidencyPoint() / 12) +
                    ((double) candidateInterview.getSoftwareEducationPoint() / 12);

        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        return candidateInterviewAveragePoint;
    }
}