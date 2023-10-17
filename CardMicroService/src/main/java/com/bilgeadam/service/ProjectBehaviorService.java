package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreatProjecrBehaviorScoreRequestDto;
import com.bilgeadam.dto.request.UpdateProjectBehaviorRequestDto;
import com.bilgeadam.dto.response.AvarageProjectBehaviorResponseDto;
import com.bilgeadam.dto.response.CreateProjectBehaviorScoreResponseDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.IProjectBehaviorMapper;
import com.bilgeadam.mapper.IProjectMapper;
import com.bilgeadam.repository.IProjectBehaviorRepository;
import com.bilgeadam.repository.entity.ProjectBehavior;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectBehaviorService extends ServiceManager<ProjectBehavior, String> {

    private final IProjectBehaviorRepository iProjectBehaviorRepository;
    private final JwtTokenManager jwtTokenManager;
    public ProjectBehaviorService(IProjectBehaviorRepository iProjectBehaviorRepository, JwtTokenManager jwtTokenManager) {
        super(iProjectBehaviorRepository);
        this.iProjectBehaviorRepository=iProjectBehaviorRepository;
        this.jwtTokenManager=jwtTokenManager;

    }

    public CreateProjectBehaviorScoreResponseDto createProjectBehaviorScore(CreatProjecrBehaviorScoreRequestDto dto){
        Optional<String> studentId=jwtTokenManager.getIdFromToken(dto.getToken());
        if (studentId.isEmpty()){
            throw new CardServiceException(ErrorType.STUDENT_NOT_FOUND);
        }
        if(dto.getRapportScore()==null)
            throw new CardServiceException(ErrorType.BEHAVIOR_POINT_EMPTY);
        if(dto.getPresentationScore()==null)
            throw new CardServiceException(ErrorType.BEHAVIOR_POINT_EMPTY);
        if (dto.getInsterestScore()==null)
            throw new CardServiceException(ErrorType.BEHAVIOR_POINT_EMPTY);
        if (dto.getRetroScore()==null)
            throw new CardServiceException(ErrorType.BEHAVIOR_POINT_EMPTY);
        if((dto.getRapportScore()>100 || dto.getRapportScore()<0) && (dto.getPresentationScore()>100 || dto.getPresentationScore()<0) &&
                (dto.getInsterestScore()>100 || dto.getInsterestScore()<0) && (dto.getRetroScore()>100 || dto.getRetroScore()<0))
            throw new CardServiceException(ErrorType.BEHAVIOR_NUMBER_RANGE);
        ProjectBehavior projectBehavior= IProjectBehaviorMapper.INSTANCE.toProjectBehavior(dto);
        projectBehavior.setStudentId(studentId.get());
        save(projectBehavior);
        return IProjectBehaviorMapper.INSTANCE.createProjectBehaviorScoreResponseDto(projectBehavior);

    }
    public MessageResponse updateProjectBehavior(UpdateProjectBehaviorRequestDto dto){
        Optional<ProjectBehavior> projectBehavior=findById(dto.getProjectBehaviorId());
        if(projectBehavior.isEmpty())
            throw new CardServiceException(ErrorType.PROJECT_NOT_FOUND);
        ProjectBehavior update= projectBehavior.get();
        update.setRapportScore(dto.getRapportScore());
        update.setPresentationScore(dto.getPresentationScore());
        update.setInsterestScore(dto.getInsterestScore());
        update.setRetroScore(dto.getRetroScore());
        update(update);
        return new MessageResponse("Puanlar başarı ile güncellendi");
    }
    public Boolean deleteProjectBehavior(String projectBehaviorId){
        Optional<ProjectBehavior> projectBehavior=findById(projectBehaviorId);
        if(projectBehavior.isEmpty())
            throw new CardServiceException(ErrorType.BEHAVIOR_NOT_FOUND);
        deleteById(projectBehaviorId);
        return true;
    }


}
