package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateTechnicalInterviewRequestDto;
import com.bilgeadam.dto.request.SaveTechnicalInterviewRequestDto;
import com.bilgeadam.dto.request.UpdateTechnicalInterviewRequestDto;
import com.bilgeadam.dto.response.GetTechnicalInterviewResponseDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.ITechnicalInterviewMapper;
import com.bilgeadam.repository.ITechnicalInterviewRepository;
import com.bilgeadam.repository.entity.TechnicalInterview;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TechnicalInterviewService extends ServiceManager<TechnicalInterview,String> {
    private final ITechnicalInterviewRepository technicalInterviewRepository;
    private final JwtTokenManager jwtTokenManager;
    private final ITechnicalInterviewMapper iTechnicalInterviewMapper;

public TechnicalInterviewService(ITechnicalInterviewRepository technicalInterviewRepository, JwtTokenManager jwtTokenManager, ITechnicalInterviewMapper iTechnicalInterviewMapper) {
    super(technicalInterviewRepository);
    this.technicalInterviewRepository = technicalInterviewRepository;
    this.jwtTokenManager=jwtTokenManager;
    this.iTechnicalInterviewMapper = iTechnicalInterviewMapper;
}


    public boolean saveTechnicalInterview(SaveTechnicalInterviewRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if(studentId.isPresent()){
            TechnicalInterview technicalInterview = ITechnicalInterviewMapper.INSTANCE.fromSaveTechnicalInterviewRequestDtoToTechnicalInterview(dto);
            technicalInterview.setStudentId(studentId.get());
            technicalInterview.setExempt(dto.isExempt());
            double technicalInterviewAvaragePoint = (dto.getDirectionCorrect()*0.25) + (dto.getCompletionTime()*0.25)+ (dto.getLevelReached()*0.25)+(dto.getSupportTaken()*0.25);
            technicalInterview.setTechnicalInterviewAveragePoint(technicalInterviewAvaragePoint);
            save(technicalInterview);
            return true;
        }
        else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }

    }

    public GetTechnicalInterviewResponseDto getTechnicalInterview(String studentId) {
        GetTechnicalInterviewResponseDto responseDto;
        if(!studentId.equals("")){
            if(technicalInterviewRepository.findAllByStudentId(studentId).size()>0){
                TechnicalInterview technicalInterview = technicalInterviewRepository.findAllByStudentId(studentId).get(0);
                responseDto = ITechnicalInterviewMapper.INSTANCE.fromTechnicalInterviewToGetTechnicalInterviewResponseDto(technicalInterview);
                responseDto.setExempt(technicalInterview.isExempt());
            } else {
                return null;
            }
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        return responseDto;
    }


    public Boolean updateTechnicalInterview(UpdateTechnicalInterviewRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if(studentId.isPresent()){
            TechnicalInterview technicalInterview = technicalInterviewRepository.findAllByStudentId(studentId.get()).get(0);

            technicalInterview.setDirectionCorrect(dto.getDirectionCorrect());
            technicalInterview.setCompletionTime(dto.getCompletionTime());
            technicalInterview.setLevelReached(dto.getLevelReached());
            technicalInterview.setSupportTaken(dto.getSupportTaken());
            technicalInterview.setCompletionTimeComment(dto.getCompletionTimeComment());
            technicalInterview.setLevelReachedComment(dto.getLevelReachedComment());
            technicalInterview.setSupportTakenChoice(dto.getSupportTakenChoice());
            technicalInterview.setComment(dto.getComment());
            technicalInterview.setExempt(dto.isExempt());
            double technicalInterviewAvaragePoint = (dto.getDirectionCorrect()*0.25) + (dto.getCompletionTime()*0.25)+ (dto.getLevelReached()*0.25)+(dto.getSupportTaken()*0.25);
            technicalInterview.setTechnicalInterviewAveragePoint(technicalInterviewAvaragePoint);
            update(technicalInterview);
            return true;
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
    }

    public Boolean deleteTechnicalInterview(String technicalInterviewId) {
        Optional<TechnicalInterview> algorithm = findById(technicalInterviewId);
        if (algorithm.isEmpty())
            throw new CardServiceException(ErrorType.TECHNICAL_INTERVIEW_NOT_FOUND);
        deleteById(technicalInterviewId);
        return true;
    }

    public boolean createTechnicalInterview(CreateTechnicalInterviewRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());

        if (studentId.isPresent()) {
            TechnicalInterview technicalInterview = iTechnicalInterviewMapper.fromCreateTechnicalInterviewRequestDtoToTechnicalInterview(dto);
            technicalInterview.setStudentId(studentId.get());
            double technicalInterviewAvaragePoint = (dto.getDirectionCorrect()*0.25) + (dto.getCompletionTime()*0.25)+ (dto.getLevelReached()*0.25)+(dto.getSupportTaken()*0.25);
            technicalInterview.setTechnicalInterviewAveragePoint(technicalInterviewAvaragePoint);
            save(technicalInterview);

            return true;
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
    }



    public Integer getTechnicalInterviewNumber(String studentId) {
        if (studentId == null || studentId.isEmpty()) {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }

        List<TechnicalInterview> technicalInterviewList = technicalInterviewRepository.findAllByStudentId(studentId);

        if (technicalInterviewList.isEmpty()) {
            return 0;
        } else {
            return 1;
        }
    }

    public Double getTechnicalInterviewAveragePoint(String studentId) {

        if (studentId == null || studentId.isEmpty()) {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        List<TechnicalInterview> technicalInterviewList = technicalInterviewRepository.findAllByStudentId(studentId);
        if (technicalInterviewList.isEmpty()) {
            return null;
        }
        double totalAveragePoint = technicalInterviewList.get(0).getTechnicalInterviewAveragePoint();

        return totalAveragePoint ;
    }
}


