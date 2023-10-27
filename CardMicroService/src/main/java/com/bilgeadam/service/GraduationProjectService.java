package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateGraduationProjectRequestDto;
import com.bilgeadam.dto.request.UpdateGraduationProjectRequestDto;
import com.bilgeadam.dto.response.GetGraduationProjectResponseDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.IGraduationProjectMapper;
import com.bilgeadam.repository.IGraduationProject;
import com.bilgeadam.repository.entity.GraduationProject;
import com.bilgeadam.repository.entity.Project;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class GraduationProjectService extends ServiceManager<GraduationProject,String> {
    private final JwtTokenManager jwtTokenManager;
    private final IGraduationProjectMapper iGraduationProjectMapper;
    private final IGraduationProject iGraduationProject;

    public GraduationProjectService(IGraduationProject iGraduationProject, JwtTokenManager jwtTokenManager, IGraduationProjectMapper iGraduationProjectMapper) {
        super(iGraduationProject);
        this.jwtTokenManager = jwtTokenManager;
        this.iGraduationProjectMapper = iGraduationProjectMapper;
        this.iGraduationProject = iGraduationProject;
    }


    public MessageResponse createGradutainProject( CreateGraduationProjectRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if(studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        if (dto.getMeetingAttendance() > 100 || dto.getMeetingAttendance() < 0) {
            throw new CardServiceException(ErrorType.GRADUATION_NUMBER_RANGE);
        }
        if (dto.getRetroScore() > 100 || dto.getRetroScore() < 0) {
            throw new CardServiceException(ErrorType.GRADUATION_NUMBER_RANGE);
        }
        if (dto.getTeamworkCompatibility() > 100 || dto.getTeamworkCompatibility() < 0) {
            throw new CardServiceException(ErrorType.GRADUATION_NUMBER_RANGE);
        }
        if (dto.getNumberOfCompletedTasksPercentage() > 100 || dto.getNumberOfCompletedTasksPercentage() < 0) {
            throw new CardServiceException(ErrorType.GRADUATION_NUMBER_RANGE);
        }
        if (dto.getInterestLevel() > 100 || dto.getInterestLevel() < 0) {
            throw new CardServiceException(ErrorType.GRADUATION_NUMBER_RANGE);
        }
        if (dto.getPresentation() > 100 || dto.getPresentation() < 0) {
            throw new CardServiceException(ErrorType.GRADUATION_NUMBER_RANGE);
        }
        if((dto.getInterestLevelPercentage()+ dto.getMeetingAttendancePercentage()+
                dto.getNumberOfCompletedTasksPercentage()+ dto.getPresentationPercentage()+
                dto.getTeamworkCompatibilityPercentage()+dto.getRetroScorePercentage()) != 100)
            throw new CardServiceException(ErrorType.TOTAL_PERCENTAGE);
        double averageScore = ((dto.getRetroScore() * dto.getRetroScorePercentage())+
                (dto.getMeetingAttendance() * dto.getMeetingAttendancePercentage())+
                (dto.getInterestLevel() * dto.getInterestLevelPercentage())+
                (dto.getTeamworkCompatibility() * dto.getTeamworkCompatibilityPercentage())+
                (dto.getNumberOfCompletedTasks() * dto.getNumberOfCompletedTasksPercentage())+
                (dto.getPresentation()* dto.getPresentationPercentage()))/100 ;
        GraduationProject graduationProject = iGraduationProjectMapper.toGraduationProject(dto);
        graduationProject.setAverageScore(averageScore);
        graduationProject.setStudentId(studentId.get());
        save(graduationProject);
        return new MessageResponse("Bitirme Projesi başarı ile kaydedildi.");
    }

    public GetGraduationProjectResponseDto findGraduationProject(String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if(studentId.isEmpty())
        { throw new CardServiceException(ErrorType.INVALID_TOKEN);}
        GraduationProject project=iGraduationProject.findByStudentId(studentId.get());
        GetGraduationProjectResponseDto getGraduationProjectResponseDto = iGraduationProjectMapper.toGraduationProject(project);
        return getGraduationProjectResponseDto;
    }

    public MessageResponse updateProject(UpdateGraduationProjectRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if(studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        if (dto.getMeetingAttendance() > 100 || dto.getMeetingAttendance() < 0) {
            throw new CardServiceException(ErrorType.GRADUATION_NUMBER_RANGE);
        }
        if (dto.getRetroScore() > 100 || dto.getRetroScore() < 0) {
            throw new CardServiceException(ErrorType.GRADUATION_NUMBER_RANGE);
        }
        if (dto.getTeamworkCompatibility() > 100 || dto.getTeamworkCompatibility() < 0) {
            throw new CardServiceException(ErrorType.GRADUATION_NUMBER_RANGE);
        }
        if (dto.getNumberOfCompletedTasksPercentage() > 100 || dto.getNumberOfCompletedTasksPercentage() < 0) {
            throw new CardServiceException(ErrorType.GRADUATION_NUMBER_RANGE);
        }
        if (dto.getInterestLevel() > 100 || dto.getInterestLevel() < 0) {
            throw new CardServiceException(ErrorType.GRADUATION_NUMBER_RANGE);
        }
        if (dto.getPresentation() > 100 || dto.getPresentation() < 0) {
            throw new CardServiceException(ErrorType.GRADUATION_NUMBER_RANGE);
        }
        if((dto.getInterestLevelPercentage()+ dto.getMeetingAttendancePercentage()+
                dto.getNumberOfCompletedTasksPercentage()+ dto.getPresentationPercentage()+
                dto.getTeamworkCompatibilityPercentage()+dto.getRetroScorePercentage()) != 100)
            throw new CardServiceException(ErrorType.TOTAL_PERCENTAGE);
        double averageScore = ((dto.getRetroScore() * dto.getRetroScorePercentage())+
                (dto.getMeetingAttendance() * dto.getMeetingAttendancePercentage())+
                (dto.getInterestLevel() * dto.getInterestLevelPercentage())+
                (dto.getTeamworkCompatibility() * dto.getTeamworkCompatibilityPercentage())+
                (dto.getNumberOfCompletedTasks() * dto.getNumberOfCompletedTasksPercentage())+
                (dto.getPresentation()* dto.getPresentationPercentage()))/100 ;
        GraduationProject graduationProject = iGraduationProject.findByStudentId(studentId.get());
        graduationProject.setAverageScore(averageScore);
        graduationProject.setInterestLevel(dto.getInterestLevel());
        graduationProject.setPresentation(dto.getPresentation());
        graduationProject.setMeetingAttendance(dto.getMeetingAttendance());
        graduationProject.setNumberOfCompletedTasks(dto.getNumberOfCompletedTasks());
        graduationProject.setTeamworkCompatibility(dto.getTeamworkCompatibility());
        graduationProject.setRetroScore(dto.getRetroScore());
        graduationProject.setInterestLevelPercentage(dto.getInterestLevelPercentage());
        graduationProject.setPresentationPercentage(dto.getPresentationPercentage());
        graduationProject.setMeetingAttendancePercentage(dto.getMeetingAttendancePercentage());
        graduationProject.setNumberOfCompletedTasksPercentage(dto.getNumberOfCompletedTasksPercentage());
        graduationProject.setTeamworkCompatibilityPercentage(dto.getTeamworkCompatibilityPercentage());
        graduationProject.setRetroScorePercentage(dto.getRetroScorePercentage());
        update(graduationProject);
        return new MessageResponse("Bitirme Projesi başarı ile güncellendi.");
    }

    public MessageResponse deleteProject(String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if(studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        iGraduationProject.deleteByStudentId(studentId.get());
        return new MessageResponse("Bitirme Projesi başarı ile silindi.");
    }
}


