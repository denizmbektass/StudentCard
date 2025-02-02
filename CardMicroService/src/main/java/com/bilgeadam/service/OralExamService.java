package com.bilgeadam.service;

import com.bilgeadam.dto.request.OralExamRequestDto;
import com.bilgeadam.dto.request.UpdateOralExamRequestDto;
import com.bilgeadam.dto.response.OralExamResponseDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.IOralExamMapper;
import com.bilgeadam.repository.IOralExamRepository;
import com.bilgeadam.repository.entity.OralExam;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OralExamService extends ServiceManager<OralExam,String> {
    private final IOralExamRepository oralExamRepository;
    private final IOralExamMapper oralExamMapper;
    private final JwtTokenManager jwtTokenManager;

    public OralExamService(IOralExamRepository oralExamRepository, IOralExamMapper oralExamMapper, JwtTokenManager jwtTokenManager) {
        super(oralExamRepository);
        this.oralExamRepository = oralExamRepository;
        this.oralExamMapper = oralExamMapper;
        this.jwtTokenManager = jwtTokenManager;
    }

    public Boolean createOralExam(OralExamRequestDto dto){
        if(dto.getScore()>100||dto.getScore()<0)
            throw new CardServiceException(ErrorType.HW_NUMBER_RANGE);
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if(studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        List<String> groupNames = jwtTokenManager.getGroupNameFromToken(dto.getStudentToken());
        if(groupNames.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        OralExam oralExam = oralExamMapper.toOralExam(dto);
        oralExam.setGroupNames(groupNames);
        oralExam.setStudentId(studentId.get());
        save(oralExam);
        return true;
    }

    public List<OralExamResponseDto> findAllOralExam(String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if(studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        return oralExamRepository.findAllByStudentId(studentId.get()).stream()
                .map(oralExam -> oralExamMapper.fromOralExam(oralExam))
                .toList();
    }

    public Boolean updateOralExam(UpdateOralExamRequestDto dto) {
        Optional<OralExam> oralExam = findById(dto.getOralExamId());
        if (oralExam.isEmpty())
            throw new CardServiceException(ErrorType.ORALEXAM_NOT_FOUND);
        OralExam toUpdate = oralExam.get();
        toUpdate.setTitle(dto.getTitle());
        toUpdate.setScore(dto.getScore());
        toUpdate.setStatement(dto.getStatement());
        update(toUpdate);
        return true;
    }

    public Integer getOralExamNote(String studentId){
        return (int) Math.floor(oralExamRepository.findAllByStudentId(studentId).stream()
                .mapToLong(x->x.getScore()).average().orElse(0));
    }

    public Boolean deleteOralExam(String oralExamId) {
        Optional<OralExam> oralExam = findById(oralExamId);
        if (oralExam.isEmpty())
            throw new CardServiceException(ErrorType.ORALEXAM_NOT_FOUND);
        deleteById(oralExamId);
        return true;
    }

    public Set<String> getAllTitles(String token) {
        List<String> groupNames = jwtTokenManager.getGroupNameFromToken(token);
        if(groupNames.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        return findAll().stream().filter(x -> x.getGroupNames().stream().anyMatch(groupNames::contains))
                .map(y-> y.getTitle()).collect(Collectors.toSet());
    }

    public Double getOralExamAverage(String studentId){
        List<Double> notes = new ArrayList<>();
        notes.add(Double.valueOf(getOralExamNote(studentId)));
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
