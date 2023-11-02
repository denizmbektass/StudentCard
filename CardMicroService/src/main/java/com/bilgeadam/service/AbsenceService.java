package com.bilgeadam.service;

import com.bilgeadam.dto.request.AddAbsenceRequestDto;
import com.bilgeadam.dto.response.ShowUserAbsenceInformationResponseDto;
import com.bilgeadam.exceptions.AbsenceException;
import com.bilgeadam.exceptions.CardServiceException;
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
            throw new CardServiceException(ErrorType.USER_NOT_EXIST);
        });
        List<Absence> absenceList = absenceRepository.findByUserId(userId);
        if(absenceList.isEmpty())
            return null;
        int sumOfAbsenceHoursGroup1 = 0;
        int sumOfAbsenceHoursGroup2 = 0;
        int sumOfTotalCourseHoursGroup1 = 0;
        int sumOfTotalCourseHoursGroup2 = 0;
        for(Absence absence : absenceList){
            if(absence.getGroup().equals("Group1")){
                sumOfAbsenceHoursGroup1 += absence.getHourOfAbsence();
                sumOfTotalCourseHoursGroup1 += absence.getTotalCourseHours();
            }else if(absence.getGroup().equals("Group2")){
                sumOfAbsenceHoursGroup2 += absence.getHourOfAbsence();
                sumOfTotalCourseHoursGroup2 += absence.getTotalCourseHours();
            }
        }
        double absenceSuccessGroup1 = 100 *((double) (sumOfTotalCourseHoursGroup1 - sumOfAbsenceHoursGroup1) / sumOfTotalCourseHoursGroup1);
        double absenceSuccessGroup2 = 100 *((double)(sumOfTotalCourseHoursGroup2 - sumOfAbsenceHoursGroup2) / sumOfTotalCourseHoursGroup2);

        String groupName = absenceList.get(0).getGroupName();
        return ShowUserAbsenceInformationResponseDto.builder()
                .group1Percentage(absenceSuccessGroup1)
                .group2Percentage(absenceSuccessGroup2)
                .groupName(groupName)
                .group1AbsenceNumber(sumOfAbsenceHoursGroup1)
                .group2AbsenceNumber(sumOfAbsenceHoursGroup2)
                .build();
    }

}
