package com.bilgeadam.service;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.dto.response.DeleteTrainerAssessmentResponseDto;
import com.bilgeadam.dto.response.TrainerAssessmentForTranscriptResponseDto;
import com.bilgeadam.dto.response.TrainerAssessmentSaveResponseDto;
import com.bilgeadam.dto.response.UpdateTrainerAssessmentResponseDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.manager.IUserManager;
import com.bilgeadam.mapper.ITrainerAssessmentMapper;
import com.bilgeadam.rabbitmq.model.ReminderMailModel;
import com.bilgeadam.rabbitmq.producer.ReminderMailProducer;
import com.bilgeadam.repository.ITrainerAssessmentRepository;
import com.bilgeadam.repository.entity.TrainerAssessment;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bilgeadam.repository.entity.TrainerAssessmentCoefficients.*;

@Service
public class TrainerAssessmentService extends ServiceManager<TrainerAssessment, String> {

    private final ITrainerAssessmentRepository iTrainerAssesmentRepository;
    private final JwtTokenManager jwtTokenManager;
    private final ReminderMailProducer reminderMailProducer;
    private final IUserManager userManager;

    public TrainerAssessmentService(ITrainerAssessmentRepository iTrainerAssessmentRepository, JwtTokenManager jwtTokenManager, ReminderMailProducer reminderMailProducer, IUserManager userManager) {
        super(iTrainerAssessmentRepository);
        this.iTrainerAssesmentRepository = iTrainerAssessmentRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.reminderMailProducer = reminderMailProducer;
        this.userManager = userManager;
    }

