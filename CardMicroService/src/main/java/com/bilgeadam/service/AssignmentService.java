package com.bilgeadam.service;

import com.bilgeadam.dto.request.AssignmentRequestDto;
import com.bilgeadam.dto.request.FindByStudentIdRequestDto;
import com.bilgeadam.dto.request.UpdateAssignmentRequestDto;
import com.bilgeadam.dto.response.AssignmentResponseDto;
import com.bilgeadam.exceptions.AssignmentException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.IAssignmentMapper;
import com.bilgeadam.repository.IAssignmentRepository;
import com.bilgeadam.repository.entity.Assignment;
import com.bilgeadam.repository.enums.AssignmentType;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService extends ServiceManager<Assignment,String> {
    private final IAssignmentRepository assignmentRepository;
    private final IAssignmentMapper assignmentMapper;
    private final JwtTokenManager jwtTokenManager;

    public AssignmentService(IAssignmentRepository assignmentRepository, IAssignmentMapper assignmentMapper, JwtTokenManager jwtTokenManager) {
        super(assignmentRepository);
        this.assignmentRepository = assignmentRepository;
        this.assignmentMapper = assignmentMapper;
        this.jwtTokenManager = jwtTokenManager;
    }

    public Boolean createAssignment(AssignmentRequestDto dto){
        if(dto.getScore()>100||dto.getScore()<0)
            throw new AssignmentException(ErrorType.BAD_REQUEST,"Ödev notu 100'den büyük veya 0'dan küçük olamaz...");
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if(studentId.isEmpty())
            throw new AssignmentException(ErrorType.INVALID_TOKEN);
        Assignment assignment = assignmentMapper.toAssignment(dto);
        assignment.setStudentId(studentId.get());
        assignment.setType(AssignmentType.valueOf(dto.getAssignmentType()));
        save(assignment);
        return true;
    }

    public List<AssignmentResponseDto> findAllAssignments(FindByStudentIdRequestDto dto) {

        return assignmentRepository.findAllByStudentId(dto.getStudentId()).stream().map(assignment ->{
            return AssignmentResponseDto.builder()
                    .assignmentType(assignment.getType().name())
                    .title(assignment.getTitle())
                    .score(assignment.getScore())
                    .statement(assignment.getStatement())
                    .build();
        }).toList();
    }

    public Boolean updateAssignment(UpdateAssignmentRequestDto dto) {
        Optional<Assignment> assignment = findById(dto.getAssignmentId());
        if (assignment.isEmpty())
            throw new AssignmentException(ErrorType.ASSIGNMENT_NOT_FOUND);
        Assignment toUpdate = assignment.get();
        toUpdate.setTitle(dto.getTitle());
        toUpdate.setType(AssignmentType.valueOf(dto.getType()));
        toUpdate.setScore(dto.getScore());
        toUpdate.setStatement(dto.getStatement());
        update(toUpdate);
        return true;
    }

    public Boolean deleteAssignment(String assignmentId) {
        Optional<Assignment> assignment = findById(assignmentId);
        if (assignment.isEmpty())
            throw new AssignmentException(ErrorType.ASSIGNMENT_NOT_FOUND);
        deleteById(assignmentId);
        return true;
    }
}
