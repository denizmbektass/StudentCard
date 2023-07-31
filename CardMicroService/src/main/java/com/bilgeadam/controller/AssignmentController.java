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
    @PostMapping(FIND_ALL)
    public ResponseEntity<List<AssignmentResponseDto>> findAllAssignments(@RequestBody @Valid FindByStudentIdRequestDto dto){
        return ResponseEntity.ok( assignmentService.findAllAssignments(dto));
    }
    @PutMapping(UPDATE)
    public ResponseEntity<MessageResponse> updateAssignment(@RequestBody UpdateAssignmentRequestDto dto){
        assignmentService.updateAssignment(dto);
        return ResponseEntity.ok(new MessageResponse("Ödev başarıyla güncellendi.."));
    }
    @DeleteMapping(DELETE)
    public ResponseEntity<MessageResponse> deleteAssignment(@RequestParam String assignmentId){
        assignmentService.deleteAssignment(assignmentId);
        return ResponseEntity.ok(new MessageResponse("Ödev başarıyla silindi.."));
    }
}
