package com.bilgeadam.controller;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.FindStudentProfileResponseDto;
import com.bilgeadam.dto.response.TranscriptInfo;
import com.bilgeadam.dto.response.UserResponseDto;
import com.bilgeadam.repository.entity.User;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PutMapping(UPDATE)
    public ResponseEntity<Boolean>updateUser(@RequestBody @Valid UserUpdateRequestDto dto){
        return ResponseEntity.ok(userService.updateUser(dto));
    }
    @PutMapping(DO_PASSIVE)
    public ResponseEntity<Boolean> doPassive(@RequestParam String id){
        return ResponseEntity.ok(userService.doPassive(id));
    }
    @PutMapping(SAFE_DELETE)
    public ResponseEntity<Boolean> safeDelete(@RequestParam String id){
        return ResponseEntity.ok(userService.safeDelete(id));
    }

    @PutMapping(SAVE)
    public  ResponseEntity<UserResponseDto> save(@RequestBody UserRequestDto dto){
        return ResponseEntity.ok(userService.save(dto));
    }
    @GetMapping("mail-reminder")
    public  ResponseEntity<List<TrainersMailReminderDto>> getTrainers(){
        return ResponseEntity.ok(userService.getTrainers());
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
    //@PreAuthorize("hasAuthority('MANAGER')")
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

    @GetMapping("get-transcript-info/{token}")
    public ResponseEntity<TranscriptInfo> getTranscriptInfoByUser(@PathVariable String token){
        return ResponseEntity.ok(userService.getTranscriptInfoByUser(token));
    }


}
