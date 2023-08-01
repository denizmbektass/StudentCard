package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateProjectScoreRequestDto;
import com.bilgeadam.dto.response.CreateProjectScoreResponseDto;
import com.bilgeadam.dto.response.StudentProjectListResponseDto;
import com.bilgeadam.manager.IUserManager;
import com.bilgeadam.mapper.IProjectMapper;
import com.bilgeadam.repository.IProjectRepository;
import com.bilgeadam.repository.entity.Project;
import com.bilgeadam.repository.enums.EProjectType;
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
        List<StudentProjectListResponseDto> studentProjectListResponseDtoList = projectList.stream().map(project ->
            StudentProjectListResponseDto.builder()
                    .projectScore(project.getProjectScore())
                    .projectType(project.getProjectType())
                    .description(project.getDescription())
                    .studentNameAndSurname(studentNameAndSurname)
                    .build()
        ).collect(Collectors.toList());
        return studentProjectListResponseDtoList;
    }
}
