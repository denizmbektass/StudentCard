package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateProjectScoreRequestDto;
import com.bilgeadam.dto.response.CreateProjectScoreResponseDto;
import com.bilgeadam.mapper.IProjectMapper;
import com.bilgeadam.repository.IProjectRepository;
import com.bilgeadam.repository.entity.Project;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class ProjectService extends ServiceManager<Project,String> {

    private final IProjectRepository iProjectRepository;

    public ProjectService(IProjectRepository iProjectRepository) {
        super(iProjectRepository);
        this.iProjectRepository = iProjectRepository;
    }

    public CreateProjectScoreResponseDto createProjectScore(CreateProjectScoreRequestDto dto){
        // TODO: GİRİLECEK OLAN DATALAR FRONTEND'DE KONTROL EDİLECEK
        Project project = IProjectMapper.INSTANCE.toProject(dto);
        save(project);
        return IProjectMapper.INSTANCE.toCreateProjectScoreResponseDto(project);
    }

}
