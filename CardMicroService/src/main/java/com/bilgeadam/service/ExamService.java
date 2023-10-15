package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateExamRequestDto;

import com.bilgeadam.dto.request.UpdateExamRequestDto;
import com.bilgeadam.dto.response.AverageExamResponseDto;
import com.bilgeadam.dto.response.ExamResponseDto;
import com.bilgeadam.dto.response.MessageResponse;

import com.bilgeadam.exceptions.AssignmentException;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.exceptions.ExamException;
import com.bilgeadam.mapper.IExamMapper;
import com.bilgeadam.repository.IExamRepository;

import com.bilgeadam.repository.entity.Assignment;
import com.bilgeadam.repository.entity.Exam;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExamService extends ServiceManager<Exam,String> {

    private  final IExamRepository examRepository;
    private  final IExamMapper examMapper;
    private  final JwtTokenManager jwtTokenManager;
    public ExamService(IExamRepository examRepository, IExamMapper examMapper, JwtTokenManager jwtTokenManager) {
        super(examRepository);
        this.examRepository=examRepository;
        this.examMapper=examMapper;
        this.jwtTokenManager=jwtTokenManager;
    }

    public MessageResponse createExam (CreateExamRequestDto dto){
        if(dto.getScore()>100||dto.getScore()<0)
            throw new CardServiceException(ErrorType.EXAM_NUMBER_RANGE);
       Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
       if(studentId.isEmpty())
           throw new CardServiceException(ErrorType.INVALID_TOKEN);
       List<String> groupNameList = jwtTokenManager.getGroupNameFromToken(dto.getStudentToken());
        if(groupNameList.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        Exam exam = examMapper.toExam(dto);
        exam.setStudentId(studentId.get());
        exam.setGroupNames(groupNameList);
        save(exam);
        return new MessageResponse("Sınav başarı ile oluşturuldu.");
    }

    public List<ExamResponseDto> findAllExams(String token){
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if(studentId.isEmpty())
        { throw new CardServiceException(ErrorType.INVALID_TOKEN);}

        return examRepository.findAllByStudentId(studentId.get()).stream().map(exam ->
            examMapper.toExamResponseDto(exam)).toList();
    }
    public Integer getExamNote(String studentId){
        return (int) Math.floor(examRepository.findAllByStudentId(studentId).stream()
                .mapToLong(x->x.getScore()).average().orElse(0));
    }
    public MessageResponse updateExam(UpdateExamRequestDto dto){
      Optional<Exam> exam = findById(dto.getExamId());
        if(exam.isEmpty())
            throw new CardServiceException(ErrorType.EXAM_NOT_FOUND);
        Exam update = exam.get();
        update.setStatement(dto.getStatement());
        update.setTitle(dto.getTitle());
        update.setScore(dto.getScore());
        update(update);
        return new MessageResponse("Sınav başarıyla güncellendi.");
    }

  /*  public MessageResponse deleteExam(String examId) {
        Optional<Exam> exam = findById(examId);
        if (exam.isEmpty())
            throw new ExamException(ErrorType.EXAM_NOT_FOUND);
        deleteById(examId);
        return  new MessageResponse("Sınav başarıyla silindi.");
    }*/
    public Boolean deleteExam(String examId) {
        Optional<Exam> exam = findById(examId);
        if (exam.isEmpty())
            throw new CardServiceException(ErrorType.EXAM_NOT_FOUND);
        deleteById(examId);
        return true;
    }

    public Set<String> getAllTitles(String token) {
        List<String> groupNames = jwtTokenManager.getGroupNameFromToken(token);
        if(groupNames.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        return findAll().stream().filter(x -> x.getGroupNames().stream().anyMatch(groupNames::contains))
                .map(y-> y.getTitle()).collect(Collectors.toSet());

    }

    public AverageExamResponseDto averageExam(String studentId){
        List<Exam> exams = examRepository.findAllByStudentId(studentId);
        if (exams.isEmpty()){
            return new AverageExamResponseDto(studentId, 0.0);
        }
        double totalScore = 0.0;
        for (Exam exam: exams) {
            totalScore += exam.getScore();
        }
        double averageScore = totalScore / exams.size();
        return new AverageExamResponseDto(studentId,averageScore);
    }

}
