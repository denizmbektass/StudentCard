package com.bilgeadam.service;

import com.bilgeadam.dto.request.AddAbsenceRequestDto;
import com.bilgeadam.dto.response.ShowUserAbsenceInformationResponseDto;
import com.bilgeadam.exceptions.AbsenceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.IAbsenceMapper;
import com.bilgeadam.repository.IAbsenceRepository;
import com.bilgeadam.repository.entity.Absence;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AbsenceService extends ServiceManager<Absence,String> {
    private final IAbsenceRepository absenceRepository;
    private final JwtTokenManager jwtTokenManager;

    public AbsenceService(IAbsenceRepository absenceRepository,
                          JwtTokenManager jwtTokenManager) {
        super(absenceRepository);
        this.absenceRepository = absenceRepository;
        this.jwtTokenManager = jwtTokenManager;
    }

    public Boolean save(AddAbsenceRequestDto dto){
        save(IAbsenceMapper.INSTANCE.fromAddAbsenceRequestDtoToAbsence(dto));
        return true;
    }

    public ShowUserAbsenceInformationResponseDto showUserAbsenceInformation(String token){
        String userId = jwtTokenManager.getIdFromToken(token).orElseThrow(()->{
            throw new AbsenceException(ErrorType.USER_NOT_EXIST);
        });
        List<Absence> absenceList = absenceRepository.findByUserId(userId);
        if(absenceList.isEmpty())
            throw new AbsenceException(ErrorType.ABSENCE_NOT_FOUND);
        byte sumOfAbsencePercentageGroup1 = 0;
        byte sumOfAbsencePercentageGroup2 = 0;
        for(Absence absence : absenceList){
            if(absence.getGroup().equals("Group1")){
                sumOfAbsencePercentageGroup1 += absence.getHourOfAbsence();
            }else if(absence.getGroup().equals("Group2")){
                sumOfAbsencePercentageGroup2 += absence.getHourOfAbsence();
            }
        }
        double absencePercentageGroup1 = 100 -((sumOfAbsencePercentageGroup1)*100 / absenceList.get(0).getHourOfAbsenceLimit()/2);
        double absencePercentageGroup2 = 100 -((sumOfAbsencePercentageGroup2)*100 / absenceList.get(0).getHourOfAbsenceLimit()/2);
        System.out.println("Group1: " + absencePercentageGroup1);
        System.out.println("Group2: " + absencePercentageGroup2);
        return ShowUserAbsenceInformationResponseDto.builder()
                .group1Percentage(absencePercentageGroup1)
                .group2Percentage(absencePercentageGroup2)
                .build();
    }

}
