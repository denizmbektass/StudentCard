package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateProjectTypeRequestDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.exceptions.ProjectException;
import com.bilgeadam.mapper.IProjectTypeMapper;
import com.bilgeadam.repository.IProjectTypeRepository;
import com.bilgeadam.repository.entity.ProjectType;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            throw new CardServiceException(ErrorType.PROJECT_TYPE_DUBLICATE);
        }
        return save(IProjectTypeMapper.INSTANCE.toProjectType(dto));
    }

    public Boolean deleteProjectType(String projectType){
        Optional<ProjectType> optionalProjectType = iProjectTypeRepository.findByProjectTypeIgnoreCase(projectType);
        if (optionalProjectType.isEmpty()){
            throw new CardServiceException(ErrorType.PROJECT_TYPE_NOT_FOUND);
        }
        if(!optionalProjectType.get().getStatus().equals(EStatus.ACTIVE)){
            throw new CardServiceException(ErrorType.PROJECT_TYPE_STATUS);
        }
        optionalProjectType.get().setStatus(EStatus.DELETED);
        update(optionalProjectType.get());
        return true;
    }

    public List<String> showProjectsType(){
        List<ProjectType> projectTypeList=iProjectTypeRepository.findAllByStatus(EStatus.ACTIVE);
        List<String> newProjectList=projectTypeList.stream().map(x -> x.getProjectType()).collect(Collectors.toList());
        return newProjectList;
    }



}
