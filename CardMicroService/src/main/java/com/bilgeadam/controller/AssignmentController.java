package com.bilgeadam.controller;

import com.bilgeadam.dto.request.AssignmentRequestDto;
import com.bilgeadam.dto.request.FindByStudentIdRequestDto;
import com.bilgeadam.dto.request.UpdateAssignmentRequestDto;
import com.bilgeadam.dto.response.AssignmentResponseDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.service.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
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


    @Operation(summary = "Ödev oluşturma işlemi",
            description = "Belirtilen DTO kullanılarak bir ödev oluşturur.")
    @PostMapping(CREATE)
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> createAssignment(@RequestBody @Valid AssignmentRequestDto dto){
        assignmentService.createAssignment(dto);
        return ResponseEntity.ok(new MessageResponse("Ödev başarıyla kaydedildi.."));
    }

    @Operation(summary = "Tüm ödevleri listeleme işlemi",
            description = "Belirtilen token kullanılarak tüm ödevleri listeler.")
    @GetMapping(FIND_ALL+"/{token}")
    @CrossOrigin("*")
    public ResponseEntity<List<AssignmentResponseDto>> findAllAssignments(@PathVariable String token){
        return ResponseEntity.ok( assignmentService.findAllAssignments(token));
    }


    //@PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANT_TRAINER','MASTER_TRAINER')")
    @Operation(summary = "Ödev güncelleme işlemi",
            description = "Belirtilen DTO kullanılarak bir ödevi günceller.")
    @PutMapping(UPDATE)
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> updateAssignment(@RequestBody UpdateAssignmentRequestDto dto){
        assignmentService.updateAssignment(dto);
        return ResponseEntity.ok(new MessageResponse("Ödev başarıyla güncellendi.."));
    }


    //@PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANT_TRAINER','MASTER_TRAINER')")
    @Operation(summary = "Ödev silme işlemi",
            description = "Belirtilen DTO kullanılarak bir ödevi siler.")
    @DeleteMapping(DELETE+"/{assignmentId}")
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> deleteAssignment(@PathVariable String assignmentId){
        assignmentService.deleteAssignment(assignmentId);
        return ResponseEntity.ok(new MessageResponse("Ödev başarıyla silindi.."));
    }


    @Operation(summary = "Tüm başlıkları alma işlemi",
            description = "Belirtilen token kullanılarak tüm ödev başlıklarını alır.")
    @GetMapping(FIND_ALL+"/title/{token}")
    @CrossOrigin("*")
    public ResponseEntity<Set<String>> getAllTitles(@PathVariable String token){
        return ResponseEntity.ok(assignmentService.getAllTitles(token));
    }
    @Operation(summary = "Ödevlerin not ortalamasını bulma işlemi",
            description = "studentId ile tüm notlara erişilir ve ortalaması alınır. ")
    @GetMapping(ASSIGNMENT_AVERAGE + "/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<Double> getAssignmentAverage(@PathVariable String studentId){
        return ResponseEntity.ok(assignmentService.getAssignmentAverage(studentId));
    }
}
