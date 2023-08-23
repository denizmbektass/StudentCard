package com.bilgeadam.controller;

import com.bilgeadam.dto.request.AssignmentRequestDto;
import com.bilgeadam.dto.request.FindByStudentIdRequestDto;
import com.bilgeadam.dto.request.UpdateAssignmentRequestDto;
import com.bilgeadam.dto.response.AssignmentResponseDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static com.bilgeadam.constants.ApiUrls.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(ASSIGNMENT)
public class AssignmentController {
    private final AssignmentService assignmentService;
    //@PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANT_TRAINER','MASTER_TRAINER')")
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
    //@PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANT_TRAINER','MASTER_TRAINER')")
    @PutMapping(UPDATE)
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> updateAssignment(@RequestBody UpdateAssignmentRequestDto dto){
        assignmentService.updateAssignment(dto);
        return ResponseEntity.ok(new MessageResponse("Ödev başarıyla güncellendi.."));
    }
    //@PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANT_TRAINER','MASTER_TRAINER')")
    @DeleteMapping(DELETE+"/{assignmentId}")
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> deleteAssignment(@PathVariable String assignmentId){
        assignmentService.deleteAssignment(assignmentId);
        return ResponseEntity.ok(new MessageResponse("Ödev başarıyla silindi.."));
    }
    @GetMapping(FIND_ALL+"/title/{token}")
    @CrossOrigin("*")
    public ResponseEntity<Set<String>> getAllTitles(@PathVariable String token){
        return ResponseEntity.ok(assignmentService.getAllTitles(token));
    }
}
