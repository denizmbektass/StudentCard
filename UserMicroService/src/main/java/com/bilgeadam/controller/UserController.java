package com.bilgeadam.controller;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.repository.entity.User;
import com.bilgeadam.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //@PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin("*")
    @PutMapping(UPDATE)
    public ResponseEntity<Boolean>updateUser(@RequestBody @Valid UserUpdateRequestDto dto){
        return ResponseEntity.ok(userService.updateUser(dto));
    }
    //@PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin("*")
    @PutMapping(DO_PASSIVE)
    public ResponseEntity<Boolean> doPassive(@RequestParam String id){
        return ResponseEntity.ok(userService.doPassive(id));
    }
    //@PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin("*")
    @PutMapping(SAFE_DELETE)
    public ResponseEntity<Boolean> safeDelete(@RequestParam String id){
        return ResponseEntity.ok(userService.safeDelete(id));
    }
    //@PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin("*")
    @PutMapping(SAVE)
    public  ResponseEntity<UserResponseDto> save(@RequestBody UserRequestDto dto){
        return ResponseEntity.ok(userService.save(dto));
    }

    @CrossOrigin("*")
    @GetMapping("mail-reminder-get-trainers")
    public  ResponseEntity<List<TrainersMailReminderDto>> getTrainers(){
        return ResponseEntity.ok(userService.getTrainers());
    }

    @CrossOrigin("*")
    @GetMapping("mail-reminder-get-masters")
    public  ResponseEntity<List<MastersMailReminderDto>> getMasters(){
        return ResponseEntity.ok(userService.getMasters());
    }

    @CrossOrigin("*")
    @GetMapping("mail-reminder-get-students")
    public  ResponseEntity<List<StudentsMailReminderDto>> getStudents(){
        return ResponseEntity.ok(userService.getStudents());
    }
    //@PreAuthorize("hasAuthority('MANAGER')")
    @CrossOrigin("*")
    @PostMapping("search-user")
    public  ResponseEntity<List<User>> searchUser(@RequestBody SearchUserRequestDto dto){
        return  ResponseEntity.ok(userService.searchUser(dto));
    }

    //@PreAuthorize("hasAuthority('MANAGER')")
    @CrossOrigin("*")
    @PostMapping("search-create-token")
    public  ResponseEntity<String> createToken( @RequestBody SelectUserCreateTokenDto dto){
        return  ResponseEntity.ok(userService.createToken(dto));
    }
    //@PreAuthorize("hasAuthority('MANAGER')")
    @CrossOrigin("*")
    @PostMapping("get-id-from-token")
    public  ResponseEntity<String> getIdFromToken( String token){
        return  ResponseEntity.ok(userService.getIdFromToken(token));
    }
    //@PreAuthorize("hasAuthority('MANAGER')")
    @CrossOrigin("*")
    @GetMapping ("find-student-profile/{token}")
    public  ResponseEntity<FindStudentProfileResponseDto> findStudentProfile(@PathVariable String token){
        return  ResponseEntity.ok(userService.findStudentProfile(token));
    }
    //@PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin("*")
    @PutMapping("save-user-list")
    public  ResponseEntity<Boolean> saveUserList(@RequestBody List<UserRequestDto> dtoList){
        return  ResponseEntity.ok(userService.saveUserList(dtoList));
    }
    //@PreAuthorize("hasAuthority('MANAGER')")
    @CrossOrigin("*")
    @GetMapping("get-name-and-surname-with-id/{userId}")
    public ResponseEntity<String> getNameAndSurnameWithId(@PathVariable String userId){
        return ResponseEntity.ok(userService.getNameAndSurnameWithId(userId));
    }
    @CrossOrigin("*")
    @GetMapping("find-by-group-name-list/{groupName}")
    public ResponseEntity<List<FindByGroupNameResponseDto>> findByGroupNameList(@PathVariable String groupName) {
        return  ResponseEntity.ok(userService.findByGroupNameList(groupName));
    }

    @CrossOrigin("*")
    @GetMapping("get-transcript-info/{token}")
    public ResponseEntity<TranscriptInfo> getTranscriptInfoByUser(@PathVariable String token){
        return ResponseEntity.ok(userService.getTranscriptInfoByUser(token));
    }

    @CrossOrigin("*")
    @PostMapping("get-all-students-without-internship")
    public ResponseEntity<List<GroupStudentResponseDto>> getAllStudentsWithoutInternship(@RequestBody GroupStudentRequestDto dto){
        return ResponseEntity.ok(userService.getAllStudentsWithoutInternship(dto));
    }

    @CrossOrigin("*")
    @PutMapping("update-user-internship-status-to-active/{userId}")
    public ResponseEntity<Boolean> updateUserInternShipStatusToActive(@PathVariable String userId){
        return ResponseEntity.ok(userService.updateUserInternShipStatus(userId));
    }

    @CrossOrigin("*")
    @PutMapping("update-user-internship-status-to-deleted/{userId}")
    public ResponseEntity<Boolean> updateUserInternShipStatusToDeleted(@PathVariable String userId){
        return ResponseEntity.ok(userService.updateUserInternShipStatusToDeleted(userId));
    }

    @Hidden
    @GetMapping("get-group-name-for-student/{userId}")
    public ResponseEntity<List<String>> findGroupNameForStudent(@PathVariable String userId){
        return ResponseEntity.ok(userService.findGroupNameForStudent(userId));
    }

}
