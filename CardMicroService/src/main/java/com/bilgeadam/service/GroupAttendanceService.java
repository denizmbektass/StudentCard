package com.bilgeadam.service;

import com.bilgeadam.dto.request.GroupStudentAttendanceRequestDto;
import com.bilgeadam.dto.request.UpdateGroupStudentAttendanceRequestDto;
import com.bilgeadam.dto.response.GroupStudentAttendanceResponseDto;
import com.bilgeadam.mapper.IGroupAttendanceMapper;
import com.bilgeadam.repository.IGroupAttendanceRepository;
import com.bilgeadam.repository.entity.Group;
import com.bilgeadam.repository.entity.GroupAttendance;
import com.bilgeadam.repository.entity.GroupStudent;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupAttendanceService extends ServiceManager<GroupAttendance,String> {
    private final IGroupAttendanceRepository groupAttendanceRepository;
    private final GroupStudentService groupStudentService;
    private final GroupService groupService;

    public GroupAttendanceService(IGroupAttendanceRepository groupAttendanceRepository,
                                  GroupStudentService groupStudentService,
                                  GroupService groupService) {
        super(groupAttendanceRepository);
        this.groupAttendanceRepository = groupAttendanceRepository;
        this.groupStudentService = groupStudentService;
        this.groupService = groupService;
    }

    public GroupStudentAttendanceResponseDto showGroupStudentAttendance(GroupStudentAttendanceRequestDto dto){
        Optional<GroupAttendance> optionalGroupAttendance = groupAttendanceRepository.findByAttendanceDateAndGroupId(dto.getCurrentDate(), dto.getGroupId());
        System.out.println(optionalGroupAttendance);
        Optional<Group> optionalGroup = groupService.findById(dto.getGroupId());
        if(optionalGroup.isEmpty()) throw new RuntimeException("Grup veritabanında kayıtlı değildir.");
        GroupStudentAttendanceResponseDto groupStudentAttendanceResponseDto;
        if(optionalGroupAttendance.isEmpty()){
            //Database'de ilk defa kaydı açılacaksa
            //GrupStudent'lar çağrıldı
            List<GroupStudent> groupStudentList = groupStudentService.findAllGroupStudents(dto.getGroupId());
            //DB'ye kayıt yapılacak
            Map<String,Boolean> groupStudents = groupStudentList.stream()
                    .collect(Collectors.toMap(student -> student.getName() + " " + student.getSurname(),attendance -> false));
            GroupAttendance groupAttendance = GroupAttendance.builder()
                    .groupId(dto.getGroupId())
                    .attendanceDate(dto.getCurrentDate())
                    .groupStudents(groupStudents)
                    .build();
            save(groupAttendance);
            groupStudentAttendanceResponseDto = IGroupAttendanceMapper.INSTANCE
                    .fromGroupAttendanceToGroupStudentAttendanceResponseDto(groupAttendance);
        }else{
            //Return işlemi hazırlanacak
            groupStudentAttendanceResponseDto = IGroupAttendanceMapper.INSTANCE
                    .fromGroupAttendanceToGroupStudentAttendanceResponseDto(optionalGroupAttendance.get());
        }
        groupStudentAttendanceResponseDto.setGroupName(optionalGroup.get().getGroupName());
        return groupStudentAttendanceResponseDto;
    }

    public Boolean updateGroupAttendance(UpdateGroupStudentAttendanceRequestDto dto){
        System.out.println(dto);

        Optional<GroupAttendance> optionalGroupAttendance = groupAttendanceRepository
                    .findByAttendanceDateAndGroupId(dto.getAttendanceDate(), dto.getGroupId());
        System.out.println(optionalGroupAttendance.get());
        System.out.println("SES");
        if (optionalGroupAttendance.isEmpty())
                throw new RuntimeException("Grup attendance veritabanında kayıtlı değildir");
        Map<String, Boolean> groupStudentList = optionalGroupAttendance.get().getGroupStudents();
        dto.getGroupStudents().entrySet().forEach(x -> {
                groupStudentList.put(x.getKey(), x.getValue());
        });
        optionalGroupAttendance.get().setGroupStudents(groupStudentList);
        System.out.println(optionalGroupAttendance.get());
        update(optionalGroupAttendance.get());
        return true;
    }







}
