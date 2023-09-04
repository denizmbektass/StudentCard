package com.bilgeadam.manager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient(url = "http://localhost:4041/api/v1/main-group",name = "card-mainGroup")
public interface IMainGroupManager {

    @GetMapping("/get-all-group-names-with-group-id/{mainGroupId}")
    ResponseEntity<Set<String>> getSubGroupNamesByMainGroupId(@PathVariable String mainGroupId);
}
