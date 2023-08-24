package com.bilgeadam.service;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.DeleteAssessmentResponseDto;
import com.bilgeadam.dto.response.TrainerAssessmentSaveResponseDto;
import com.bilgeadam.dto.response.UpdateTrainerAssessmentResponseDto;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.exceptions.TrainerAssessmentException;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainerAssessmentService extends ServiceManager<TrainerAssessment,String> {

    private final ITrainerAssessmentRepository iTrainerAssesmentRepository;
    private final JwtTokenManager jwtTokenManager;
    private final ReminderMailProducer reminderMailProducer;

    private final IUserManager userManager;

    public TrainerAssessmentService(ITrainerAssessmentRepository iTrainerAssessmentRepository, JwtTokenManager jwtTokenManager, ReminderMailProducer reminderMailProducer, IUserManager userManager){
        super(iTrainerAssessmentRepository);
        this.iTrainerAssesmentRepository=iTrainerAssessmentRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.reminderMailProducer = reminderMailProducer;
        this.userManager = userManager;
    }

    public TrainerAssessmentSaveResponseDto saveTrainerAssessment(TrainerAssessmentSaveRequestDto dto){
        if (dto.getScore()<0 || dto.getScore()>10)
            throw new TrainerAssessmentException(ErrorType.BAD_REQUEST,"Puan 1 ile 10 arasında olmak zorundadır...");
        if(dto.getDescription().isEmpty())
            throw new TrainerAssessmentException(ErrorType.BAD_REQUEST,"Görüş boş bırakılamaz...");
        if(dto.getScore()==null)
            throw new TrainerAssessmentException(ErrorType.BAD_REQUEST,"Puan boş bırakılamaz...");

        Optional<String> studentId= jwtTokenManager.getIdFromToken(dto.getToken());
        TrainerAssessment trainerAssessment= ITrainerAssesmentMapper.INSTANCE.toTrainerAssesment(dto);
        trainerAssessment.setStudentId(studentId.get());
        List<TrainerAssessment> trainerAssessmentList = iTrainerAssesmentRepository.findAllByStudentId(studentId.get());
        int mastergorussayisi = 1;
        int trainergorussayisi = 1;
        int admingorussayisi = 1;
        if(!trainerAssessmentList.isEmpty()) {
            for (TrainerAssessment t : trainerAssessmentList) {
                if (dto.getToken().getRole().contains(ERole.MASTER)) {
                    mastergorussayisi++;
                }
                if(dto.getToken().getRole().contains(ERole.TRAINER)){
                    trainergorussayisi++;
                }
                if(dto.getToken().getRole().contains(ERole.ADMIN)){
                    admingorussayisi++;
                }
            }
            if(dto.getToken().getRole().contains(ERole.MASTER)){
                trainerAssessment.setAssessmentName(mastergorussayisi + ". Master Görüş");
            }
            if(dto.getToken().getRole().contains(ERole.TRAINER)){
                trainerAssessment.setAssessmentName(trainergorussayisi + ". Trainer Görüş");
            }
            if(dto.getToken().getRole().contains(ERole.ADMIN)){
                trainerAssessment.setAssessmentName(admingorussayisi + ". Admin Görüş");
            }
        }
        else{
            if(dto.getToken().getRole().contains(ERole.MASTER)){
                trainerAssessment.setAssessmentName("1. Master Görüş");
            }
            if(dto.getToken().getRole().contains(ERole.TRAINER)){
                trainerAssessment.setAssessmentName("1. Trainer Görüş");
            }
            if(dto.getToken().getRole().contains(ERole.ADMIN)){
                trainerAssessment.setAssessmentName("1. Admin Görüş");
            }
        }
        trainerAssessment.setAssessmentName(" ");
        save(trainerAssessment);
        return ITrainerAssesmentMapper.INSTANCE.toSaveTrainerAssesment(trainerAssessment);
    }

    public UpdateTrainerAssessmentResponseDto updateTrainerAssessment(UpdateTrainerAssessmentRequestDto dto){
        Optional<TrainerAssessment> trainerAssessment=iTrainerAssesmentRepository.findById(dto.getAssessmentId());
        if(trainerAssessment.isEmpty())
            throw new TrainerAssessmentException(ErrorType.TRAINER_ASSESSMENT_NOT_FOUND);
        if(dto.getDescription().isEmpty())
            throw new TrainerAssessmentException(ErrorType.BAD_REQUEST,"Görüş boş bırakılamaz...");
        if(dto.getScore()==null)
            throw new TrainerAssessmentException(ErrorType.BAD_REQUEST,"Puan boş bırakılamaz...");
        TrainerAssessment trainerAssessmentUpdate = trainerAssessment.get();
        trainerAssessmentUpdate.setScore(dto.getScore());
        trainerAssessmentUpdate.setDescription(dto.getDescription());
        update(trainerAssessmentUpdate);

        return ITrainerAssesmentMapper.INSTANCE.toUpdateTrainerAssessment(trainerAssessment.get());
    }
    public Integer getTrainerAssessmentNote(String studentId){
        return (int) Math.floor(iTrainerAssesmentRepository.findAllByStudentId(studentId).stream()
                .mapToLong(x->x.getScore()).average().orElseThrow(()-> new TrainerAssessmentException(ErrorType.TRAINER_ASSESSMENT_NOT_FOUND)));
    }
    public DeleteAssessmentResponseDto deleteTrainerAssessment(String id){
        Optional<TrainerAssessment> trainerAssessment=iTrainerAssesmentRepository.findById(id);
        if(trainerAssessment.isEmpty())
            throw new TrainerAssessmentException(ErrorType.TRAINER_ASSESSMENT_NOT_FOUND);
        trainerAssessment.get().setEStatus(EStatus.DELETED);
        update(trainerAssessment.get());
        return ITrainerAssesmentMapper.INSTANCE.toDeleteTrainerAssesment(trainerAssessment.get());
    }

    public List<TrainerAssessment> findAllTrainerAssessment(TokenRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getToken());
        return findAll().stream().filter(x->x.getEStatus()==EStatus.ACTIVE && x.getStudentId().equals(studentId.get()))
                .collect(Collectors.toList());
    }
    @Scheduled(fixedRate = 86400000)
    //cron = "0 58 23 15 * ?"
    //fixedRate = 300000
    public void sendReminderMail (){
        List<StudentsMailReminderDto> students = userManager.getStudents().getBody();
        List<TrainersMailReminderDto> trainers = userManager.getTrainers().getBody();
        List<MastersMailReminderDto> masters = userManager.getMasters().getBody();
        System.out.println(" sadadfdg"+" "+trainers);
        System.out.println(" sadadfdg"+" "+students);
        for(StudentsMailReminderDto s : students) {
            Double sure = s.getEgitimSaati();
            List<TrainerAssessment> gorusListesi = iTrainerAssesmentRepository.findAllByStudentId(s.getStudentId()).stream()
                    .filter(x -> x.getEStatus().equals(EStatus.ACTIVE))
                    .collect(Collectors.toList());
            if(sure == null)
                throw new TrainerAssessmentException(ErrorType.TRAINER_ASSESSMENT_NOT_FOUND, "Öğrencinin eğitim süresi değeri null olamaz.");
            if (sure >= 1 && sure < 50) {
                List<TrainerAssessment> masterAssessmentList = gorusListesi.stream().filter(x -> x.getAssessmentName().equals("1. Master Görüş")).collect(Collectors.toList());
                List<TrainerAssessment> trainerAssessmentList = gorusListesi.stream().filter(x -> x.getAssessmentName().equals("1. Trainer Görüş")).collect(Collectors.toList());
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
}
