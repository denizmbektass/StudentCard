package com.bilgeadam.manager;

import com.bilgeadam.dto.request.TranscriptInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:4041/api/v1/user",name = "card-user")
public interface IUserManager {

    @GetMapping("get-name-and-surname-with-id/{userId}")
    ResponseEntity<String> getNameAndSurnameWithId(@PathVariable String userId);

    @GetMapping("get-transcript-info/{token}")
    ResponseEntity<TranscriptInfo> getTranscriptInfoByUser(@PathVariable String token);

}
