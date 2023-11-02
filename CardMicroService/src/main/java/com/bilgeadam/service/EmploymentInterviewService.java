package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateEmploymentInterviewRequestDto;
import com.bilgeadam.dto.request.UpdateEmploymentInterviewRequestDto;
import com.bilgeadam.dto.response.GetEmploymentInterviewResponseDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.IEmploymentInterviewMapper;
import com.bilgeadam.repository.IEmploymentInterviewRepository;
import com.bilgeadam.repository.entity.EmploymentInterview;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmploymentInterviewService extends ServiceManager<EmploymentInterview, String> {

    private final IEmploymentInterviewRepository iEmploymentInterviewRepository;
    private final JwtTokenManager jwtTokenManager;
    private final IEmploymentInterviewMapper interviewMapper;

    public EmploymentInterviewService(IEmploymentInterviewRepository iEmploymentInterviewRepository, JwtTokenManager jwtTokenManager, IEmploymentInterviewMapper interviewMapper) {
        super(iEmploymentInterviewRepository);
        this.iEmploymentInterviewRepository = iEmploymentInterviewRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.interviewMapper = interviewMapper;
    }

    public MessageResponse createEmploymentInterview(CreateEmploymentInterviewRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        if (dto.getHrInterviewScore() > 100 || dto.getHrInterviewScore() < 0)
            throw new CardServiceException(ErrorType.EMPLOYMENT_INTERVIEW_NUMBER_RANGE);
        if (dto.getTechnicalInterviewScore() > 100 || dto.getTechnicalInterviewScore() < 0)
            throw new CardServiceException(ErrorType.EMPLOYMENT_INTERVIEW_NUMBER_RANGE);
        EmploymentInterview employmentInterview = interviewMapper.toEmploymentInterview(dto);
        double hrInterviewWeight = 0.15;
        double technicalInterviewWeight = 0.20;
        double hrInterviewFinalScore = dto.getHrInterviewScore() * hrInterviewWeight;
        double technicalInterviewFinalScore = dto.getTechnicalInterviewScore() * technicalInterviewWeight;
        employmentInterview.setHrInterviewFinalScore(hrInterviewFinalScore);
        employmentInterview.setTechnicalInterviewFinalScore(technicalInterviewFinalScore);
        employmentInterview.setStudentId(studentId.get());
        save(employmentInterview);
        return new MessageResponse("Mülakatlar başarı ile oluşturuldu.");
    }


    public MessageResponse updateEmploymentInterview(UpdateEmploymentInterviewRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        if (dto.getHrInterviewScore() > 100 || dto.getHrInterviewScore() < 0)
            throw new CardServiceException(ErrorType.EMPLOYMENT_INTERVIEW_NUMBER_RANGE);
        if (dto.getTechnicalInterviewScore() > 100 || dto.getTechnicalInterviewScore() < 0)
            throw new CardServiceException(ErrorType.EMPLOYMENT_INTERVIEW_NUMBER_RANGE);
        double hrInterviewWeight = 0.15;
        double technicalInterviewWeight = 0.20;
        double hrInterviewFinalScore = dto.getHrInterviewScore() * hrInterviewWeight;
        double technicalInterviewFinalScore = dto.getTechnicalInterviewScore() * technicalInterviewWeight;
        EmploymentInterview interview = iEmploymentInterviewRepository.findByStudentId(studentId.get());
        interview.setHrInterviewComment(dto.getHrInterviewComment());
        interview.setTechnicalInterviewComment(dto.getTechnicalInterviewComment());
        interview.setHrInterviewScore(dto.getHrInterviewScore());
        interview.setHrInterviewFinalScore(hrInterviewFinalScore);
        interview.setTechnicalInterviewScore(dto.getTechnicalInterviewScore());
        interview.setTechnicalInterviewFinalScore(technicalInterviewFinalScore);
        update(interview);
        return new MessageResponse("Mülakatlar başarı ile güncellendi.");
    }

    public MessageResponse deleteEmploymentInterview(String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if (studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        iEmploymentInterviewRepository.deleteByStudentId(studentId.get());
        return new MessageResponse("Mülakat başarı ile silinmştir");
    }

    public GetEmploymentInterviewResponseDto getEmploymentInterview(String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if (studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        EmploymentInterview employmentInterview = iEmploymentInterviewRepository.findByStudentId(studentId.get());
        GetEmploymentInterviewResponseDto employmentInterviewResponseDto = interviewMapper.toGetEmploymentInterviewResponseDto(employmentInterview);
        return employmentInterviewResponseDto;
    }

    public Double getEmploymentInterviewAvg(String token){
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if (studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        EmploymentInterview employmentInterview = iEmploymentInterviewRepository.findByStudentId(studentId.get());
        if(employmentInterview != null){
            double avarage = employmentInterview.getTechnicalInterviewFinalScore()+employmentInterview.getHrInterviewFinalScore();
            return avarage;
        }
        return null;
    }
}
