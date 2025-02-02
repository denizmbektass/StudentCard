package com.bilgeadam.service;

import com.bilgeadam.dto.request.AddAbsenceRequestDto;
import com.bilgeadam.dto.request.SendAbsenceRequestDto;
import com.bilgeadam.dto.response.ShowStudentAbsenceInformationResponseDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.manager.IBaseManager;
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
    private final IBaseManager baseManager;

    public AbsenceService(IAbsenceRepository absenceRepository,
                          JwtTokenManager jwtTokenManager, IBaseManager baseManager) {
        super(absenceRepository);
        this.absenceRepository = absenceRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.baseManager = baseManager;
    }

    public Boolean save(AddAbsenceRequestDto dto){
        save(IAbsenceMapper.INSTANCE.fromAddAbsenceRequestDtoToAbsence(dto));
        return true;
    }

    /*public ShowStudentAbsenceInformationResponseDto showUserAbsenceInformation(String token){
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
        return ShowStudentAbsenceInformationResponseDto.builder()
                .group1Percentage(absenceSuccessGroup1)
                .group2Percentage(absenceSuccessGroup2)
                .groupName(groupName)
                .group1AbsenceNumber(sumOfAbsenceHoursGroup1)
                .group2AbsenceNumber(sumOfAbsenceHoursGroup2)
                .build();
    }*/

    public ShowStudentAbsenceInformationResponseDto showStudentAbsenceInformation(String token){
        String studentId = jwtTokenManager.getIdFromToken(token).orElseThrow(()->{
            throw new CardServiceException(ErrorType.STUDENT_NOT_EXIST);
        });
        List<Absence> absenceList = absenceRepository.findByStudentId(studentId);
        if(absenceList.isEmpty())
            return null;

        int sumOfAbsenceHoursTheo = 0;
        int sumOfTotalCourseHoursTheo = 0;
        int sumOfAbsenceHoursPrac = 0;
        int sumOfTotalCourseHoursPrac = 0;

        for(Absence absence : absenceList){
            sumOfAbsenceHoursTheo += absence.getHourOfAbsenceTheo();
            sumOfTotalCourseHoursTheo += absence.getTotalCourseHoursTheo();

            sumOfAbsenceHoursPrac += absence.getHourOfAbsencePrac();
            sumOfTotalCourseHoursPrac += absence.getTotalCourseHoursPrac();
        }

        double absenceSuccessTheo = calculateAbsenceSuccess(sumOfAbsenceHoursTheo, sumOfTotalCourseHoursTheo);
        double absenceSuccessPrac = calculateAbsenceSuccess(sumOfAbsenceHoursPrac, sumOfTotalCourseHoursPrac);

        String groupName = absenceList.get(0).getGroupName();
        return ShowStudentAbsenceInformationResponseDto.builder()
                .group1Percentage(absenceSuccessTheo) // Teorik dersler
                .group2Percentage(absenceSuccessPrac) // Pratik dersler
                .groupName(groupName)
                .group1AbsenceNumber(sumOfAbsenceHoursTheo) // Teorik dersler
                .group2AbsenceNumber(sumOfAbsenceHoursPrac) // Pratik dersler
                .build();
    }

    private double calculateAbsenceSuccess(int sumOfAbsenceHours, int sumOfTotalCourseHours){
        return 100 * ((double) (sumOfTotalCourseHours - sumOfAbsenceHours) / sumOfTotalCourseHours);
    }

    public Boolean getAllBaseAbsences(){
        List<SendAbsenceRequestDto> absenceRequestDtos = baseManager.findAllAbsences().getBody();
        if (absenceRequestDtos.isEmpty()){
            throw new CardServiceException(ErrorType.ABSENCE_NOT_FOUND);
        }
        for (SendAbsenceRequestDto dto : absenceRequestDtos){
            Absence absence = IAbsenceMapper.INSTANCE.fromDtoToAbsence(dto);
            absenceRepository.save(absence);
        }
        return true;
    }

}
