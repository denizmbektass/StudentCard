package com.bilgeadam.service;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.dto.response.DeleteAssessmentResponseDto;
import com.bilgeadam.dto.response.TrainerAssessmentForTranscriptResponseDto;
import com.bilgeadam.dto.response.TrainerAssessmentSaveResponseDto;
import com.bilgeadam.dto.response.UpdateTrainerAssessmentResponseDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.manager.IUserManager;
import com.bilgeadam.mapper.ITrainerAssessmentMapper;
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

import static com.bilgeadam.constants.ApiUrls.*;
import static com.bilgeadam.repository.entity.TrainerAssessment.*;

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

    public double calculateTrainerAssessmentScore(TrainerAssessmentSaveRequestDto dto){

        double additionOfBehaviorInClass = BEHAVIOR_IN_CLASS_COEFFICIENT * dto.getBehaviorInClass();
        double additionOfCourseInterestLevel = COURSE_INTEREST_LEVEL_COEFFICIENT * dto.getCourseInterestLevel();
        double additionOfCameraOpeningGrade = CAMERA_OPENING_RATE_COEFFICIENT * dto.getCameraOpeningGrade();
        double additionOfInstructorGrade = INSTRUCTOR_GRADE_RATE_COEFFICIENT * dto.getInstructorGrade();
        double additionOfDailyHomeworkGrade = DAILY_HOMEWORK_RATE_COEFFICIENT * dto.getDailyHomeworkGrade();
        double totalTrainerAssessmentScore = additionOfBehaviorInClass + additionOfCourseInterestLevel + additionOfCameraOpeningGrade + additionOfInstructorGrade + additionOfDailyHomeworkGrade;
        return totalTrainerAssessmentScore;
    }

    public TrainerAssessmentSaveResponseDto saveTrainerAssessment(TrainerAssessmentSaveRequestDto dto){
        System.out.println(dto);
        System.out.println(1);
        if (dto.getBehaviorInClass()<0.0 || dto.getBehaviorInClass()>100.0)
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_POINT_RANGE);
        if(dto.getCourseInterestLevel()<0.0 || dto.getCourseInterestLevel()>100.0)
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_POINT_RANGE);
        if(dto.getCameraOpeningGrade()<0.0 || dto.getCameraOpeningGrade()>100.0)
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_POINT_RANGE);
        if(dto.getInstructorGrade()<0.0 || dto.getInstructorGrade()>100.0)
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_POINT_RANGE);
        if(dto.getDailyHomeworkGrade()<0.0 || dto.getDailyHomeworkGrade()>100.0)
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_POINT_RANGE);
        if (dto.getDescription().isEmpty())
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_EMPTY);

        Optional<String> studentId= jwtTokenManager.getIdFromToken(dto.getStudentToken());
        System.out.println(studentId);
        System.out.println(4);
        TrainerAssessment trainerAssessment= ITrainerAssessmentMapper.INSTANCE.toTrainerAssessment(dto);
        trainerAssessment.setStudentId(studentId.get());
        trainerAssessment.setTotalTrainerAssessmentScore(calculateTrainerAssessmentScore(dto));
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
        return ITrainerAssessmentMapper.INSTANCE.toSaveTrainerAssessment(trainerAssessment);
    }

    public UpdateTrainerAssessmentResponseDto updateTrainerAssessment(UpdateTrainerAssessmentRequestDto dto) {
        Optional<TrainerAssessment> trainerAssessment = iTrainerAssesmentRepository.findById(dto.getAssessmentId());
        if (trainerAssessment.isEmpty())
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_NOT_FOUND);
        if (dto.getDescription().isEmpty())
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_EMPTY);
        if(dto.getTotalTrainerAssessmentScore() == 0.0)
            throw new CardServiceException(ErrorType.POINT_EMPTY);
        TrainerAssessment trainerAssessmentUpdate = trainerAssessment.get();
        trainerAssessmentUpdate.setTotalTrainerAssessmentScore(dto.getTotalTrainerAssessmentScore());
        trainerAssessmentUpdate.setDescription(dto.getDescription());
        update(trainerAssessmentUpdate);

        return ITrainerAssessmentMapper.INSTANCE.toUpdateTrainerAssessment(trainerAssessment.get());
    }

    public Integer getTrainerAssessmentNote(String studentId) {
        return (int) Math.floor(iTrainerAssesmentRepository.findAllByStudentId(studentId).stream()
                .mapToLong(x-> (long) x.getTotalTrainerAssessmentScore()).average().orElse(0));

    }

    public DeleteAssessmentResponseDto deleteTrainerAssessment(String id) {
        Optional<TrainerAssessment> trainerAssessment = iTrainerAssesmentRepository.findById(id);
        if (trainerAssessment.isEmpty())
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_NOT_FOUND);
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
                    TrainerAssessmentForTranscriptResponseDto dto = ITrainerAssessmentMapper.INSTANCE.toTrainerAssessmentForTranscriptResponseDto(x);
                    trainerAssessmentForTranscriptResponseDtoList.add(dto);
                }
        );
        return trainerAssessmentForTranscriptResponseDtoList;
    }
}
