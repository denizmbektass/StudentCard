package com.bilgeadam.service;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.exceptions.StudentServiceException;
import com.bilgeadam.converter.UserConverter;
import com.bilgeadam.manager.IAuthManager;
import com.bilgeadam.manager.IBaseManager;
import com.bilgeadam.mapper.IStudentMapper;
import com.bilgeadam.repository.IStudentRepository;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.repository.entity.Student;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.sql.Date;
import java.util.stream.Collectors;

@Service
public class StudentService extends ServiceManager<Student, String> {
    private final IStudentRepository studentRepository;
    private final UserConverter userConverter;
    private final JwtTokenManager jwtTokenManager;
    private final IAuthManager authManager;
    private final MainGroupService mainGroupService;
    private final GroupService groupService;
    private final IBaseManager baseManager;

    public StudentService(IStudentRepository studentRepository,
                          UserConverter userConverter,
                          JwtTokenManager jwtTokenManager,
                          MainGroupService mainGroupService,
                          GroupService groupService,
                          IAuthManager authManager, IBaseManager baseManager) {
        super(studentRepository);
        this.studentRepository = studentRepository;
        this.userConverter = userConverter;
        this.jwtTokenManager = jwtTokenManager;
        this.authManager = authManager;
        this.mainGroupService = mainGroupService;
        this.groupService = groupService;
        this.baseManager = baseManager;
    }

    public Boolean updateStudent(StudentUpdateRequestDto dto) {
        Optional<Student> student = studentRepository.findByStudentId(dto.getStudentId());
        if (student.isEmpty())
            throw new StudentServiceException(ErrorType.STUDENT_NOT_EXIST);
        //TODO mapper'a çevrilmeli
        Student toUpdate = student.get();
        toUpdate.setName(dto.getName());
        toUpdate.setSurname(dto.getSurname());
        //toUpdate.setIdentityNumber(dto.getIdentityNumber());
        toUpdate.setPhoneNumber(dto.getPhoneNumber());
        toUpdate.setAddress(dto.getAddress());
        toUpdate.setBirthDate(dto.getBirthDate());
        toUpdate.setBirthPlace(dto.getBirthPlace());
        toUpdate.setSchool(dto.getSchool());
        toUpdate.setDepartment(dto.getDepartment());
        update(toUpdate);
        return true;
    }


    public Boolean doPassive(String studentId) {
        Optional<Student> student = studentRepository.findByStudentId(studentId);
        if (student.isEmpty())
            throw new StudentServiceException(ErrorType.STUDENT_NOT_EXIST);
        student.get().setStatus(EStatus.PASSIVE);
        update(student.get());
        return true;
    }

    public Boolean safeDelete(String studentId) {
        Optional<Student> student = studentRepository.findByStudentId(studentId);
        if (student.isEmpty())
            throw new StudentServiceException(ErrorType.STUDENT_NOT_EXIST);
        student.get().setStatus(EStatus.DELETED);
        update(student.get());
        return true;
    }

    public SaveStudentResponseDto saveStudent(SaveStudentRequestDto dto) {
        groupService.addSubGroupToGroup(dto.getGroupNameList());
        Student student = IStudentMapper.INSTANCE.toStudent(dto);
        save(student);
        return IStudentMapper.INSTANCE.toStudentResponseDto(student);
    }

    public List<Student> searchStudent(SearchStudentRequestDto dto) {
        return studentRepository.findByNameContainingIgnoreCaseAndSurnameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndPhoneNumberContaining(dto.getName(), dto.getSurname(), dto.getEmail(), dto.getPhoneNumber());
    }

    public String createToken(SelectStudentCreateTokenDto dto) {
        Optional<Student> student = studentRepository.findByStudentId(dto.getStudentId());
        if (student.isPresent() && student.get().getStatus() == EStatus.DELETED) {
            throw new StudentServiceException(ErrorType.TOKEN_NOT_CREATED);
        }
        Optional<String> token = jwtTokenManager.createToken(dto.getStudentId(), dto.getRoleList(), dto.getStatus(), student.get().getGroupNameList(), student.get().getEmail());
        if (token.isEmpty()) throw new StudentServiceException(ErrorType.TOKEN_NOT_CREATED);
        return token.get();
    }

