package com.bilgeadam.service;

import com.bilgeadam.dto.request.AssignmentRequestDto;
import com.bilgeadam.dto.response.AssignmentResponseDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.exceptions.AssignmentException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.IAssignmentMapper;
import com.bilgeadam.repository.IAssignmentRepository;
import com.bilgeadam.repository.entity.Assignment;
import com.bilgeadam.repository.enums.AssignmentType;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService extends ServiceManager<Assignment,String> {
    private final IAssignmentRepository assignmentRepository;
    private final IAssignmentMapper assignmentMapper;


    public AssignmentService(IAssignmentRepository assignmentRepository, IAssignmentMapper assignmentMapper) {
        super(assignmentRepository);
        this.assignmentRepository = assignmentRepository;
        this.assignmentMapper = assignmentMapper;
    }

    public MessageResponse createAssignment(AssignmentRequestDto dto){
        if(dto.getScore()>=100||dto.getScore()<=0){
            throw new AssignmentException(ErrorType.BAD_REQUEST,"Ödev notu 100'den büyük veya 0'dan küçük olamaz...");
        }
        Assignment assignment = assignmentMapper.toAssignment(dto);
        assignment.setType(AssignmentType.valueOf(dto.getAssignmentType()));
        save(assignment);
        return new MessageResponse("Ödev başarıyla kaydedildi...");
    }

    public List<AssignmentResponseDto> findAllAssignments() {
        return findAll().stream().map(assignment ->{
            return AssignmentResponseDto.builder().assignmentType(assignment.getType().name())
                    .title(assignment.getTitle())
                    .score(assignment.getScore())
                    .statement(assignment.getStatement())
                    .build();
        }).toList();
    }
}
