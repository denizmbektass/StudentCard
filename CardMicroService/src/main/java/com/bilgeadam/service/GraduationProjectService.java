package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateGraduationProjectRequestDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.IGraduationProjectMapper;
import com.bilgeadam.repository.IGraduationProject;
import com.bilgeadam.repository.entity.GraduationProject;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class GraduationProjectService extends ServiceManager<GraduationProject,String> {
    private final JwtTokenManager jwtTokenManager;
    private final IGraduationProjectMapper iGraduationProjectMapper;

    public GraduationProjectService(IGraduationProject iGraduationProject, JwtTokenManager jwtTokenManager, IGraduationProjectMapper iGraduationProjectMapper) {
        super(iGraduationProject);
        this.jwtTokenManager = jwtTokenManager;
        this.iGraduationProjectMapper = iGraduationProjectMapper;
    }


    public MessageResponse createGradutainProject(CreateGraduationProjectRequestDto dto) {
        if((dto.getInterestLevel()>100||dto.getInterestLevel()<0) && (dto.getPresentation()>100||dto.getPresentation()<0) && (dto.getMeetingAttendance()>100 ||dto.getMeetingAttendance()<0) && (dto.getRetroScore()>100 ||dto.getRetroScore()<0) && (dto.getTeamworkCompatibility()>100 ||dto.getTeamworkCompatibility()<0))
            throw new CardServiceException(ErrorType.GRADUATION_NUMBER_RANGE);
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if(studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        GraduationProject graduationProject = iGraduationProjectMapper.toGraduationProject(dto);
        graduationProject.setStudentId(studentId.get());
        save(graduationProject);
        return new MessageResponse("Bitirme Projesi başarı ile oluşturuldu.");
    }
}