    public String getStudentIdFromToken(String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if (studentId.isEmpty()) throw new StudentServiceException(ErrorType.INVALID_TOKEN);
        return studentId.get();
    }


    public Boolean saveStudentList(List<SaveStudentRequestDto> dtoList) {
        dtoList.stream().forEach(dto -> {
            saveStudent(dto);
        });
        return true;
    }

    public FindStudentProfileResponseDto findStudentProfile(String token) {
        String studentId = jwtTokenManager.getIdFromToken(token).orElseThrow(() -> {
            throw new StudentServiceException(ErrorType.INVALID_TOKEN);
        });
        Student student = studentRepository.findByStudentId(studentId).orElseThrow(() -> {
            throw new StudentServiceException(ErrorType.STUDENT_NOT_EXIST);
        });
        return IStudentMapper.INSTANCE.toFindStudentProfileResponseDto(student);
    }

    public String getNameAndSurnameWithStudentId(String studentId) {
        Optional<Student> optionalStudent = studentRepository.findByStudentId(studentId);
        if (optionalStudent.isEmpty()) {
            throw new StudentServiceException(ErrorType.STUDENT_NOT_EXIST);
        }
        return optionalStudent.get().getName() + " " + optionalStudent.get().getSurname();
    }

    public List<TrainersMailReminderDto> getTrainers() {
        return findAll().stream()
                .filter(x -> x.getStatus().equals(EStatus.ACTIVE) && x.getRoleList().contains(ERole.ASSISTANT_TRAINER))
                .map(x -> TrainersMailReminderDto.builder()
                        .email(x.getEmail())
                        .groupName(x.getGroupNameList())
                        .build())
                .toList();
    }

    public List<MastersMailReminderDto> getMasters() {
        return findAll().stream()
                .filter(x -> x.getStatus().equals(EStatus.ACTIVE) && x.getRoleList().contains(ERole.MASTER_TRAINER))
                .map(x -> MastersMailReminderDto.builder()
                        .email(x.getEmail())
                        .groupName(x.getGroupNameList())
                        .build())
                .toList();
    }

    public List<StudentsMailReminderDto> getStudents() {
        return findAll().stream()
                .filter(x -> x.getStatus().equals(EStatus.ACTIVE) && x.getRoleList().contains(ERole.STUDENT))
                .map(x -> StudentsMailReminderDto.builder()
                        .studentId(x.getStudentId())
                        .name(x.getName())
                        .surname(x.getSurname())
                        .groupName(x.getGroupNameList())
                        .egitimSaati(x.getEgitimSaati())
                        .build())
                .toList();
    }

    public List<FindByGroupNameResponseDto> findByGroupNameList(String groupName) {
        return IStudentMapper.INSTANCE.toFindByGroupNameListResponseDto(studentRepository.findByGroupNameListIgnoreCase(groupName));
    }

    public TranscriptInfo getTranscriptInfoByStudent(String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if (studentId.isEmpty()) throw new StudentServiceException(ErrorType.INVALID_TOKEN);
        Optional<Student> optionalStudent = studentRepository.findByStudentId(studentId.get());
        if (optionalStudent.isEmpty()) throw new StudentServiceException(ErrorType.STUDENT_NOT_EXIST);
        Student student = optionalStudent.get();
        List<Student> students = findAll();
        String masterTrainer = students.stream()
                .filter(x -> x.getGroupNameList().stream().anyMatch(student.getGroupNameList()::contains)
                        && x.getRoleList().contains(ERole.MASTER_TRAINER))
                .map(u -> u.getName() + " " + u.getSurname())
                .collect(Collectors.joining(", "));
        String assistantTrainer = students.stream()
                .filter(x -> x.getGroupNameList().stream().anyMatch(student.getGroupNameList()::contains)
                        && x.getRoleList().contains(ERole.ASSISTANT_TRAINER))
                .map(Student::getName)
                .collect(Collectors.joining(", "));
        return TranscriptInfo.builder().profilePicture(student.getProfilePicture()).startDate(new Date(student.getCreateDate()))
                .endDate(new Date(student.getUpdateDate())).masterTrainer(masterTrainer).assistantTrainer(assistantTrainer).group(student.getGroupNameList().get(0)).build();
    }

