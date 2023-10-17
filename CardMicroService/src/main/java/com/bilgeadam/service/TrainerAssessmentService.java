package com.bilgeadam.service;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.DeleteAssessmentResponseDto;
import com.bilgeadam.dto.response.TrainerAssessmentForTranscriptResponseDto;
import com.bilgeadam.dto.response.TrainerAssessmentSaveResponseDto;
import com.bilgeadam.dto.response.UpdateTrainerAssessmentResponseDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.manager.IUserManager;
import com.bilgeadam.mapper.ITrainerAssesmentMapper;
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

    public TrainerAssessmentSaveResponseDto saveTrainerAssessment(TrainerAssessmentSaveRequestDto dto) {
        System.out.println(dto);
        System.out.println(1);
        if (dto.getScore() < 0 || dto.getScore() > 10)
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_POINT_RANGE);
        if (dto.getDescription().isEmpty())
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_EMPTY);
        if (dto.getScore() == null)
            throw new CardServiceException(ErrorType.POINT_EMPTY);
        System.out.println(2);
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudenToken());
        System.out.println(studentId);
        System.out.println(4);
        TrainerAssessment trainerAssessment = ITrainerAssesmentMapper.INSTANCE.toTrainerAssesment(dto);
        trainerAssessment.setStudentId(studentId.get());
        List<TrainerAssessment> trainerAssessmentList = iTrainerAssesmentRepository.findAllByStudentId(studentId.get());
        int mastergorussayisi = 1;
        int trainergorussayisi = 1;
        int admingorussayisi = 1;
        if (!trainerAssessmentList.isEmpty()) {
            for (TrainerAssessment t : trainerAssessmentList) {
                if (jwtTokenManager.getIdRoleStatusEmailFromToken(dto.getToken()).getRole().equals(ERole.MASTER_TRAINER.name())) {
                    mastergorussayisi++;
                }
                if (jwtTokenManager.getIdRoleStatusEmailFromToken(dto.getToken()).getRole().equals(ERole.ASSISTANT_TRAINER.name())) {
                    trainergorussayisi++;
                }
                if (jwtTokenManager.getIdRoleStatusEmailFromToken(dto.getToken()).getRole().equals(ERole.ADMIN.name())) {
                    admingorussayisi++;
                }
            }
            if (jwtTokenManager.getIdRoleStatusEmailFromToken(dto.getToken()).getRole().equals(ERole.MASTER_TRAINER.name())) {
                trainerAssessment.setAssessmentName(mastergorussayisi + ". Master Görüş");
            }
            if (jwtTokenManager.getIdRoleStatusEmailFromToken(dto.getToken()).getRole().equals(ERole.ASSISTANT_TRAINER.name())) {
                trainerAssessment.setAssessmentName(trainergorussayisi + ". Trainer Görüş");
            }
            if (jwtTokenManager.getIdRoleStatusEmailFromToken(dto.getToken()).getRole().equals(ERole.ADMIN.name())) {
                trainerAssessment.setAssessmentName(admingorussayisi + ". Admin Görüş");
            }
        } else {
            if (jwtTokenManager.getIdRoleStatusEmailFromToken(dto.getToken()).getRole().equals(ERole.MASTER_TRAINER.name())) {
                trainerAssessment.setAssessmentName("1. Master Görüş");
            }
            if (jwtTokenManager.getIdRoleStatusEmailFromToken(dto.getToken()).getRole().equals(ERole.ASSISTANT_TRAINER.name())) {
                trainerAssessment.setAssessmentName("1. Trainer Görüş");
            }
            if (jwtTokenManager.getIdRoleStatusEmailFromToken(dto.getToken()).getRole().equals(ERole.ADMIN.name())) {
                trainerAssessment.setAssessmentName("1. Admin Görüş");
            }
        }
        save(trainerAssessment);
        return ITrainerAssesmentMapper.INSTANCE.toSaveTrainerAssesment(trainerAssessment);
    }

    public UpdateTrainerAssessmentResponseDto updateTrainerAssessment(UpdateTrainerAssessmentRequestDto dto) {
        Optional<TrainerAssessment> trainerAssessment = iTrainerAssesmentRepository.findById(dto.getAssessmentId());
        if (trainerAssessment.isEmpty())
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_NOT_FOUND);
        if (dto.getDescription().isEmpty())
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_EMPTY);
        if (dto.getScore() == null)
            throw new CardServiceException(ErrorType.POINT_EMPTY);
        TrainerAssessment trainerAssessmentUpdate = trainerAssessment.get();
        trainerAssessmentUpdate.setScore(dto.getScore());
        trainerAssessmentUpdate.setDescription(dto.getDescription());
        update(trainerAssessmentUpdate);

        return ITrainerAssesmentMapper.INSTANCE.toUpdateTrainerAssessment(trainerAssessment.get());
    }

    public Integer getTrainerAssessmentNote(String studentId) {
        return (int) Math.floor(iTrainerAssesmentRepository.findAllByStudentId(studentId).stream()
                .mapToLong(x -> x.getScore()).average().orElse(0));
    }

    public DeleteAssessmentResponseDto deleteTrainerAssessment(String id) {
        Optional<TrainerAssessment> trainerAssessment = iTrainerAssesmentRepository.findById(id);
        if (trainerAssessment.isEmpty())
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_NOT_FOUND);
        trainerAssessment.get().setEStatus(EStatus.DELETED);
        update(trainerAssessment.get());
        return ITrainerAssesmentMapper.INSTANCE.toDeleteTrainerAssesment(trainerAssessment.get());
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
                    TrainerAssessmentForTranscriptResponseDto dto = ITrainerAssesmentMapper.INSTANCE.toTrainerAssesmentForTranscriptResponseDto(x);
                    trainerAssessmentForTranscriptResponseDtoList.add(dto);
                }
        );
        return trainerAssessmentForTranscriptResponseDtoList;
    }
}
