package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateExamRequestDto;
import com.bilgeadam.dto.request.FindByStudentIdRequestDto;
import com.bilgeadam.dto.request.UpdateExamRequestDto;
import com.bilgeadam.dto.response.ExamResponseDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.exceptions.AssignmentException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.exceptions.ExamException;
import com.bilgeadam.mapper.IExamMapper;
import com.bilgeadam.repository.IExamRepository;
import com.bilgeadam.repository.entity.Assignment;
import com.bilgeadam.repository.entity.Exam;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamService extends ServiceManager<Exam,String> {

    private  final IExamRepository examRepository;
    private  final IExamMapper examMapper;
    public ExamService(IExamRepository examRepository, IExamMapper examMapper) {
        super(examRepository);
        this.examRepository=examRepository;
        this.examMapper=examMapper;
    }

    public MessageResponse createExam (CreateExamRequestDto dto){
        if(dto.getScore()>=100||dto.getScore()<=0)
            throw new ExamException(ErrorType.BAD_REQUEST,"Sınav notu 100'den büyük veya 0'dan küçük olamaz...");
        Exam exam = examMapper.toExam(dto);
        save(exam);
        return new MessageResponse("Sınav başarı ile oluşturuldu.");
    }

    public List<ExamResponseDto> findAllExams(FindByStudentIdRequestDto dto){
        return examRepository.findAllByStudentId(dto.getStudentId()).stream().map(exam ->
            examMapper.toExamResponseDto(exam)
        ).toList();
    }
    public MessageResponse updateExam(UpdateExamRequestDto dto){
      Optional<Exam> exam = findById(dto.getExamId());
        if(exam.isEmpty())
            throw new ExamException(ErrorType.EXAM_NOT_FOUND);
        Exam update = exam.get();
        update.setDescription(dto.getDescription());
        update.setTitle(dto.getTitle());
        update.setScore(dto.getScore());
        update(update);
        return new MessageResponse("Sınav başarıyla güncellendi.");
    }

    public MessageResponse deleteExam(String examId) {
        Optional<Exam> exam = findById(examId);
        if (exam.isEmpty())
            throw new ExamException(ErrorType.EXAM_NOT_FOUND);
        deleteById(examId);
        return  new MessageResponse("Sınav başarıyla silindi.");
    }
}
