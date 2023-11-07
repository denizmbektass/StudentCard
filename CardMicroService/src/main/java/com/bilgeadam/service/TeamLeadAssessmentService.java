package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateTeamLeadAssesmentRequestDto;
import com.bilgeadam.dto.request.UpdateTeamLeadAssessmentRequestDto;
import com.bilgeadam.dto.response.GetTeamLeadAssessmentDetailsResponseDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.ITeamLeadAssessmentMapper;
import com.bilgeadam.repository.ITeamLeadAssessmentRepository;
import com.bilgeadam.repository.entity.TeamLeadAssessment;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamLeadAssessmentService extends ServiceManager<TeamLeadAssessment, String> {
    private final ITeamLeadAssessmentRepository teamLeadAssesmentRepository;
    private final JwtTokenManager jwtTokenManager;

    public TeamLeadAssessmentService(MongoRepository<TeamLeadAssessment, String> repository, ITeamLeadAssessmentRepository teamLeadAssesmentRepository, JwtTokenManager jwtTokenManager) {
        super(repository);
        this.teamLeadAssesmentRepository = teamLeadAssesmentRepository;
        this.jwtTokenManager = jwtTokenManager;
    }

    public Boolean saveTrainerAssessment(CreateTeamLeadAssesmentRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isPresent()) {
            TeamLeadAssessment teamLeadAssessment = ITeamLeadAssessmentMapper.INSTANCE.toTeamLeadAssessment(dto);
            teamLeadAssessment.setStudentId(studentId.get());
            teamLeadAssessment.setSuccessScore(calculateSuccessScore(dto.getScore()));
            save(teamLeadAssessment);
            return true;
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
    }

    public Double calculateSuccessScore(Double score) {
        double teamLeadAssesmentWeight = 0.25;
        if (score == null) {
            return null;
        } else {
            return score * teamLeadAssesmentWeight;
        }
    }

    public Boolean updateTrainerAssessment(UpdateTeamLeadAssessmentRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isPresent()) {
            TeamLeadAssessment teamLeadAssessment = teamLeadAssesmentRepository.findByStudentId(studentId.get());
            teamLeadAssessment.setScore(dto.getScore());
            teamLeadAssessment.setSuccessScore(calculateSuccessScore(teamLeadAssessment.getScore()));
            teamLeadAssessment.setAssessment(dto.getAssessment());
            update(teamLeadAssessment);
            return true;
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
    }

    public GetTeamLeadAssessmentDetailsResponseDto getTeamLeadsAssessmentDetails(String studentId){
        TeamLeadAssessment teamLeadAssessment = teamLeadAssesmentRepository.findByStudentId(studentId);
        if (teamLeadAssessment == null) {
            return null;
        } else {
            return GetTeamLeadAssessmentDetailsResponseDto.builder()
                    .score(teamLeadAssessment.getScore())
                    .successScore(teamLeadAssessment.getSuccessScore())
                    .assessment(teamLeadAssessment.getAssessment())
                    .build();
        }
    }
}
