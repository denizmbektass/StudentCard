package com.bilgeadam.service;


import com.bilgeadam.dto.request.CreatProjectBehaviorScoreRequestDto;
import com.bilgeadam.dto.request.UpdateProjectBehaviorRequestDto;

import com.bilgeadam.dto.response.CreateProjectBehaviorScoreResponseDto;
import com.bilgeadam.dto.response.GetProjectBehaviorResponseDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.IProjectBehaviorMapper;
import com.bilgeadam.repository.IProjectBehaviorRepository;
import com.bilgeadam.repository.entity.ProjectBehavior;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectBehaviorService extends ServiceManager<ProjectBehavior, String> {

    private final IProjectBehaviorRepository iProjectBehaviorRepository;
    private final JwtTokenManager jwtTokenManager;
    private final IProjectBehaviorMapper projectBehaviorMapper;
    public ProjectBehaviorService(IProjectBehaviorRepository iProjectBehaviorRepository, JwtTokenManager jwtTokenManager, IProjectBehaviorMapper projectBehaviorMapper) {
        super(iProjectBehaviorRepository);
        this.iProjectBehaviorRepository=iProjectBehaviorRepository;
        this.jwtTokenManager=jwtTokenManager;
        this.projectBehaviorMapper=projectBehaviorMapper;

    }

    public CreateProjectBehaviorScoreResponseDto createProjectBehaviorScore(CreatProjectBehaviorScoreRequestDto dto){
        Optional<String> studentId=jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isEmpty()){
            throw new CardServiceException(ErrorType.STUDENT_NOT_FOUND);
        }
        if(dto.getRapportScore()==null)
            throw new CardServiceException(ErrorType.PERSONAL_MOTIVATION_POINT_EMPTY);
        if(dto.getPresentationScore()==null)
            throw new CardServiceException(ErrorType.BEHAVIOR_POINT_EMPTY);
        if (dto.getInterestScore()==null)
            throw new CardServiceException(ErrorType.BEHAVIOR_POINT_EMPTY);
        if (dto.getRetroScore()==null)
            throw new CardServiceException(ErrorType.BEHAVIOR_POINT_EMPTY);
        if((dto.getRapportScore()>100 || dto.getRapportScore()<0) && (dto.getPresentationScore()>100 || dto.getPresentationScore()<0) &&
                (dto.getInterestScore()>100 || dto.getInterestScore()<0) && (dto.getRetroScore()>100 || dto.getRetroScore()<0))
            throw new CardServiceException(ErrorType.BEHAVIOR_NUMBER_RANGE);
        if ((dto.getRapportScorePercentage()+dto.getPresentationScorePercentage()+dto.getInterestScorePercentage()+dto.getRetroScorePercentage() !=100))
            throw new CardServiceException(ErrorType.TOTAL_PERCENTAGE);
        double averageScore=((dto.getRapportScore()*dto.getRapportScorePercentage())+(dto.getPresentationScore()*dto.getPresentationScorePercentage())+
                (dto.getInterestScore()*dto.getInterestScorePercentage())+ (dto.getRetroScore()*dto.getRetroScorePercentage()))/100;
        ProjectBehavior projectBehavior= IProjectBehaviorMapper.INSTANCE.toProjectBehavior(dto);
        projectBehavior.setAverageScore(averageScore);
        projectBehavior.setStudentId(studentId.get());
        save(projectBehavior);
        return IProjectBehaviorMapper.INSTANCE.createProjectBehaviorScoreResponseDto(projectBehavior);

    }
    public MessageResponse updateProjectBehavior(UpdateProjectBehaviorRequestDto dto){
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if(studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        if((dto.getRapportScore()>100 || dto.getRapportScore()<0) && (dto.getPresentationScore()>100 || dto.getPresentationScore()<0) &&
                (dto.getInterestScore()>100 || dto.getInterestScore()<0) && (dto.getRetroScore()>100 || dto.getRetroScore()<0))
            throw new CardServiceException(ErrorType.PERSONAL_MOTIVATION_NUMBER_RANGE);
        if ((dto.getRapportScorePercentage()+dto.getPresentationScorePercentage()+dto.getInterestScorePercentage()+dto.getRetroScorePercentage() !=100))
            throw new CardServiceException(ErrorType.TOTAL_PERCENTAGE);
        double averageScore=((dto.getRapportScore()*dto.getRapportScorePercentage())+(dto.getPresentationScore()*dto.getPresentationScorePercentage())+
                (dto.getInterestScore()*dto.getInterestScorePercentage())+ (dto.getRetroScore()*dto.getRetroScorePercentage()))/100;
        ProjectBehavior projectBehavior= iProjectBehaviorRepository.findByStudentId(studentId.get());
        projectBehavior.setRapportScore(dto.getRapportScore());
        projectBehavior.setPresentationScore(dto.getPresentationScore());
        projectBehavior.setInterestScore(dto.getInterestScore());
        projectBehavior.setRetroScore(dto.getRetroScore());
        projectBehavior.setRapportScorePercentage(dto.getRapportScorePercentage());
        projectBehavior.setPresentationScorePercentage(dto.getPresentationScorePercentage());
        projectBehavior.setInterestScorePercentage(dto.getInterestScorePercentage());
        projectBehavior.setRetroScorePercentage(dto.getRetroScorePercentage());
        projectBehavior.setAverageScore(averageScore);
        update(projectBehavior);
        return new MessageResponse("Puanlar başarı ile güncellendi");
    }

    public Boolean deleteProjectBehavior(String token){
        Optional<String> studentId= jwtTokenManager.getIdFromToken(token);
        if(studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        iProjectBehaviorRepository.deleteByStudentId(studentId.get());
        return true;
    }

    public GetProjectBehaviorResponseDto findProjectBehavior (String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if(studentId.isEmpty())
        { throw new CardServiceException(ErrorType.INVALID_TOKEN);}
        ProjectBehavior project=iProjectBehaviorRepository.findByStudentId(studentId.get());
        GetProjectBehaviorResponseDto  getProjectBehaviorResponseDto= projectBehaviorMapper.toProjectBehavior(project);
        return getProjectBehaviorResponseDto;
    }

}
