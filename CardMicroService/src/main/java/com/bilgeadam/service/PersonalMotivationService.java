package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreatePersonalMotivationRequestDto;
import com.bilgeadam.dto.request.UpdatePersonalMotivationRequestDto;
import com.bilgeadam.dto.request.UpdateProjectBehaviorRequestDto;
import com.bilgeadam.dto.response.GetPersonalMotivationResponseDto;
import com.bilgeadam.dto.response.GetProjectBehaviorResponseDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.IPersonalMotivationMapper;
import com.bilgeadam.repository.IPersonalMotivationRepository;
import com.bilgeadam.repository.entity.PersonalMotivation;
import com.bilgeadam.repository.entity.ProjectBehavior;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonalMotivationService extends ServiceManager<PersonalMotivation, String > {

    private final IPersonalMotivationRepository repository;
    private final JwtTokenManager jwtTokenManager;
    private final IPersonalMotivationMapper personalMotivationMapper;
    public PersonalMotivationService(IPersonalMotivationRepository repository, JwtTokenManager jwtTokenManager, IPersonalMotivationMapper personalMotivationMapper){
        super(repository);
        this.repository=repository;
        this.jwtTokenManager=jwtTokenManager;
        this.personalMotivationMapper=personalMotivationMapper;
    }

    public MessageResponse createPersonalMotivation(CreatePersonalMotivationRequestDto dto){
        Optional<String> studentId= jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isEmpty()){
            throw new CardServiceException(ErrorType.STUDENT_NOT_FOUND);
        }
        if(dto.getCamera()==null)
            throw new CardServiceException(ErrorType.PERSONAL_MOTIVATION_POINT_EMPTY);
        if(dto.getBacklog()==null)
            throw new CardServiceException(ErrorType.PERSONAL_MOTIVATION_POINT_EMPTY);
        if (dto.getClothes()==null)
            throw new CardServiceException(ErrorType.PERSONAL_MOTIVATION_POINT_EMPTY);
        if (dto.getParticipation()==null)
            throw new CardServiceException(ErrorType.PERSONAL_MOTIVATION_POINT_EMPTY);
        if (dto.getWorkingEnvironment()==null)
            throw new CardServiceException(ErrorType.PERSONAL_MOTIVATION_POINT_EMPTY);
        if((dto.getCamera()>100 || dto.getCamera()<0) && (dto.getBacklog()>100 || dto.getBacklog()<0) &&
                (dto.getClothes()>100 || dto.getClothes()<0) && (dto.getParticipation()>100 || dto.getParticipation()<0)
                && (dto.getWorkingEnvironment()>100 || dto.getWorkingEnvironment()<0))
            throw new CardServiceException(ErrorType.PERSONAL_MOTIVATION_NUMBER_RANGE);
        if ((dto.getCameraPercentage()+dto.getBacklogPercentage()+dto.getClothesPercentage()+dto.getParticipationPercentage()+
                dto.getWorkingEnvironmentPercentage()!=100))
            throw new CardServiceException(ErrorType.TOTAL_PERCENTAGE);
        double average=((dto.getCamera()*dto.getCameraPercentage())+(dto.getBacklog()*dto.getBacklogPercentage())+
                (dto.getClothes()*dto.getClothesPercentage())+ (dto.getParticipation()*dto.getParticipationPercentage())+
                (dto.getWorkingEnvironment()*dto.getWorkingEnvironmentPercentage()))/100;
        PersonalMotivation personalMotivation= IPersonalMotivationMapper.INSTANCE.toPersonalMotivation(dto);
        personalMotivation.setAverage(average);
        personalMotivation.setStudentId(studentId.get());
        save(personalMotivation);
        return new MessageResponse("Puan bilgileri başarı ile kaydedildi");
    }

    public MessageResponse updatePersonalMotivation(UpdatePersonalMotivationRequestDto dto){
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if(studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        if((dto.getCamera()>100 || dto.getCamera()<0) && (dto.getBacklog()>100 || dto.getBacklog()<0) &&
                (dto.getClothes()>100 || dto.getClothes()<0) && (dto.getParticipation()>100 || dto.getParticipation()<0)
                && (dto.getWorkingEnvironment()>100 || dto.getWorkingEnvironment()<0))
            throw new CardServiceException(ErrorType.PERSONAL_MOTIVATION_NUMBER_RANGE);
        if ((dto.getCameraPercentage()+dto.getBacklogPercentage()+dto.getClothesPercentage()+dto.getParticipationPercentage()+
                dto.getWorkingEnvironmentPercentage()!=100))
            throw new CardServiceException(ErrorType.TOTAL_PERCENTAGE);
        double average=((dto.getCamera()*dto.getCameraPercentage())+(dto.getBacklog()*dto.getBacklogPercentage())+
                (dto.getClothes()*dto.getClothesPercentage())+ (dto.getParticipation()*dto.getParticipationPercentage())+
                (dto.getWorkingEnvironment()*dto.getWorkingEnvironmentPercentage()))/100;
        PersonalMotivation personalMotivation= repository.findByStudentId(studentId.get());
        personalMotivation.setCamera(dto.getCamera());
        personalMotivation.setBacklog(dto.getBacklog());
        personalMotivation.setClothes(dto.getClothes());
        personalMotivation.setParticipation(dto.getParticipation());
        personalMotivation.setWorkingEnvironment(dto.getWorkingEnvironment());
        personalMotivation.setCameraPercentage(dto.getCameraPercentage());
        personalMotivation.setBacklogPercentage(dto.getBacklogPercentage());
        personalMotivation.setClothesPercentage(dto.getClothesPercentage());
        personalMotivation.setParticipationPercentage(dto.getParticipationPercentage());
        personalMotivation.setWorkingEnvironmentPercentage(dto.getWorkingEnvironmentPercentage());
        personalMotivation.setAverage(average);
        update(personalMotivation);
        return new MessageResponse("Puanlar başarı ile güncellendi");
    }
    public Boolean deletePersonalMotivation(String token){
        Optional<String> studentId= jwtTokenManager.getIdFromToken(token);
        if(studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        repository.deleteByStudentId(studentId.get());
        return true;
    }

    public GetPersonalMotivationResponseDto findPersonalMotivation (String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if(studentId.isEmpty())
        { throw new CardServiceException(ErrorType.INVALID_TOKEN);}
        PersonalMotivation personalMotivation=repository.findByStudentId(studentId.get());
        GetPersonalMotivationResponseDto  getPersonalMotivationResponseDto= personalMotivationMapper.toPersonalMotivation(personalMotivation);
        return getPersonalMotivationResponseDto;
    }
}
