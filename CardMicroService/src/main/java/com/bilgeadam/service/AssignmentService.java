package com.bilgeadam.service;

import com.bilgeadam.dto.request.AssignmentRequestDto;
import com.bilgeadam.dto.request.FindByStudentIdRequestDto;
import com.bilgeadam.dto.request.UpdateAssignmentRequestDto;
import com.bilgeadam.dto.response.AssignmentResponseDto;
import com.bilgeadam.exceptions.AssignmentException;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.IAssignmentMapper;
import com.bilgeadam.repository.IAssignmentRepository;
import com.bilgeadam.repository.entity.Assignment;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
            throw new CardServiceException(ErrorType.HW_NUMBER_RANGE);
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if(studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        List<String> groupNames = jwtTokenManager.getGroupNameFromToken(dto.getStudentToken());
        if(groupNames.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        Assignment assignment = assignmentMapper.toAssignment(dto);
        assignment.setGroupNames(groupNames);
        assignment.setStudentId(studentId.get());
        save(assignment);
        return true;
    }

    public List<AssignmentResponseDto> findAllAssignments(String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if(studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        return assignmentRepository.findAllByStudentId(studentId.get()).stream()
                .map(assignment -> assignmentMapper.fromAssignment(assignment))
                .toList();
    }

    public Boolean updateAssignment(UpdateAssignmentRequestDto dto) {
        Optional<Assignment> assignment = findById(dto.getAssignmentId());
        if (assignment.isEmpty())
            throw new CardServiceException(ErrorType.ASSIGNMENT_NOT_FOUND);
        Assignment toUpdate = assignment.get();
        toUpdate.setTitle(dto.getTitle());
        toUpdate.setScore(dto.getScore());
        toUpdate.setStatement(dto.getStatement());
        update(toUpdate);
        return true;
    }

    public Integer getAssignmentNote(String studentId){
        return (int) Math.floor(assignmentRepository.findAllByStudentId(studentId).stream()
                .mapToLong(x->x.getScore()).average().orElse(0));
    }

    public Boolean deleteAssignment(String assignmentId) {
        Optional<Assignment> assignment = findById(assignmentId);
        if (assignment.isEmpty())
            throw new CardServiceException(ErrorType.ASSIGNMENT_NOT_FOUND);
        deleteById(assignmentId);
        return true;
    }

    public Set<String> getAllTitles(String token) {
        List<String> groupNames = jwtTokenManager.getGroupNameFromToken(token);
        if(groupNames.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        return findAll().stream().filter(x -> x.getGroupNames().stream().anyMatch(groupNames::contains))
                .map(y-> y.getTitle()).collect(Collectors.toSet());
    }

    public Double getAssignmentAverage(String studentId){
        List<Double> notes = new ArrayList<>();
        notes.add(Double.valueOf(getAssignmentNote(studentId)));
        if(!notes.isEmpty()){
            double sumNotes = notes.stream().mapToDouble(note->note).sum();
            double result;
            result = sumNotes/notes.size();
            return result;
        }else{
            return (double) 0;
        }
    }
}