    public List<GroupStudentResponseDto> getAllStudentsWithoutInternship(GroupStudentRequestDto dto) {
        List<Student> studentList = studentRepository.findStudentsByGroupNameListAndInternshipStatus(dto.getGroupName(), Arrays.asList(ERole.STUDENT));
        List<GroupStudentResponseDto> groupStudentResponseDtoList = studentList.stream().map(student ->
                IStudentMapper.INSTANCE.toGroupStudentResponseDto(student)
        ).collect(Collectors.toList());
        return groupStudentResponseDtoList;
    }

    public Boolean updateStudentInternShipStatus(String studentId) {
        Student student = studentRepository.findByStudentId(studentId).orElseThrow(() -> {
            throw new StudentServiceException(ErrorType.STUDENT_NOT_EXIST);
        });
        student.setInternShipStatus(EStatus.ACTIVE);
        update(student);
        return true;
    }

    public Boolean updateStudentInternShipStatusToDeleted(String studentId) {
        Student student = studentRepository.findByStudentId(studentId).orElseThrow(() -> {
            throw new StudentServiceException(ErrorType.STUDENT_NOT_EXIST);
        });
        student.setInternShipStatus(EStatus.DELETED);
        update(student);
        return true;
    }

    //Grup isimlerini almak için
    public List<String> findGroupNameForStudent(String studentId) {
        Optional<Student> student = studentRepository.findByStudentId(studentId);
        if (student.isEmpty()) throw new StudentServiceException(ErrorType.STUDENT_NOT_EXIST);
        List<String> groupNames = new ArrayList<>();
        groupNames.addAll(student.get().getGroupNameList());
        return groupNames;
    }

    public String registerManagerForStudent(RegisterRequestDto dto) {
        Student student = IStudentMapper.INSTANCE.toStudentFromRegisterRequestDto(dto);
        student.setRoleList(List.of(ERole.ADMIN));
        save(student);
        return student.getStudentId();
    }

    /**
     * Bu method giriş aksiyonu için yazılmış olup. Giriş yapan öğrenci-admin-trainer'lar'ın profil bilgilerinde kullanılacaktır.
     *
     * @param token
     * @return
     */
    public GetNameAndSurnameByIdResponseDto getStudentNameAndSurnameFromToken(String token) {
        Optional<String> optionalStudentId = jwtTokenManager.getIdFromToken(token);
        if (optionalStudentId.isEmpty()) {
            throw new StudentServiceException(ErrorType.INVALID_TOKEN);
        }
        Optional<Student> student = studentRepository.findByStudentId(optionalStudentId.get());
        if (student.isEmpty()) {
            throw new StudentServiceException(ErrorType.STUDENT_NOT_EXIST);
        }
        return IStudentMapper.INSTANCE.toGetNameAndSurnameByIdResponseDtoFromUser(student.get());
    }


    public Boolean changePassword(ChangePasswordRequestDto dto, String token) {
        Optional<String> optionalUserId = jwtTokenManager.getIdFromTokenForStudentId(token);
        if (optionalUserId.isEmpty()) {
            throw new StudentServiceException(ErrorType.STUDENT_NOT_EXIST);
        }
        GetAuthInfoForChangePassword getAuthInfoForChangePassword = authManager.getAuthInfoForChangePassword(optionalUserId.get()).getBody();
        if (!getAuthInfoForChangePassword.getPassword().equals(dto.getLastPassword())) {
            throw new StudentServiceException(ErrorType.USER_WRONG_PASSWORD);
        }
        if (!dto.getNewPassword().equals(dto.getReNewPassword()))
            throw new StudentServiceException(ErrorType.PASSWORD_UNMATCH);
        ChangePasswordResponseDto dto1 = ChangePasswordResponseDto.builder()
                .newPassword(dto.getNewPassword())
                .userId(optionalUserId.get()).build();
        authManager.changePasswordFromUser(dto1);
        return true;
    }

    /**
     * Bu method giriş işlemi ardından öğrencinin profilini görüntüler --->
     *
     * @param token
     */
    public FindStudentProfileResponseDto getStudentProfile(String token) {
        String studentId = jwtTokenManager.getIdFromToken(token).orElseThrow(
                () -> {
                    throw new StudentServiceException(ErrorType.INVALID_TOKEN);
                });
        Optional<Student> student = studentRepository.findByStudentId(studentId);
        if (student.isEmpty())
            throw new StudentServiceException(ErrorType.STUDENT_NOT_EXIST);
        return IStudentMapper.INSTANCE.toFindStudentProfileResponseDto(student.get());
    }


