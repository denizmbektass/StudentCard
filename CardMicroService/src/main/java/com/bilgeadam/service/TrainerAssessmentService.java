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
import com.bilgeadam.repository.ITrainerAssessmentCoefficientsRepository;
import com.bilgeadam.repository.ITrainerAssessmentRepository;
import com.bilgeadam.repository.entity.TrainerAssessment;
import com.bilgeadam.repository.entity.TrainerAssessmentCoefficients;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bilgeadam.constants.ApiUrls.*;
import static com.bilgeadam.repository.entity.TrainerAssessment.*;
import static com.bilgeadam.repository.entity.TrainerAssessmentCoefficients.*;

@Service
public class TrainerAssessmentService extends ServiceManager<TrainerAssessment, String> {

    private final ITrainerAssessmentRepository iTrainerAssesmentRepository;
    private final ITrainerAssessmentCoefficientsRepository iTrainerAssessmentCoefficientsRepository;
    private final JwtTokenManager jwtTokenManager;
    private final ReminderMailProducer reminderMailProducer;
    private final IUserManager userManager;

    public TrainerAssessmentService(ITrainerAssessmentRepository iTrainerAssessmentRepository, JwtTokenManager jwtTokenManager, ReminderMailProducer reminderMailProducer, IUserManager userManager,ITrainerAssessmentCoefficientsRepository iTrainerAssessmentCoefficientsRepository) {
        super(iTrainerAssessmentRepository);
        this.iTrainerAssesmentRepository = iTrainerAssessmentRepository;
        this.iTrainerAssessmentCoefficientsRepository = iTrainerAssessmentCoefficientsRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.reminderMailProducer = reminderMailProducer;
        this.userManager = userManager;
    }
    public TrainerAssessment changeTrainerAssessmentCoefficients(@RequestBody ChangeTrainerAssessmentCoefficientsRequestDto dto){
        System.out.println(dto);
        if (dto.getBehaviorInClassCoefficient()<0.0 || dto.getBehaviorInClassCoefficient()>1.0)
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_COEFFICIENTS_POINT_RANGE);
        if(dto.getCourseInterestLevelCoefficient()<0.0 || dto.getCourseInterestLevelCoefficient()>1.0)
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_COEFFICIENTS_POINT_RANGE);
        if(dto.getCameraOpeningGradeCoefficient()<0.0 || dto.getCameraOpeningGradeCoefficient()>1.0)
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_COEFFICIENTS_POINT_RANGE);
        if(dto.getInstructorGradeCoefficient()<0.0 || dto.getInstructorGradeCoefficient()>1.0)
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_COEFFICIENTS_POINT_RANGE);
        if(dto.getDailyHomeworkGradeCoefficient()<0.0 || dto.getDailyHomeworkGradeCoefficient()>1.0)
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_COEFFICIENTS_POINT_RANGE);

        TrainerAssessmentCoefficients trainerAssessmentCoefficients = ITrainerAssessmentMapper.INSTANCE.toTrainerAssessmentCoefficients(dto);
        BEHAVIOR_IN_CLASS_COEFFICIENT = dto.getBehaviorInClassCoefficient();
        COURSE_INTEREST_LEVEL_COEFFICIENT = dto.getCourseInterestLevelCoefficient();
        CAMERA_OPENING_RATE_COEFFICIENT = dto.getCameraOpeningGradeCoefficient();
        INSTRUCTOR_GRADE_RATE_COEFFICIENT = dto.getInstructorGradeCoefficient();
        DAILY_HOMEWORK_RATE_COEFFICIENT = dto.getDailyHomeworkGradeCoefficient();
        double totalTrainerAssessmentCoefficients = BEHAVIOR_IN_CLASS_COEFFICIENT + COURSE_INTEREST_LEVEL_COEFFICIENT +
                                                    CAMERA_OPENING_RATE_COEFFICIENT + INSTRUCTOR_GRADE_RATE_COEFFICIENT +
                                                    DAILY_HOMEWORK_RATE_COEFFICIENT;
        if(totalTrainerAssessmentCoefficients <0.0 || totalTrainerAssessmentCoefficients >1.0){
            throw new CardServiceException(ErrorType.TOTAL_TRAINER_ASSESSMENT_COEFFICIENTS_POINT_RANGE);
        }

        TrainerAssessment trainerAssessment = ITrainerAssessmentMapper.INSTANCE.toSaveTrainerAssessmentCoefficients(trainerAssessmentCoefficients);
        save(trainerAssessment);
        System.out.println("Eğitmen Puanı Katsayıları başarıyla değiştirildi..");
        System.out.println("Güncel Eğitmen Puanı Katsayıları: " + trainerAssessment);
        return ITrainerAssessmentMapper.INSTANCE.toSaveTrainerAssessmentCoefficients(trainerAssessmentCoefficients);
    }
    public double calculateTrainerAssessmentScore(TrainerAssessmentSaveRequestDto dto){
        System.out.println(dto);
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

        double additionOfBehaviorInClass = BEHAVIOR_IN_CLASS_COEFFICIENT * dto.getBehaviorInClass();
        double additionOfCourseInterestLevel = COURSE_INTEREST_LEVEL_COEFFICIENT * dto.getCourseInterestLevel();
        double additionOfCameraOpeningGrade = CAMERA_OPENING_RATE_COEFFICIENT * dto.getCameraOpeningGrade();
        double additionOfInstructorGrade = INSTRUCTOR_GRADE_RATE_COEFFICIENT * dto.getInstructorGrade();
        double additionOfDailyHomeworkGrade = DAILY_HOMEWORK_RATE_COEFFICIENT * dto.getDailyHomeworkGrade();
        double totalTrainerAssessmentScore = additionOfBehaviorInClass + additionOfCourseInterestLevel + additionOfCameraOpeningGrade + additionOfInstructorGrade + additionOfDailyHomeworkGrade;
        return totalTrainerAssessmentScore;
    }
    public TrainerAssessmentSaveResponseDto saveTrainerAssessment(TrainerAssessmentSaveRequestDto dto){
        double score = calculateTrainerAssessmentScore(dto);
        if(score<0 || score>100)
            throw new CardServiceException(ErrorType.TOTAL_TRAINER_ASSESSMENT_POINT_RANGE);
        if (dto.getDescription().isEmpty())
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_EMPTY);

        String studentId = String.valueOf(jwtTokenManager.getIdFromToken(dto.getStudentToken()));
        Optional <String> studentId1 = Optional.ofNullable(studentId);
        if (studentId1.isPresent()) {
            String str = studentId1.get();
            System.out.println("Öğrenci ID: " + str);
        }
        else {
            throw new CardServiceException(ErrorType.STUDENT_ID_NOT_FOUND);
        }
        TrainerAssessment trainerAssessment = ITrainerAssessmentMapper.INSTANCE.toTrainerAssessment(dto);
        trainerAssessment.setStudentId(studentId1.get());
        trainerAssessment.setTotalTrainerAssessmentScore(score);
        List<TrainerAssessment> trainerAssessmentList = iTrainerAssesmentRepository.findAllByStudentId(studentId1.get());
        int mastergorussayisi = 1;
        int trainergorussayisi = 1;
        int admingorussayisi = 1;
        GetIdRoleStatusEmailFromTokenResponseDto tokenDto1 = jwtTokenManager.getIdRoleStatusEmailFromToken(dto.getStudentToken());
        if (!trainerAssessmentList.isEmpty()) {
            for (TrainerAssessment t : trainerAssessmentList) {
                if (tokenDto1.getRole().equals(ERole.MASTER_TRAINER.name())) {
                    mastergorussayisi++;
                }
                if (tokenDto1.getRole().equals(ERole.ASSISTANT_TRAINER.name())) {
                    trainergorussayisi++;
                }
                if (tokenDto1.getRole().equals(ERole.ADMIN.name())) {
                    admingorussayisi++;
                }
            }
            if (tokenDto1.getRole().equals(ERole.MASTER_TRAINER.name())) {
                trainerAssessment.setAssessmentName(mastergorussayisi + ". Master Görüş");
            }
            if (tokenDto1.getRole().equals(ERole.ASSISTANT_TRAINER.name())) {
                trainerAssessment.setAssessmentName(trainergorussayisi + ". Trainer Görüş");
            }
            if (tokenDto1.getRole().equals(ERole.ADMIN.name())) {
                trainerAssessment.setAssessmentName(admingorussayisi + ". Admin Görüş");
            }
        }
        else {

            GetIdRoleStatusEmailFromTokenResponseDto tokenDto2 = jwtTokenManager.getIdRoleStatusEmailFromToken(dto.getStudentToken());

            if (tokenDto2.getRole().equals(ERole.MASTER_TRAINER.name())) {
                trainerAssessment.setAssessmentName("1. Master Görüş");
            }
            if (tokenDto2.getRole().equals(ERole.ASSISTANT_TRAINER.name())) {
                trainerAssessment.setAssessmentName("1. Trainer Görüş");
            }
            if (tokenDto2.getRole().equals(ERole.ADMIN.name())) {
                trainerAssessment.setAssessmentName("1. Admin Görüş");
            }
        }
        save(trainerAssessment);
        System.out.println("Eğitmen Puanı: " + trainerAssessment.getTotalTrainerAssessmentScore());
        System.out.println("Eğitmen Puanı başarıyla kaydedildi..");
        return ITrainerAssessmentMapper.INSTANCE.toSaveTrainerAssessment(trainerAssessment);
    }
    public UpdateTrainerAssessmentResponseDto updateTrainerAssessment(UpdateTrainerAssessmentRequestDto dto) {
        Optional<TrainerAssessment> trainerAssessment = iTrainerAssesmentRepository.findById(dto.getAssessmentId());
        if (trainerAssessment.isEmpty())
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_NOT_FOUND);

        TrainerAssessment updated = trainerAssessment.get();
        updated.setAssessmentName(dto.getAssessmentName());
        updated.setBehaviorInClass(dto.getBehaviorInClass());
        updated.setCourseInterestLevel(dto.getCourseInterestLevel());
        updated.setCameraOpeningGrade(dto.getCameraOpeningGrade());
        updated.setInstructorGrade(dto.getInstructorGrade());
        updated.setDailyHomeworkGrade(dto.getDailyHomeworkGrade());

        if (dto.getDescription().isEmpty())
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_EMPTY);
        updated.setDescription(dto.getDescription());

        if(dto.getTotalTrainerAssessmentScore() == 0.0)
            throw new CardServiceException(ErrorType.POINT_EMPTY);
        updated.setTotalTrainerAssessmentScore(dto.getTotalTrainerAssessmentScore());

        update(updated);
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
                    TrainerAssessmentForTranscriptResponseDto dto = ITrainerAssessmentMapper.INSTANCE.toTrainerAssessmentForTranscriptResponseDto(x);
                    trainerAssessmentForTranscriptResponseDtoList.add(dto);
                }
        );
        return trainerAssessmentForTranscriptResponseDtoList;
    }
}
