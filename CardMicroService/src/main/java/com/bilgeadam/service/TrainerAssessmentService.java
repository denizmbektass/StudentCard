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
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainerAssessmentService extends ServiceManager<TrainerAssessment,String> {

    private final ITrainerAssessmentRepository iTrainerAssesmentRepository;
    private final JwtTokenManager jwtTokenManager;

    public TrainerAssessmentService(ITrainerAssessmentRepository iTrainerAssessmentRepository, JwtTokenManager jwtTokenManager){
        super(iTrainerAssessmentRepository);
        this.iTrainerAssesmentRepository=iTrainerAssessmentRepository;
        this.jwtTokenManager = jwtTokenManager;
    }

    public TrainerAssessmentSaveResponseDto saveTrainerAssessment(TrainerAssessmentSaveRequestDto dto){
        /**
         * StudentId ekledniğinde geliştirilecek
         */
//        if (dto.getScore()<=0 || dto.getScore()>10)
//            throw new TrainerAssessmentException(ErrorType.BAD_REQUEST,"Puan 1 ile 10 arasında olmak zorundadır...");
//        if(dto.getDescription().isEmpty())
//            throw new TrainerAssessmentException(ErrorType.BAD_REQUEST,"Görüş boş bırakılamaz...");
//        if(dto.getScore()==null)
//            throw new TrainerAssessmentException(ErrorType.BAD_REQUEST,"Puan boş bırakılamaz...");

        Optional<String> studentId= jwtTokenManager.getIdFromToken(dto.getToken());
        TrainerAssessment trainerAssessment= ITrainerAssesmentMapper.INSTANCE.toTrainerAssesment(dto);
        trainerAssessment.setStudentId(studentId.get());
        save(trainerAssessment);
        return ITrainerAssesmentMapper.INSTANCE.toSaveTrainerAssesment(trainerAssessment);
    }

    public UpdateTrainerAssessmentResponseDto updateTrainerAssessment(UpdateTrainerAssessmentRequestDto dto, String id){
        Optional<TrainerAssessment> trainerAssessment=iTrainerAssesmentRepository.findOptionalByTrainerAssessmentId(id);
        if(trainerAssessment.isEmpty())
            throw new TrainerAssessmentException(ErrorType.TRAINER_ASSESSMENT_NOT_FOUND);
        TrainerAssessment trainerAssessmentUpdate = trainerAssessment.get();
        trainerAssessmentUpdate.setScore(dto.getScore());
        trainerAssessmentUpdate.setDescription(dto.getDescription());
        trainerAssessmentUpdate.setStudentId(dto.getStudentId());
        update(trainerAssessmentUpdate);

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

    public List<TrainerAssessment> findAllTrainerAssessment(String studentId) {
        return findAll().stream().filter(x->x.getEStatus()==EStatus.ACTIVE && x.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }
}
