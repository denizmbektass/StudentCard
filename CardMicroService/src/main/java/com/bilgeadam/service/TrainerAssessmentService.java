package com.bilgeadam.service;

import com.bilgeadam.dto.request.TrainerAssessmentRequestDto;
import com.bilgeadam.dto.response.TrainerAssessmentResponseDto;
import com.bilgeadam.mapper.ITrainerAssesmentMapper;
import com.bilgeadam.repository.ITrainerAssessmentRepository;
import com.bilgeadam.repository.entity.TrainerAssessment;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TrainerAssessmentService extends ServiceManager<TrainerAssessment,String> {

    private final ITrainerAssessmentRepository iTrainerAssesmentRepository;

    public TrainerAssessmentService(ITrainerAssessmentRepository iTrainerAssessmentRepository){
        super(iTrainerAssessmentRepository);
        this.iTrainerAssesmentRepository=iTrainerAssessmentRepository;
    }

    public ResponseEntity<TrainerAssessmentResponseDto> TrainerAssesmentDescription(TrainerAssessmentRequestDto dto){
//        Optional<TrainerAssessment> trainerAssessment = iTrainerAssesmentRepository.findOptionalByCardId(dto);
//        if(!trainerAssessment.isPresent())
//            throw new CardServiceException(ErrorType.CARD_NOT_FOUND);
        TrainerAssessment trainerAssessment= new TrainerAssessment();
        trainerAssessment.builder()
                .description(dto.getDescription())
                .score(dto.getScore())
                .studentId(dto.getStudentId())
                .build();

        return ResponseEntity.ok(ITrainerAssesmentMapper.INSTANCE.toTrainerAssesment2(trainerAssessment));
    }
}