    public Boolean saveProfileImage(SaveProfileImageRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getToken());
        if (studentId.isEmpty()) throw new StudentServiceException(ErrorType.INVALID_TOKEN);
        Optional<Student> optionalStudent = studentRepository.findByStudentId(studentId.get());
        optionalStudent.get().setProfilePicture(dto.getProfilePicture());
        update(optionalStudent.get());
        return true;
    }


    public String getStudentProfileImage(String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if (studentId.isEmpty()) throw new StudentServiceException(ErrorType.INVALID_TOKEN);
        Optional<Student> optionalStudent = studentRepository.findByStudentId(studentId.get());
        if (optionalStudent.isEmpty()) throw new StudentServiceException(ErrorType.STUDENT_NOT_EXIST);
        String response = optionalStudent.get().getProfilePicture();
        return response;
    }

    public Boolean getAllBaseStudents() {
        List<BaseApiStudentRequestDto> studentDtos = baseManager.findAllBaseStudents().getBody();
        if (studentDtos.isEmpty()) {
            throw new StudentServiceException(ErrorType.STUDENT_NOT_EXIST);
        }
        for (BaseApiStudentRequestDto studentDto : studentDtos) {
            groupService.addSubGroupToGroup(studentDto.getGroupNameList());
            Optional<Student> student = studentRepository.findByEmail(studentDto.getEmail());
            if (student.isEmpty()) {
                Student newUser = IStudentMapper.INSTANCE.studentToUser(studentDto);
                newUser.setRoleList(List.of(ERole.STUDENT));
                studentRepository.save(newUser);
            } else {
                Student existingStudent = student.get();
                if (existingStudent.getUpdateDate() < studentDto.getUpdateDate()) {
                    Student updatedUser = IStudentMapper.INSTANCE.studentToUser(studentDto);
                    updatedUser.setRoleList(List.of(ERole.STUDENT));
                    studentRepository.save(updatedUser);
                } else {
                    if (existingStudent.getRoleList().contains(ERole.CANDIDATE)) {
                        existingStudent.setRoleList(List.of(ERole.STUDENT));
                        studentRepository.save(existingStudent);
                    }
                }
            }
        }
        return true;
    }

    public Optional<Student> findByWithStudentId(String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if (studentId.isEmpty())
            throw new StudentServiceException(ErrorType.INVALID_TOKEN);
        Optional<Student> student = studentRepository.findByStudentId(studentId.get());
        if (student.isEmpty())
            throw new StudentServiceException(ErrorType.STUDENT_NOT_EXIST);
        return student;
    }

    public byte[] readExcel(MultipartFile file) throws IOException {
        List<Student> candidates = new ArrayList<>();
        List<Student> incorrectRecords = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getRowNum() == 0) {
                    continue;
                }
                Iterator<Cell> cellIterator = currentRow.iterator();
                Student candidate = new Student();
                boolean isEmptyRow = true;

                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    int columnIndex = currentCell.getColumnIndex();

                    switch (columnIndex) {
                        case 0:
                            candidate.setRowNumber((long) currentCell.getNumericCellValue());
                            break;
                        case 1:
                            LocalDateTime localDateTimeApplicationDate = currentCell.getLocalDateTimeCellValue();
                            LocalDate applicationDate = (localDateTimeApplicationDate != null) ? localDateTimeApplicationDate.toLocalDate() : null;
                            candidate.setApplicationDate(applicationDate);
                            break;
                        case 2:
                            candidate.setName(currentCell.getStringCellValue());
                            break;
                        case 3:
                            candidate.setChannel(currentCell.getStringCellValue());
                            break;
                        case 4:
                            LocalDateTime localDateTimeBirthDate = currentCell.getLocalDateTimeCellValue();
                            LocalDate birthDate = (localDateTimeBirthDate != null) ? localDateTimeBirthDate.toLocalDate() : null;
                            candidate.setBirthDate(birthDate);
                            break;
                        case 5:
                            candidate.setEmail(currentCell.getStringCellValue());
                            break;
                        case 6:
                            String phoneNumber;
                            if (currentCell.getCellType() == CellType.STRING) {
                                phoneNumber = currentCell.getStringCellValue();
                            } else if (currentCell.getCellType() == CellType.NUMERIC) {
                                phoneNumber = String.valueOf((long) currentCell.getNumericCellValue());
                            } else {
                                phoneNumber = "";
                            }
                            candidate.setPhoneNumber(phoneNumber);
                            break;
                        case 7:
                            candidate.setEducation(currentCell.getStringCellValue());
                            break;
                        case 8:
                            candidate.setEducationStatus(currentCell.getStringCellValue());
                            break;
                        case 9:
                            candidate.setClassName(currentCell.getStringCellValue());
                            break;
                        case 10:
                            candidate.setSchool(currentCell.getStringCellValue());
                            break;
                        case 11:
                            candidate.setDepartment(currentCell.getStringCellValue());
                            break;
                        case 12:
                            candidate.setEnglishLevel(currentCell.getStringCellValue());
                            break;
                        case 13:
                            candidate.setCity(currentCell.getStringCellValue());
                            break;
                        case 14:
                            candidate.setDistrict(currentCell.getStringCellValue());
                            break;
                        case 15:
                            candidate.setEducationBranch(currentCell.getStringCellValue());
                            break;
                        case 16:
                            candidate.setRelevantBranch(currentCell.getStringCellValue());
                            break;
                        case 17:
                            LocalDateTime localDateTimeWorkshopDate = currentCell.getLocalDateTimeCellValue();
                            LocalDate workshopDate = (localDateTimeWorkshopDate != null) ? localDateTimeWorkshopDate.toLocalDate() : null;
                            candidate.setWorkshopDate(workshopDate);
                            break;
                        case 18:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                java.util.Date utilDateValue = currentCell.getDateCellValue();

                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                String formattedTime = sdf.format(utilDateValue);
                                candidate.setWorkshopTime(formattedTime);
                            } else if (currentCell.getCellType() == CellType.STRING) {
                                candidate.setWorkshopTime(currentCell.getStringCellValue());
                            }
                            break;
                        case 19:
                            candidate.setWorkshopPlace(currentCell.getStringCellValue());
                            break;
                        case 20:
                            candidate.setParticipationStatus(currentCell.getStringCellValue());
                            break;
                        case 21:
                            candidate.setExamStatus(currentCell.getStringCellValue());
                            break;
                        case 22:
                            LocalDateTime localDateTimeInterviewDate = currentCell.getLocalDateTimeCellValue();
                            LocalDate interwiewDate = (localDateTimeInterviewDate != null) ? localDateTimeInterviewDate.toLocalDate() : null;
                            candidate.setInterviewDate(interwiewDate);
                            break;
                        case 23:
                            candidate.setInterviewParticipationStatus(currentCell.getStringCellValue());
                            break;
                        case 24:
                            candidate.setInterviewer(currentCell.getStringCellValue());
                            break;
                        case 25:
                            candidate.setEvaluation(currentCell.getStringCellValue());
                            break;
                        case 26:
                            candidate.setExamAndInterviewResult(currentCell.getStringCellValue());
                            break;
                        case 27:
                            candidate.setContract(currentCell.getStringCellValue());
                            break;
                        case 28:
                            candidate.setNotes(currentCell.getStringCellValue());
                            break;
                        default:
                    }
                    if (currentCell.getCellType() != CellType.BLANK) {
                        isEmptyRow = false;
                    }
                }
                if (!isEmptyRow) {
                    candidate.setRoleList(List.of(ERole.CANDIDATE));

                    if ((candidate.getName() != null && candidate.getName() != "") && (candidate.getEmail() != null && candidate.getEmail() != "") &&
                            (candidate.getPhoneNumber() != null && candidate.getPhoneNumber() != "") && (candidate.getRowNumber() != null)
                            && (candidate.getApplicationDate() != null) && (candidate.getBirthDate() != null)
                            && (candidate.getEducation() != null && candidate.getEducation() != "") && (candidate.getEducationStatus() != null && candidate.getEducationStatus() != "")
                            && (candidate.getSchool() != null && candidate.getSchool() != "") && (candidate.getDepartment() != null && candidate.getDepartment() != "")
                            && (candidate.getEnglishLevel() != null && candidate.getEnglishLevel() != "") && (candidate.getCity() != null && candidate.getCity() != "")
                            && (candidate.getDistrict() != null && candidate.getDistrict() != "") && (candidate.getEducationBranch() != null && candidate.getEducationBranch() != "")
                            && (candidate.getRelevantBranch() != null && candidate.getRelevantBranch() != "") && (candidate.getWorkshopDate() != null)
                            && (candidate.getWorkshopTime() != null && candidate.getWorkshopTime() != "") && (candidate.getWorkshopPlace() != null && candidate.getWorkshopPlace() != "")
                            && (candidate.getParticipationStatus() != null && candidate.getParticipationStatus() != "")) {
                        candidates.add(candidate);
                    } else {
                        incorrectRecords.add(candidate);
                    }
                }
            }
            saveAll(candidates);
        }
        if (incorrectRecords.isEmpty()) {
            return null;
        } else {

            return writeExcel(incorrectRecords);
        }
    }

    public byte[] writeExcel(List<Student> users) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");

        Row headerRow = sheet.createRow(0);
        String[] columns = {"S.N.", "Başvuru Tarihi", "İsim Soyisim", "Geliş Kanalı", "Doğum Tarihi", "E-mail Adresi", "Gsm Numarası", "Öğrenim Seviyesi",
                "Öğrenim Durumu", "Sınıfı", "Üniversite", "Bölüm", "İngilizce Bilgisi", "Adres (İL)", "Adres (İLÇE)", "Eğitim Alabileceği Şube",
                "Sizinle İlgilenmesini İstediğiniz Şube", "Planlanan Workshop Tarihi", "Planlanan Workshop Saati", "Planlanan Workshop Yeri",
                "Katılım Durumu", "Sınav Durumu", "Mülakat Tarihi", "Mülakat Katılım Durumu", "Mülakatı Gerçekleştiren",
                "Değerlendirme", "Mülakat / Sınav Sonucu", "Sözleşme", "Açıklama ve Notlar"};

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        int rowNum = 1;
        for (Student user : users) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(user.getRowNumber());
            writeDateCell(row.createCell(1), user.getApplicationDate(), workbook);
            row.createCell(2).setCellValue(user.getName());
            row.createCell(3).setCellValue(user.getChannel());
            writeDateCell(row.createCell(4), user.getBirthDate(), workbook);
            row.createCell(5).setCellValue(user.getEmail());
            row.createCell(6).setCellValue(user.getPhoneNumber());
            row.createCell(7).setCellValue(user.getEducation());
            row.createCell(8).setCellValue(user.getEducationStatus());
            row.createCell(9).setCellValue(user.getClassName());
            row.createCell(10).setCellValue(user.getSchool());
            row.createCell(11).setCellValue(user.getDepartment());
            row.createCell(12).setCellValue(user.getEnglishLevel());
            row.createCell(13).setCellValue(user.getCity());
            row.createCell(14).setCellValue(user.getDistrict());
            row.createCell(15).setCellValue(user.getEducationBranch());
            row.createCell(16).setCellValue(user.getRelevantBranch());
            writeDateCell(row.createCell(17), user.getWorkshopDate(), workbook);
            row.createCell(18).setCellValue(user.getWorkshopTime().toString());
            row.createCell(19).setCellValue(user.getWorkshopPlace());
            row.createCell(20).setCellValue(user.getParticipationStatus());
            row.createCell(21).setCellValue(user.getExamStatus());
            writeDateCell(row.createCell(22), user.getInterviewDate(), workbook);
            row.createCell(23).setCellValue(user.getInterviewParticipationStatus());
            row.createCell(24).setCellValue(user.getInterviewer());
            row.createCell(25).setCellValue(user.getEvaluation());
            row.createCell(26).setCellValue(user.getExamAndInterviewResult());
            row.createCell(27).setCellValue(user.getContract());
            row.createCell(28).setCellValue(user.getNotes());
        }

        for (int i = 0; i < 29; i++) {
            sheet.autoSizeColumn(i);
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    private void writeDateCell(Cell cell, LocalDate date, Workbook workbook) {
        if (date != null) {
            CellStyle dateStyle = workbook.createCellStyle();
            dateStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd.MM.yyyy"));
            cell.setCellValue(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            cell.setCellStyle(dateStyle);
        } else {
            cell.setCellValue("");
        }
    }
}
