package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateAttendanceRequestDto;
import com.bilgeadam.dto.response.GetAttendanceResponseDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.IAttendanceMapper;
import com.bilgeadam.repository.IAttendanceRepository;
import com.bilgeadam.repository.entity.Attendance;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AttendanceService extends ServiceManager<Attendance,String> {

    private final IAttendanceRepository attendanceRepository;
    private final JwtTokenManager jwtTokenManager;
    private final IAttendanceMapper attendanceMapper;


    public AttendanceService(IAttendanceRepository attendanceRepository, JwtTokenManager jwtTokenManager, IAttendanceMapper attendanceMapper) {
        super(attendanceRepository);
        this.attendanceRepository = attendanceRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.attendanceMapper = attendanceMapper;
    }


    public MessageResponse createAttendanceScore(CreateAttendanceRequestDto dto){
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        if ((dto.getCodeReview() > 100 || dto.getCodeReview() < 0) || (dto.getDailyScrum() > 100 || dto.getDailyScrum() < 0)
            || (dto.getSprintPlaning() > 100 || dto.getSprintPlaning() < 0) || (dto.getSprintReview() > 100 || dto.getSprintReview() < 0)
            || (dto.getSprintRetrospective() > 100 || dto.getSprintRetrospective() < 0))
            throw new CardServiceException(ErrorType.ATTENDANCE_NUMBER_RANGE);
        double attendanceAverage = ((dto.getCodeReview()) + (dto.getSprintReview()) + (dto.getDailyScrum())
                                   + (dto.getSprintPlaning()) + (dto.getSprintRetrospective())) / 5;
        Attendance attendance = attendanceMapper.fromRequestDtoToAttendance(dto);
        attendance.setAttendanceAverage(attendanceAverage);
        attendance.setStudentId(studentId.get());
        attendanceRepository.save(attendance);
        return new MessageResponse("Katılım puanları başarıyla kaydedildi.");
    }

    public MessageResponse updateAttendanceScore(CreateAttendanceRequestDto dto){
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        if ((dto.getCodeReview() > 100 || dto.getCodeReview() < 0) || (dto.getDailyScrum() > 100 || dto.getDailyScrum() < 0)
                || (dto.getSprintPlaning() > 100 || dto.getSprintPlaning() < 0) || (dto.getSprintReview() > 100 || dto.getSprintReview() < 0)
                || (dto.getSprintRetrospective() > 100 || dto.getSprintRetrospective() < 0))
            throw new CardServiceException(ErrorType.ATTENDANCE_NUMBER_RANGE);
        double attendanceAverage = ((dto.getCodeReview()) + (dto.getSprintReview()) + (dto.getDailyScrum())
                + (dto.getSprintPlaning()) + (dto.getSprintRetrospective())) / 5;
        Attendance attendance = attendanceRepository.findByStudentId(studentId.get());
        attendance.setAttendanceAverage(attendanceAverage);
        attendance.setCodeReview(dto.getCodeReview());
        attendance.setDailyScrum(dto.getDailyScrum());
        attendance.setSprintPlaning(dto.getSprintPlaning());
        attendance.setSprintReview(dto.getSprintReview());
        attendance.setSprintRetrospective(dto.getSprintRetrospective());
        attendanceRepository.save(attendance);
        return new MessageResponse("Katılım puanları başarıyla güncellendi.");
    }

    public GetAttendanceResponseDto getAttendanceInfo (String token){
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if (studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        Attendance attendance = attendanceRepository.findByStudentId(studentId.get());
        GetAttendanceResponseDto getAttendanceResponseDto = attendanceMapper.fromAttendanceToResponseDto(attendance);
        return getAttendanceResponseDto;
    }

    public MessageResponse deleteAttendanceScore(String token){
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if (studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        attendanceRepository.deleteByStudentId(studentId.get());
        return new MessageResponse("Katılım puanı başarıyla silindi.");
    }



}
