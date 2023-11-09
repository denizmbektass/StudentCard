package com.bilgeadam.service;

import com.bilgeadam.dto.request.SaveContributionRequestDto;
import com.bilgeadam.dto.request.UpdateContributionRequestDto;
import com.bilgeadam.dto.response.GetContributionResponseDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.IContributionMapper;
import com.bilgeadam.repository.IContributionRepository;
import com.bilgeadam.repository.entity.Contribution;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContributionService extends ServiceManager<Contribution, String> {
    private final IContributionRepository contributionRepository;
    private final JwtTokenManager jwtTokenManager;

    public ContributionService(IContributionRepository contributionRepository, JwtTokenManager jwtTokenManager) {
        super(contributionRepository);
        this.contributionRepository = contributionRepository;
        this.jwtTokenManager = jwtTokenManager;
    }
    public boolean saveContribution(SaveContributionRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        Contribution addContribution = IContributionMapper.INSTANCE.fromSaveContributionRequestDtoToContribution(dto);

        if (studentId.isPresent()) {
            addContribution.setStudentId(studentId.get());
            save(addContribution);
            return true;
        }
        else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
    }
    public boolean updateContribution(UpdateContributionRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isPresent()) {
            Contribution contribution = contributionRepository.findByStudentId(studentId.get());
            contribution.setIncorrectCodeOrDisplayMessageNote(dto.getIncorrectCodeOrDisplayMessageNote());
            contribution.setDocumentationForBacklogNote(dto.getDocumentationForBacklogNote());
            contribution.setResearchNote(dto.getResearchNote());
            contribution.setIntraTeamTrainingNote(dto.getIntraTeamTrainingNote());
            update(contribution);
            return true;
        }
        else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
    }
    public GetContributionResponseDto getContribution(String studentId) {
        GetContributionResponseDto giveContributionResponseDto;
        if (studentId != null) {
            if (contributionRepository.findByStudentId(studentId) != null) {
                Contribution contribution = contributionRepository.findByStudentId(studentId);
                giveContributionResponseDto = IContributionMapper.INSTANCE.fromContributionToGetContributionResponseDto(contribution);
            }
            else {
                throw new CardServiceException(ErrorType.CONTRIBUTION_NOT_FOUND);
            }
        }
        else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        return giveContributionResponseDto;
    }
    public Double calculateAndGetTotalScoreContribution(String studentId) {
        if (studentId == null || studentId.isEmpty()) {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        double totalScoreContribution = 0;
        if (contributionRepository.findByStudentId(studentId) != null) {
            Contribution contribution = contributionRepository.findByStudentId(studentId);
            boolean isDocumentationForBacklogNote0 = (contribution.getDocumentationForBacklogNote() == 0);
            if (contribution != null) {
                if (isDocumentationForBacklogNote0) {
                    totalScoreContribution = 0;
                }
                else {
                    totalScoreContribution = (contribution.getIncorrectCodeOrDisplayMessageNote() * 0.05)
                                                + (contribution.getDocumentationForBacklogNote())
                                                + (contribution.getResearchNote() * 0.05)
                                                + (contribution.getIntraTeamTrainingNote() * 0.05);

                    if (totalScoreContribution > 100) {
                        totalScoreContribution = 100;
                    }
                }
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
        return totalScoreContribution;
    }
}
