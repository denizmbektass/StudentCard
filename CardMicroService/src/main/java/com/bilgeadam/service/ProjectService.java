package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateProjectScoreRequestDto;
import com.bilgeadam.dto.request.CreateProjectTypeRequestDto;
import com.bilgeadam.dto.request.UpdateProjectRequestDto;
import com.bilgeadam.dto.response.CreateProjectScoreResponseDto;
import com.bilgeadam.dto.response.StudentProjectListResponseDto;
import com.bilgeadam.dto.response.UpdateProjectResponseDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.manager.IStudentManager;
import com.bilgeadam.mapper.IProjectMapper;
import com.bilgeadam.repository.IProjectRepository;
import com.bilgeadam.repository.entity.Project;
import com.bilgeadam.repository.entity.ProjectType;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService extends ServiceManager<Project, String> {

    private final IProjectRepository iProjectRepository;
    private final JwtTokenManager jwtTokenManager;
    private final IStudentManager studentManager;
    private final ProjectTypeService projectTypeService;


    public ProjectService(IProjectRepository iProjectRepository, JwtTokenManager jwtTokenManager, IStudentManager studentManager, ProjectTypeService projectTypeService) {
        super(iProjectRepository);
        this.iProjectRepository = iProjectRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.studentManager = studentManager;
        this.projectTypeService = projectTypeService;
    }


    public CreateProjectScoreResponseDto createProjectScore(CreateProjectScoreRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getToken());
        if (studentId.isEmpty()) {
            throw new CardServiceException(ErrorType.USER_NOT_FOUND);
        }
        if (dto.getProjectScore() == null)
            throw new CardServiceException(ErrorType.PROJECT_POINT_EMPTY);
        if (dto.getDescription().isBlank())
            throw new CardServiceException(ErrorType.DESCRIPTION_EMPTY);
        if (dto.getProjectScore() == null)
            throw new CardServiceException(ErrorType.PROJECT_POINT_EMPTY);
        if (dto.getProjectType().toString().isEmpty())
            throw new CardServiceException(ErrorType.PROJECT_TYPE_EMPTY);
        Project project = IProjectMapper.INSTANCE.toProject(dto);
        project.setStudentId(studentId.get());
        save(project);
        return IProjectMapper.INSTANCE.toCreateProjectScoreResponseDto(project);
    }


    public List<StudentProjectListResponseDto> showStudentProjectList(String studentToken) {
        String studentId = jwtTokenManager.getIdFromToken(studentToken).orElseThrow(() -> {
            throw new CardServiceException(ErrorType.USER_NOT_FOUND);
        });
        String studentNameAndSurname = studentManager.getNameAndSurnameWithStudentId(studentId).getBody();
        List<Project> projectList = iProjectRepository.findAllByStudentId(studentId);
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

    public Integer getProjectNote(String studentId) {
        return (int) Math.floor(iProjectRepository.findAllByStudentId(studentId).stream()
                .mapToLong(x -> x.getProjectScore()).average().orElse(0));
    }

    public Boolean deleteStudentProject(String projectId) {
        Project project = findById(projectId).orElseThrow(() -> {
            throw new CardServiceException(ErrorType.PROJECT_NOT_FOUND);
        });
        project.setStatus(EStatus.DELETED);
        update(project);
        return true;
    }

    public UpdateProjectResponseDto updateStudentProject(UpdateProjectRequestDto dto) {
        Optional<Project> project = findById(dto.getProjectId());
        if (project.isEmpty()) {
            throw new CardServiceException(ErrorType.PROJECT_NOT_FOUND);
        }
        if (dto.getProjectScore() == null)
            throw new CardServiceException(ErrorType.PROJECT_POINT_EMPTY);
        if (dto.getDescription().isBlank())
            throw new CardServiceException(ErrorType.DESCRIPTION_EMPTY);


        update(IProjectMapper.INSTANCE.updateProjectRequestDtoTOProject(dto, project.get()));
        UpdateProjectResponseDto updatedProject = IProjectMapper.INSTANCE.toUpdateProjectResponseDto(project.get());
        return updatedProject;
    }

    public String createProjectType(CreateProjectTypeRequestDto dto) {
        ProjectType projectType = projectTypeService.createProjectType(dto);
        return projectType.getProjectType();
    }

    public List<String> showProjectsType() {
        return projectTypeService.showProjectsType();
    }

    public Boolean deleteProjectType(String projectTypeId) {
        Boolean isDeleted = projectTypeService.deleteProjectType(projectTypeId);
        if (!isDeleted) {
            return false;
        }
        return true;
    }

}
