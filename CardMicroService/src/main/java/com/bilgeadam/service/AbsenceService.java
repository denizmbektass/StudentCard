package com.bilgeadam.service;

import com.bilgeadam.dto.request.AddAbsenceRequestDto;
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

    public Double showUserAbsenceInformation(String userId){
        List<Absence> absenceList = absenceRepository.findByUserId(userId);
        if(absenceList.isEmpty())
            throw new AbsenceException(ErrorType.ABSENCE_NOT_FOUND);
        byte sumOfAbsenceHours = 0;
        for(Absence absence : absenceList){
            sumOfAbsenceHours += absence.getHourOfAbsence();
        }
        System.out.println(sumOfAbsenceHours);
        double absenceHour = 100 -((sumOfAbsenceHours)*100/absenceList.get(0).getHourOfAbsenceLimit());
        return absenceHour;
    }

}
