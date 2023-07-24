package com.bilgeadam.controller;

import com.bilgeadam.dto.request.SearchUserRequestDto;
import com.bilgeadam.dto.request.UserRequestDto;
import com.bilgeadam.dto.request.UserUpdateRequestDto;
import com.bilgeadam.dto.response.UserResponseDto;
import com.bilgeadam.repository.entity.User;
import com.bilgeadam.service.UserService;
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
    @PostMapping("search-user")
    public  ResponseEntity<List<User>> searchUser(@RequestBody SearchUserRequestDto dto){
        if(dto.getName()!=null){}


        return  ResponseEntity.ok(userService.searchUser(dto));
    }
}
