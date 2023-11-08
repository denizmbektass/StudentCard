package com.bilgeadam.service;

import com.bilgeadam.dto.request.SaveTeamworkRequestDto;
import com.bilgeadam.dto.request.UpdateTeamworkRequestDto;
import com.bilgeadam.dto.response.GetTeamworkResponseDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.ITeamworkMapper;
import com.bilgeadam.repository.ITeamworkRepository;
import com.bilgeadam.repository.entity.Teamwork;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamworkService extends ServiceManager<Teamwork, String> {
    private final ITeamworkRepository teamworkRepository;
    private final JwtTokenManager jwtTokenManager;

    public TeamworkService(ITeamworkRepository teamworkRepository, JwtTokenManager jwtTokenManager) {
        super(teamworkRepository);
        this.teamworkRepository = teamworkRepository;
        this.jwtTokenManager = jwtTokenManager;
    }

    public Integer getTeamworkCount(String studentId) {
        int teamworkCount = Integer.MAX_VALUE;
        Teamwork teamwork;
        if (!(studentId == null)) {
            teamwork = teamworkRepository.findByStudentId(studentId);
            if (teamwork == null) {
                return 0;
            }
            if (teamwork != null) {
                return 1;
            }
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        return teamworkCount;
    }

    public boolean saveTeamwork(SaveTeamworkRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isPresent()) {
            Teamwork newTeamwork = ITeamworkMapper.INSTANCE
                    .fromSaveTeamworkRequestDtoToTeamwork(dto);
            newTeamwork.setStudentId(studentId.get());
            save(newTeamwork);
            return true;
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
    }

    public boolean updateTeamwork(UpdateTeamworkRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isPresent()) {
            Teamwork teamwork = teamworkRepository.findByStudentId(studentId.get());
            teamwork.setCommunicationWithinTeamPoint(dto.getCommunicationWithinTeamPoint());
            teamwork.setAskingForHelpPoint(dto.getAskingForHelpPoint());
            teamwork.setHelpingTeamMembersPoint(dto.getHelpingTeamMembersPoint());
            teamwork.setCommunicationWithTeamLeadersPoint(dto.getCommunicationWithTeamLeadersPoint());
            teamwork.setProjectPresentationPoint(dto.getProjectPresentationPoint());
            teamwork.setProjectOwnerMeetingActivityPoint(dto.getProjectOwnerMeetingActivityPoint());
            update(teamwork);
            return true;
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
    }

    public GetTeamworkResponseDto getTeamwork(String studentId) {
        GetTeamworkResponseDto responseDto;
        if (!studentId.equals("")) {
            if (teamworkRepository.findByStudentId(studentId) != null) {
                Teamwork teamwork = teamworkRepository.findByStudentId(studentId);
                responseDto = ITeamworkMapper.INSTANCE
                        .fromTeamworkToGetTeamworkResponseDto(teamwork);
            } else {
                throw new CardServiceException(ErrorType.TEAMWORK_NOT_FOUND);
            }
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        return responseDto;
    }

    public Double getTeamworkSuccessPoint(String studentId) {
        if (studentId == null || studentId.isEmpty()) {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        double teamworkSuccessPoint = 0.0;
        if (teamworkRepository.findByStudentId(studentId) != null) {
            Teamwork teamwork = teamworkRepository.findByStudentId(studentId);
            boolean isCommunicationWithinTeamPoint0 = (teamwork.getCommunicationWithinTeamPoint() == 0);
            boolean isCommunicationWithTeamLeadersPoint0 = (teamwork.getCommunicationWithTeamLeadersPoint() == 0);
            if (teamwork != null) {
                if (isCommunicationWithinTeamPoint0 && isCommunicationWithTeamLeadersPoint0) {
                    teamworkSuccessPoint = 0;
                } else {
                    teamworkSuccessPoint = ((double) teamwork.getCommunicationWithinTeamPoint() * 0.5)
                            + ((double) teamwork.getAskingForHelpPoint() * 0.05)
                            + ((double) teamwork.getHelpingTeamMembersPoint() * 0.05)
                            + ((double) teamwork.getCommunicationWithTeamLeadersPoint() * 0.5)
                            + ((double) teamwork.getProjectPresentationPoint() * 0.05)
                            + ((double) teamwork.getProjectOwnerMeetingActivityPoint() * 0.05);
                    if (teamworkSuccessPoint > 100) {
                        teamworkSuccessPoint = 100;
                    }
                }
            } else {
                return null;
            }
        }else{
            return null;
        }
        return teamworkSuccessPoint;
    }
}
