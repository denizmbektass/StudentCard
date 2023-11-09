package com.bilgeadam.service;


import com.bilgeadam.dto.request.SaveInternshipTaskRequestDto;
import com.bilgeadam.dto.response.GetInternshipTaskResponseDto;
import com.bilgeadam.dto.response.GetTeamworkResponseDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.IInternshipTaskMapper;
import com.bilgeadam.mapper.ITeamworkMapper;
import com.bilgeadam.repository.IInternshipTasksRepository;
import com.bilgeadam.repository.entity.InternshipTasks;
import com.bilgeadam.repository.entity.Teamwork;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InternshipTasksService extends ServiceManager<InternshipTasks, String> {
    private final IInternshipTasksRepository internshipTasksRepository;
    private final JwtTokenManager jwtTokenManager;
    public InternshipTasksService(IInternshipTasksRepository internshipTasksRepository, JwtTokenManager jwtTokenManager){
        super(internshipTasksRepository);
        this.internshipTasksRepository = internshipTasksRepository;
        this.jwtTokenManager = jwtTokenManager;
    }

    public boolean saveInternshipTask(SaveInternshipTaskRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isPresent()) {
            InternshipTasks internshipTasks = IInternshipTaskMapper.INSTANCE
                    .fromSaveInternshipTaskRequestDtoToTeamwork(dto);
            internshipTasks.setStudentId(studentId.get());
            save(internshipTasks);
            return true;
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
    }

    public Integer getInternshipTaskCount(String studentId) {
        int InternshipTaskCount = Integer.MAX_VALUE;
        InternshipTasks internshipTasks;
        if (!(studentId == null)) {
            internshipTasks = internshipTasksRepository.findByStudentId(studentId);
            if (internshipTasks == null) {
                return 0;
            }
            if (internshipTasks != null) {
                return 1;
            }
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        return InternshipTaskCount;
    }

    public boolean updateInternshipTask(SaveInternshipTaskRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isPresent()) {
            InternshipTasks internshipTasks = internshipTasksRepository.findByStudentId(studentId.get());
            internshipTasks.setBacklogCompletionTime(dto.getBacklogCompletionTime());
            internshipTasks.setCompletedBacklog(dto.getCompletedBacklog());
            update(internshipTasks);
            return true;
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
    }

    public GetInternshipTaskResponseDto getInternshipTask(String studentId) {

        GetInternshipTaskResponseDto responseDto;
        if (!studentId.equals("")) {
            if (internshipTasksRepository.findByStudentId(studentId) != null) {
                InternshipTasks internshipTasks = internshipTasksRepository.findByStudentId(studentId);
                responseDto = IInternshipTaskMapper.INSTANCE
                        .getInternshipTaskResponseDtofromInternshipTasks(internshipTasks);
            } else {
                throw new CardServiceException(ErrorType.TEAMWORK_NOT_FOUND);
            }
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        return responseDto;
    }

    public Double getInternShipTaskSuccessPoint(String studentId) {
        if (studentId == null || studentId.isEmpty()) {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        double InternshipTaskSuccessPoint = 0;
        if (internshipTasksRepository.findByStudentId(studentId) != null) {
            InternshipTasks internshipTasks = internshipTasksRepository.findByStudentId(studentId);
            boolean isBacklogCompletionTime0 = (internshipTasks.getBacklogCompletionTime() == 0);
            boolean isCompletedBacklog0 = (internshipTasks.getCompletedBacklog() == 0);
            if (internshipTasks != null) {
                if (isBacklogCompletionTime0 && isCompletedBacklog0) {
                    InternshipTaskSuccessPoint = 0;
                } else {
                    InternshipTaskSuccessPoint = ((double) internshipTasks.getCompletedBacklog() * 0.8)
                            + ((double) internshipTasks.getBacklogCompletionTime() * 0.2);
                    if (InternshipTaskSuccessPoint > 100) {
                        InternshipTaskSuccessPoint = 100;
                    }
                }
            } else {
                return null;
            }
        }else{
            return null;
        }
        return InternshipTaskSuccessPoint;
    }
}
