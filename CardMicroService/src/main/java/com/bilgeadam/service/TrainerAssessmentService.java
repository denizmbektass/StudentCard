package com.bilgeadam.service;

import com.bilgeadam.dto.request.TrainerAssessmentSaveRequestDto;
import com.bilgeadam.dto.request.UpdateTrainerAssessmentRequestDto;
import com.bilgeadam.dto.response.TrainerAssessmentSaveResponseDto;
import com.bilgeadam.dto.response.UpdateTrainerAssessmentResponseDto;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.exceptions.TrainerAssessmentException;
import com.bilgeadam.mapper.ITrainerAssesmentMapper;
import com.bilgeadam.repository.ITrainerAssessmentRepository;
import com.bilgeadam.repository.entity.TrainerAssessment;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainerAssessmentService extends ServiceManager<TrainerAssessment,String> {

    private final ITrainerAssessmentRepository iTrainerAssesmentRepository;

    public TrainerAssessmentService(ITrainerAssessmentRepository iTrainerAssessmentRepository){
        super(iTrainerAssessmentRepository);
        this.iTrainerAssesmentRepository=iTrainerAssessmentRepository;
    }

    public TrainerAssessmentSaveResponseDto saveTrainerAssessment(TrainerAssessmentSaveRequestDto dto){
        /**
         * StudentId ekledniğinde geliştirilecek
         */
        TrainerAssessment trainerAssessment= ITrainerAssesmentMapper.INSTANCE.toTrainerAssesment(dto);
        save(trainerAssessment);
        return ITrainerAssesmentMapper.INSTANCE.toSaveTrainerAssesment(trainerAssessment);
    }

    public UpdateTrainerAssessmentResponseDto updateTrainerAssessment(UpdateTrainerAssessmentRequestDto dto, String id){
        Optional<TrainerAssessment> trainerAssessment=iTrainerAssesmentRepository.findOptionalByTrainerAssessmentId(id);
        if(trainerAssessment.isEmpty())
            throw new TrainerAssessmentException(ErrorType.TRAINER_ASSESSMENT_NOT_FOUND);

        trainerAssessment.get().setScore(dto.getScore());
        trainerAssessment.get().setDescription(dto.getDescription());
        trainerAssessment.get().setStudentId(dto.getStudentId());
        update(trainerAssessment.get());

        return ITrainerAssesmentMapper.INSTANCE.toUpdateTrainerAssessment(trainerAssessment.get());
    }

    public String deleteTrainerAssessment(String id){
        Optional<TrainerAssessment> trainerAssessment=iTrainerAssesmentRepository.findOptionalByTrainerAssessmentId(id);
        if(trainerAssessment.isEmpty())
            throw new TrainerAssessmentException(ErrorType.TRAINER_ASSESSMENT_NOT_FOUND);
        trainerAssessment.get().setEStatus(EStatus.DELETED);
        update(trainerAssessment.get());

        return "Silme işlemi başarılı";
    }

    public List<TrainerAssessment> findAllTrainerAssessment() {
        return findAll().stream().filter(x->x.getEStatus()==EStatus.ACTIVE)
                .collect(Collectors.toList());
    }
}
