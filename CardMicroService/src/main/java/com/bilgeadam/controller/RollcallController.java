package com.bilgeadam.controller;
import com.bilgeadam.dto.request.UpdateRollcallRequestDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.service.RollcallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;
import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(ROLLCALL)
@RequiredArgsConstructor
public class RollcallController {
    private final RollcallService rollcallService;


  /*  @PostMapping(CREATE)
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> createRollcall(@RequestBody @Valid CreateRollcallRequestDto dto){
        return  ResponseEntity.ok(rollcallService.findAllRollcall(token));
    }*/


    @DeleteMapping(DELETE+"/{rollbackId}")
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> deleteRollback(@PathVariable String rollbackId){
        rollcallService.deleteRollcall(rollbackId);
        return ResponseEntity.ok(new MessageResponse("Grup bilgisi başarıyla silindi.."));
    }
    @GetMapping(FIND_ALL+"/title/{token}")
    @CrossOrigin("*")
    public ResponseEntity<Set<String>> getAllTitles(@PathVariable String token){
        return  ResponseEntity.ok(rollcallService.getAllTitles(token));
    }

    @PutMapping(UPDATE)
    @CrossOrigin("*")
    public  ResponseEntity<MessageResponse> updateRollcall(@RequestBody UpdateRollcallRequestDto dto){
        return  ResponseEntity.ok(rollcallService.updateRollcall(dto));
    }

}
