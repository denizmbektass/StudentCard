package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateProjectTypeRequestDto;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.exceptions.ProjectException;
import com.bilgeadam.mapper.IProjectTypeMapper;
import com.bilgeadam.repository.IProjectTypeRepository;
import com.bilgeadam.repository.entity.ProjectType;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectTypeService extends ServiceManager<ProjectType, String> {

    private final IProjectTypeRepository iProjectTypeRepository;

    public ProjectTypeService(IProjectTypeRepository iProjectTypeRepository) {
        super(iProjectTypeRepository);
        this.iProjectTypeRepository = iProjectTypeRepository;
    }

    public ProjectType createProjectType(CreateProjectTypeRequestDto dto) {
        Optional<ProjectType> optionalProjectType= iProjectTypeRepository.findByProjectTypeIgnoreCase(dto.getProjectType());
        if (optionalProjectType.isPresent()){
            throw new ProjectException(ErrorType.PROJECT_TYPE_DUBLICATE);
        }
        System.out.println(optionalProjectType);
        return save(IProjectTypeMapper.INSTANCE.toProjectType(dto));
    }

}
