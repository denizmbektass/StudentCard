package com.bilgeadam.controller;


import com.bilgeadam.service.GroupAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/group-attendance")
public class GroupAttendanceController {
    private final GroupAttendanceService groupAttendanceService;


}