    public Integer getTrainerAssessmentNote(String studentId) {
        return (int) Math.floor(iTrainerAssesmentRepository.findAllByStudentId(studentId).stream()
                .mapToLong(x-> (long) x.getTotalTrainerAssessmentScore()).average().orElse(0));
    }
    public DeleteTrainerAssessmentResponseDto deleteTrainerAssessment(String id) {
        Optional<TrainerAssessment> trainerAssessment = iTrainerAssesmentRepository.findById(id);
        if (trainerAssessment.isEmpty())
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_NOT_FOUND);
        deleteById(id);
        trainerAssessment.get().setEStatus(EStatus.DELETED);
        update(trainerAssessment.get());
        return ITrainerAssessmentMapper.INSTANCE.toDeleteTrainerAssessment(trainerAssessment.get());
    }
    public List<TrainerAssessment> findAllTrainerAssessment(TokenRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getToken());
        return findAll().stream().filter(x -> x.getEStatus() == EStatus.ACTIVE && x.getStudentId().equals(studentId.get()))
                .collect(Collectors.toList());
    }
    @Scheduled(fixedRate = 86400000)
    //cron = "0 58 23 15 * ?"
    //fixedRate = 300000
    public void sendReminderMail() {
        List<TrainersMailReminderDto> trainers = userManager.getTrainers().getBody();
        List<StudentsMailReminderDto> students = userManager.getStudents().getBody();
        List<MastersMailReminderDto> masters = userManager.getMasters().getBody();
        for (StudentsMailReminderDto s : students) {
            Double sure = s.getEgitimSaati();
            List<TrainerAssessment> gorusListesi = iTrainerAssesmentRepository.findAllByStudentId(s.getStudentId()).stream()
                    .filter(x -> x.getEStatus().equals(EStatus.ACTIVE))
                    .collect(Collectors.toList());
            if(sure == null)
               // throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_NOT_FOUND);
                continue; // TODO: Tekrar bakılması gerekiyor !!!
            if (sure == null)
                throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_NOT_FOUND);
            if (sure >= 1 && sure < 50) {
                List<TrainerAssessment> masterAssessmentList = gorusListesi.stream().filter(x ->
                                x.getAssessmentName().equals("1. Master Görüş"))
                        .collect(Collectors.toList());
                List<TrainerAssessment> trainerAssessmentList = gorusListesi.stream().filter(x ->
                                x.getAssessmentName().equals("1. Trainer Görüş"))
                        .collect(Collectors.toList());
                if (masterAssessmentList.isEmpty()) {
                    reminderMailProducer.sendReminderMail(ReminderMailModel.builder()
                            .email(masters.stream().filter(x -> x.getGroupName().contains(s.getGroupName().get(0))).toList().get(0).getEmail())
                            .studentName(s.getName() + " " + s.getSurname())
                            .aralik("eğitim başlangıcı görüşü")
                            .build());
                }
                if (trainerAssessmentList.isEmpty()) {
                    reminderMailProducer.sendReminderMail(ReminderMailModel.builder()
                            .email(trainers.stream().filter(x -> x.getGroupName().contains(s.getGroupName().get(0))).toList().get(0).getEmail())
                            .studentName(s.getName() + " " + s.getSurname())
                            .aralik("eğitim başlangıcı görüşü")
                            .build());
                }
            } else if (sure >= 50 && sure < 100) {
                List<TrainerAssessment> masterAssessmentList = gorusListesi.stream().filter(x -> x.getAssessmentName().equals("2. Master Görüş")).collect(Collectors.toList());
                List<TrainerAssessment> trainerAssessmentList = gorusListesi.stream().filter(x -> x.getAssessmentName().equals("2. Trainer Görüş")).collect(Collectors.toList());
                if (masterAssessmentList.isEmpty()) {
                    reminderMailProducer.sendReminderMail(ReminderMailModel.builder()
                            .email(masters.stream().filter(x -> x.getGroupName().contains(s.getGroupName().get(0))).toList().get(0).getEmail())
                            .studentName(s.getName() + " " + s.getSurname())
                            .aralik("50. saat görüşü")
                            .build());
                }
                if (trainerAssessmentList.isEmpty()) {
                    reminderMailProducer.sendReminderMail(ReminderMailModel.builder()
                            .email(trainers.stream().filter(x -> x.getGroupName().contains(s.getGroupName().get(0))).toList().get(0).getEmail())
                            .studentName(s.getName() + " " + s.getSurname())
                            .aralik("50. saat görüşü")
                            .build());
                }
            } else if (sure >= 100 && sure < 200) {
                List<TrainerAssessment> masterAssessmentList = gorusListesi.stream().filter(x -> x.getAssessmentName().equals("3. Master Görüş")).collect(Collectors.toList());
                List<TrainerAssessment> trainerAssessmentList = gorusListesi.stream().filter(x -> x.getAssessmentName().equals("3. Trainer Görüş")).collect(Collectors.toList());
                if (masterAssessmentList.isEmpty()) {
                    reminderMailProducer.sendReminderMail(ReminderMailModel.builder()
                            .email(masters.stream().filter(x -> x.getGroupName().contains(s.getGroupName().get(0))).toList().get(0).getEmail())
                            .studentName(s.getName() + " " + s.getSurname())
                            .aralik("100. saat görüşü")
                            .build());
                }
                if (trainerAssessmentList.isEmpty()) {
                    reminderMailProducer.sendReminderMail(ReminderMailModel.builder()
                            .email(trainers.stream().filter(x -> x.getGroupName().contains(s.getGroupName().get(0))).toList().get(0).getEmail())
                            .studentName(s.getName() + " " + s.getSurname())
                            .aralik("100. saat görüşü")
                            .build());
                }
            } else if (sure >= 200 && sure < 300) {
                List<TrainerAssessment> masterAssessmentList = gorusListesi.stream().filter(x -> x.getAssessmentName().equals("4. Master Görüş")).collect(Collectors.toList());
                List<TrainerAssessment> trainerAssessmentList = gorusListesi.stream().filter(x -> x.getAssessmentName().equals("4. Trainer Görüş")).collect(Collectors.toList());
                if (masterAssessmentList.isEmpty()) {
                    reminderMailProducer.sendReminderMail(ReminderMailModel.builder()
                            .email(masters.stream().filter(x -> x.getGroupName().contains(s.getGroupName().get(0))).toList().get(0).getEmail())
                            .studentName(s.getName() + " " + s.getSurname())
                            .aralik("200. saat görüşü")
                            .build());
                }
                if (trainerAssessmentList.isEmpty()) {
                    reminderMailProducer.sendReminderMail(ReminderMailModel.builder()
                            .email(trainers.stream().filter(x -> x.getGroupName().contains(s.getGroupName().get(0))).toList().get(0).getEmail())
                            .studentName(s.getName() + " " + s.getSurname())
                            .aralik("200. saat görüşü")
                            .build());
                }
            } else if (sure >= 300) {
                List<TrainerAssessment> masterAssessmentList = gorusListesi.stream().filter(x -> x.getAssessmentName().equals("5. Master Görüş")).collect(Collectors.toList());
                List<TrainerAssessment> trainerAssessmentList = gorusListesi.stream().filter(x -> x.getAssessmentName().equals("5. Trainer Görüş")).collect(Collectors.toList());
                if (masterAssessmentList.isEmpty()) {
                    reminderMailProducer.sendReminderMail(ReminderMailModel.builder()
                            .email(masters.stream().filter(x -> x.getGroupName().contains(s.getGroupName().get(0))).toList().get(0).getEmail())
                            .studentName(s.getName() + " " + s.getSurname())
                            .aralik("300. saat görüşü")
                            .build());
                }
                if (trainerAssessmentList.isEmpty()) {
                    reminderMailProducer.sendReminderMail(ReminderMailModel.builder()
                            .email(trainers.stream().filter(x -> x.getGroupName().contains(s.getGroupName().get(0))).toList().get(0).getEmail())
                            .studentName(s.getName() + " " + s.getSurname())
                            .aralik("300. saat görüşü")
                            .build());
                }
            }
        }
    }

    public List<TrainerAssessmentForTranscriptResponseDto> findAllTrainerAssessmentForTranscriptResponseDto(String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        List<TrainerAssessment> trainerAssessmentList = findAll().stream().filter(x -> x.getEStatus() == EStatus.ACTIVE && x.getStudentId().equals(studentId.get()))
                .collect(Collectors.toList());
        List<TrainerAssessmentForTranscriptResponseDto> trainerAssessmentForTranscriptResponseDtoList = new ArrayList<>();
        trainerAssessmentList.forEach(x -> {
                    TrainerAssessmentForTranscriptResponseDto dto = TrainerAssessmentForTranscriptResponseDto.builder().totalTrainerAssessmentScore(x.getTotalTrainerAssessmentScore()).score(x.getTotalTrainerAssessmentScore()).assessmentName(x.getAssessmentName()).build();
                    trainerAssessmentForTranscriptResponseDtoList.add(dto);
                }
        );
        return trainerAssessmentForTranscriptResponseDtoList;
    }
}