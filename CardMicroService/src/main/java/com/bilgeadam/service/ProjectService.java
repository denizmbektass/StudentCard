package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateProjectScoreRequestDto;
import com.bilgeadam.dto.response.CreateProjectScoreResponseDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.IProjectMapper;
import com.bilgeadam.repository.IProjectRepository;
import com.bilgeadam.repository.entity.Project;
import com.bilgeadam.repository.enums.EProjectType;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.bouncycastle.tsp.TSPUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService extends ServiceManager<Project,String> {

    private final IProjectRepository iProjectRepository;
    private final JwtTokenManager jwtTokenManager;


    public ProjectService(IProjectRepository iProjectRepository, JwtTokenManager jwtTokenManager) {
        super(iProjectRepository);
        this.iProjectRepository = iProjectRepository;
        this.jwtTokenManager = jwtTokenManager;
    }

    public CreateProjectScoreResponseDto createProjectScore(CreateProjectScoreRequestDto dto){
        Optional<String> userId=jwtTokenManager.getIdFromToken(dto.getToken());
        if (userId.isEmpty()){
              throw new RuntimeException("Böyle bir kullanıcı bulunamadı ..");
        }
        Project project = IProjectMapper.INSTANCE.toProject(dto);
        project.setUserId(userId.get());
        save(project);
        return IProjectMapper.INSTANCE.toCreateProjectScoreResponseDto(project);
    }

    public List<EProjectType> showProjectsType(){
        List<EProjectType> result = Arrays.asList(EProjectType.values());
        return result;
    }




}
