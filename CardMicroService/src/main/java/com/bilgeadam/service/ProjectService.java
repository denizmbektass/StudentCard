package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateProjectScoreRequestDto;
import com.bilgeadam.dto.request.UpdateProjectRequestDto;
import com.bilgeadam.dto.response.CreateProjectScoreResponseDto;
import com.bilgeadam.dto.response.StudentProjectListResponseDto;
import com.bilgeadam.dto.response.UpdateProjectResponseDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.manager.IUserManager;
import com.bilgeadam.mapper.IProjectMapper;
import com.bilgeadam.repository.IProjectRepository;
import com.bilgeadam.repository.entity.Project;
import com.bilgeadam.repository.enums.EProjectType;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService extends ServiceManager<Project,String> {

    private final IProjectRepository iProjectRepository;
    private final JwtTokenManager jwtTokenManager;
    private final IUserManager userManager;



    public ProjectService(IProjectRepository iProjectRepository, JwtTokenManager jwtTokenManager, IUserManager userManager) {
        super(iProjectRepository);
        this.iProjectRepository = iProjectRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.userManager = userManager;
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


    public List<StudentProjectListResponseDto> showStudentProjectList(String studentToken) {
        String userId = jwtTokenManager.getIdFromToken(studentToken).orElseThrow(()->{throw new RuntimeException("USER NOT FOUND");});
        String studentNameAndSurname = userManager.getNameAndSurnameWithId(userId).getBody();
        List<Project> projectList = iProjectRepository.findAllByUserId(userId);
        List<StudentProjectListResponseDto> studentProjectListResponseDtoList = projectList.stream().filter(project -> project.getStatus() == EStatus.ACTIVE)
                .map(project ->
                        StudentProjectListResponseDto.builder()
                    .projectId(project.getProjectId())
                    .projectScore(project.getProjectScore())
                    .projectType(project.getProjectType())
                    .description(project.getDescription())
                    .studentNameAndSurname(studentNameAndSurname)
                    .build()
        ).collect(Collectors.toList());
        return studentProjectListResponseDtoList;
    }
    public Long getProjectNote(String studentId){
        return (long) Math.floor(iProjectRepository.findAllByUserId(studentId).stream()
                .mapToLong(x->x.getProjectScore()).average().getAsDouble());
    }
    public Boolean deleteStudentProject(String projectId) {
        Project project = findById(projectId).orElseThrow(()->{throw new RuntimeException("Project Not Found");});
        project.setStatus(EStatus.DELETED);
        update(project);
        return true;
    }

    public UpdateProjectResponseDto updateStudentProject(UpdateProjectRequestDto dto){
        Optional<Project> project = findById(dto.getProjectId());
        if (project.isEmpty()){
            throw new RuntimeException("Böyle bir proje bulunamadı");
        }
        update(IProjectMapper.INSTANCE.updateProjectRequestDtoTOProject(dto,project.get()));
        UpdateProjectResponseDto updatedProject= IProjectMapper.INSTANCE.toUpdateProjectResponseDto(project.get());
        return updatedProject;
    }
}
