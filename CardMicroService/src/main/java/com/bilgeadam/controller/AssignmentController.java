package com.bilgeadam.controller;

import com.bilgeadam.dto.request.AssignmentRequestDto;
import com.bilgeadam.dto.request.FindByStudentIdRequestDto;
import com.bilgeadam.dto.request.UpdateAssignmentRequestDto;
import com.bilgeadam.dto.response.AssignmentResponseDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.bilgeadam.constants.ApiUrls.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(ASSIGNMENT)
public class AssignmentController {
    private final AssignmentService assignmentService;

    @PostMapping(CREATE)
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> createAssignment(@RequestBody @Valid AssignmentRequestDto dto){
        assignmentService.createAssignment(dto);
        return ResponseEntity.ok(new MessageResponse("Ödev başarıyla kaydedildi.."));
    }
    @GetMapping(FIND_ALL+"/{token}")
    @CrossOrigin("*")
    public ResponseEntity<List<AssignmentResponseDto>> findAllAssignments(@PathVariable String token){
        return ResponseEntity.ok( assignmentService.findAllAssignments(token));
    }
    @PutMapping(UPDATE)
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> updateAssignment(@RequestBody UpdateAssignmentRequestDto dto){
        assignmentService.updateAssignment(dto);
        return ResponseEntity.ok(new MessageResponse("Ödev başarıyla güncellendi.."));
    }
    @DeleteMapping(DELETE)
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> deleteAssignment(@RequestParam String assignmentId){
        assignmentService.deleteAssignment(assignmentId);
        return ResponseEntity.ok(new MessageResponse("Ödev başarıyla silindi.."));
    }
    @GetMapping(FIND_ALL+"/title/{token}")
    public ResponseEntity<List<String>> getAllTitles(@PathVariable String token){
        return ResponseEntity.ok(assignmentService.getAllTitles(token));
    }
}
