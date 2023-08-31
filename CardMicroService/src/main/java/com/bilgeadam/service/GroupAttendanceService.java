package com.bilgeadam.service;


import com.bilgeadam.repository.IGroupAttendanceRepository;
import com.bilgeadam.repository.entity.GroupAttendance;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GroupAttendanceService extends ServiceManager<GroupAttendance,String> {
    private final IGroupAttendanceRepository groupAttendanceRepository;

    public GroupAttendanceService(IGroupAttendanceRepository groupAttendanceRepository) {
        super(groupAttendanceRepository);
        this.groupAttendanceRepository = groupAttendanceRepository;

    }

    public Optional<GroupAttendance> findByAttendanceDateAndGroupId(Date currentDate,String groupId){
        Optional<GroupAttendance> optionalGroupAttendance = groupAttendanceRepository.findByAttendanceDateAndGroupId(currentDate, groupId);
        return optionalGroupAttendance;
    }

    public List<GroupAttendance> findByGroupId(String groupId){
        return groupAttendanceRepository.findByGroupId(groupId);
    }









}
