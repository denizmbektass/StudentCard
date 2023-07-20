package com.bilgeadam.controller;

import com.bilgeadam.dto.request.AssignmentRequestDto;
import com.bilgeadam.dto.response.AssignmentResponseDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constants.ApiUrls.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(ASSIGNMENT)
public class AssignmentController {
    private final AssignmentService assignmentService;

    @PostMapping(CREATE)
    public ResponseEntity<MessageResponse> createAssignment(@RequestBody AssignmentRequestDto dto){
        return ResponseEntity.ok(assignmentService.createAssignment(dto));
    }
    @GetMapping(FIND_ALL)
    public ResponseEntity<List<AssignmentResponseDto>> findAllAssignments(){
        return ResponseEntity.ok( assignmentService.findAllAssignments());
    }


}
