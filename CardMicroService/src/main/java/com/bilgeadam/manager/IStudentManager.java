package com.bilgeadam.manager;

import com.bilgeadam.dto.request.MastersMailReminderDto;
import com.bilgeadam.dto.request.StudentsMailReminderDto;
import com.bilgeadam.dto.request.TranscriptInfo;
import com.bilgeadam.dto.request.TrainersMailReminderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(url = "${student-service.url}", name = "card-student")
public interface IStudentManager {

    @GetMapping("/get-name-and-surname-with-id/{studentId}")
    ResponseEntity<String> getNameAndSurnameWithStudentId(@PathVariable String studentId);

    @GetMapping("mail-reminder-get-trainers")
    ResponseEntity<List<TrainersMailReminderDto>> getTrainers();

    @GetMapping("mail-reminder-get-students")
    ResponseEntity<List<StudentsMailReminderDto>> getStudents();

    @GetMapping("mail-reminder-get-masters")
    ResponseEntity<List<MastersMailReminderDto>> getMasters();

    @GetMapping("get-transcript-info/{token}")
    ResponseEntity<TranscriptInfo> getTranscriptInfoByStudent(@PathVariable String token);

    @PutMapping("/update-student-internship-status-to-active/{studentId}")
    ResponseEntity<Boolean> updateStudentInternShipStatusToActive(@PathVariable String studentId);

    @PutMapping("/update-student-internship-status-to-deleted/{studentId}")
    ResponseEntity<Boolean> updateStudentInternShipStatusToDeleted(@PathVariable String studentId);

    @GetMapping("/get-group-name-for-student/{studentId}")
    ResponseEntity<List<String>> findGroupNameForStudent(@PathVariable String studentId);

    @GetMapping("/get-all-base-students")
    ResponseEntity<String> getAllBaseStudents();
}
