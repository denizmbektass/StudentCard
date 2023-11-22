package com.bilgeadam.service;

import com.bilgeadam.dto.request.SaveWrittenExamRequestDto;
import com.bilgeadam.dto.request.UpdateWrittenExamRequestDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.repository.IWrittenExamRepository;
import com.bilgeadam.repository.entity.WrittenExam;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WrittenExamService extends ServiceManager<WrittenExam, String> {

    private final IWrittenExamRepository writtenExamRepository;
    private  final JwtTokenManager jwtTokenManager;

    public WrittenExamService(IWrittenExamRepository writtenExamRepository, JwtTokenManager jwtTokenManager) {
        super(writtenExamRepository);
        this.writtenExamRepository = writtenExamRepository;
        this.jwtTokenManager = jwtTokenManager;
    }

    public double calculateWrittenExamScore(int correctAnswers){
        int totalQuestion = 35;
        double maxScore = 100;

        if (correctAnswers < 0 || correctAnswers > totalQuestion){
            throw new CardServiceException(ErrorType.WRITTENEXAM_NUMBER_RANGE);
        }

        double score = (maxScore / totalQuestion)*correctAnswers;
        return score;
    }

    public WrittenExam saveWrittenExam(SaveWrittenExamRequestDto dto){
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isEmpty()){
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        WrittenExam writtenExam = WrittenExam.builder()
                .correctAnswers(dto.getCorrectAnswers())
                .score(calculateWrittenExamScore(dto.getCorrectAnswers()))
                .studentId(studentId.get())
                .build();
        return writtenExamRepository.save(writtenExam);
    }

    public WrittenExam getWrittenExamByStudentId(String studentId){
        Optional<WrittenExam> writtenExam = writtenExamRepository.findByStudentId(studentId);
        if (writtenExam.isPresent()){
            return writtenExam.get();
        }else {
            return null;
        }
    }

    public MessageResponse updateWrittenExam(UpdateWrittenExamRequestDto dto){
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isEmpty()){
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        Optional<WrittenExam> writtenExam = writtenExamRepository.findByStudentId(studentId.get());
        if (writtenExam.isEmpty())
            throw new CardServiceException(ErrorType.WRITTENEXAM_NOT_FOUND);
        WrittenExam updatedWrittenExam = writtenExam.get();
        updatedWrittenExam.setCorrectAnswers(dto.getCorrectAnswers());
        updatedWrittenExam.setScore(calculateWrittenExamScore(dto.getCorrectAnswers()));
        update(updatedWrittenExam);
        return new MessageResponse("Yazılı sınav başarıyla güncellendi.");
    }



}
