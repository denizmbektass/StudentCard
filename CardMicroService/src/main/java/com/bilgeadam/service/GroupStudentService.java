package com.bilgeadam.service;


import com.bilgeadam.dto.request.GroupStudentAttendanceRequestDto;
import com.bilgeadam.dto.request.SaveGroupStudentRequestDto;
import com.bilgeadam.dto.request.UpdateGroupStudentAttendanceRequestDto;
import com.bilgeadam.dto.request.UpdateGroupStudentRequestDto;
import com.bilgeadam.dto.response.GroupStudentAttendanceResponseDto;
import com.bilgeadam.dto.response.GroupStudentsResponseDto;
import com.bilgeadam.dto.response.ShowGroupInformationListResponseDto;
import com.bilgeadam.manager.IUserManager;
import com.bilgeadam.mapper.IGroupAttendanceMapper;
import com.bilgeadam.repository.entity.Group;
import com.bilgeadam.repository.entity.GroupAttendance;
import com.bilgeadam.repository.view.VwGroupResponseDto;
import com.bilgeadam.repository.view.VwGroupStudentResponseDto;

import com.bilgeadam.mapper.IGroupStudentMapper;
import com.bilgeadam.repository.IGroupStudentRepository;
import com.bilgeadam.repository.entity.GroupStudent;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupStudentService extends ServiceManager<GroupStudent, String> {
    private final IGroupStudentRepository groupStudentRepository;
    private final GroupService groupService;
    private final IUserManager userManager;
    private final GroupAttendanceService groupAttendanceService;


    public GroupStudentService(IGroupStudentRepository addGroupStudentRepository,
                               GroupService groupService,
                               IUserManager userManager,
                               GroupAttendanceService groupAttendanceService) {
        super(addGroupStudentRepository);
        this.groupStudentRepository = addGroupStudentRepository;
        this.groupService = groupService;
        this.userManager = userManager;
        this.groupAttendanceService = groupAttendanceService;
    }

    public Boolean saveAddGroupStudent(SaveGroupStudentRequestDto dto){
        Optional<Group> group = groupService.findGroupByGroupName(dto.getGroupName());
        if(group.isEmpty()){
            throw new RuntimeException("Grup isminin veritabanında kaydı bulunmamaktadır.");
        }
        if(!groupStudentRepository.existsByUserId(dto.getUserId()) && userManager.updateUserInternShipStatusToActive(dto.getUserId()).getBody()) {
            GroupStudent groupStudent = IGroupStudentMapper.INSTANCE.fromSaveGroupStudentRequestDtoToGroupStudent(dto);
            groupStudent.setGroupId(group.get().getGroupId());
            save(groupStudent);
            List<GroupAttendance> groupAttendanceList = groupAttendanceService.findByGroupId(groupStudent.getGroupId());
            groupAttendanceList.forEach(groupAttendance -> {
                groupAttendance.getGroupStudents().put(groupStudent.getName() + " " + groupStudent.getSurname(), false);
                groupAttendanceService.update(groupAttendance);
            });
            return true;
        }
        throw new RuntimeException("Öğrencinin veritabanında kaydı bulunmamaktadır.");
    }

    public List<GroupStudentsResponseDto> findAllGroupStudentList(){
        List<VwGroupStudentResponseDto> vwGroupStudentList = groupStudentRepository.findAllGroupStudentList();
        List<GroupStudentsResponseDto> groupStudentList = vwGroupStudentList.stream()
                .map(x -> {
                    Group group = groupService.findById(x.getGroupId())
                            .orElseThrow(() -> new RuntimeException("Grup veritabanında mevcut değildir."));
                    return GroupStudentsResponseDto.builder()
                            .groupName(group.getGroupName())
                            .name(x.getName())
                            .surname(x.getSurname())
                            .groupStudentId(x.getGroupStudentId())
                            .build();
                })
                .collect(Collectors.toList());
        return groupStudentList;
    }

    public Boolean deleteGroupStudentById(String groupStudentId){
        if(groupStudentRepository.existsByGroupStudentId(groupStudentId)){
            GroupStudent groupStudent = groupStudentRepository.findById(groupStudentId).orElseThrow(()->{
                throw new RuntimeException("NO STUDENT FOUND");
            });
            userManager.updateUserInternShipStatusToDeleted(groupStudent.getUserId());
            List<GroupAttendance> groupAttendanceList = groupAttendanceService.findByGroupId(groupStudent.getGroupId());
            groupAttendanceList.forEach(groupAttendance -> {
                groupAttendance.getGroupStudents().remove(groupStudent.getName() + " " + groupStudent.getSurname());
                groupAttendanceService.update(groupAttendance);
            });
            deleteById(groupStudentId);
            return true;
        }
        throw new RuntimeException("Bu ID'ye sahip mevcut bir öğrenci bulunmamaktadır.");
    }

    public Boolean updateGroupStudent(UpdateGroupStudentRequestDto dto){
        Optional<GroupStudent> groupStudent = findById(dto.getGroupStudentId());
        if(groupStudent.isEmpty()){
            throw new RuntimeException("Veritabanında kayıtlı böyle bir öğrenci bulunmamaktadır.");
        }
        Optional<Group> group = groupService.findGroupByGroupName(dto.getGroupName());
        if(group.isEmpty()){
            throw new RuntimeException("Grup isminin veritabanında kaydı bulunmamaktadır.");
        }
        groupStudent.get().setGroupId(group.get().getGroupId());
        groupStudent.get().setName(dto.getName());
        groupStudent.get().setSurname(dto.getSurname());
        update(groupStudent.get());
        return true;
    }

    public List<ShowGroupInformationListResponseDto> showGroupInformationList(){
        List<VwGroupResponseDto> vwGroupList = groupService.findAllGroupNames();
        List<ShowGroupInformationListResponseDto> dto = new ArrayList<>();
        vwGroupList.stream().forEach(group->{
            Integer groupCount = groupStudentRepository.countAllByGroupId(group.getGroupId());
            if(groupCount>0){
                dto.add(ShowGroupInformationListResponseDto.builder()
                        .groupId(group.getGroupId())
                        .groupName(group.getGroupName())
                        .numberOfStudent(groupCount)
                        .build());
            }
        });
        return dto;
    }



    public GroupStudentAttendanceResponseDto showGroupStudentAttendance(GroupStudentAttendanceRequestDto dto){
        Optional<GroupAttendance> optionalGroupAttendance = groupAttendanceService.findByAttendanceDateAndGroupId(dto.getCurrentDate(), dto.getGroupId());
        Optional<Group> optionalGroup = groupService.findById(dto.getGroupId());
        if(optionalGroup.isEmpty()) throw new RuntimeException("Grup veritabanında kayıtlı değildir.");
        GroupStudentAttendanceResponseDto groupStudentAttendanceResponseDto;
        if(optionalGroupAttendance.isEmpty()){
            //Database'de ilk defa kaydı açılacaksa
            //GrupStudent'lar çağrıldı
            List<GroupStudent> groupStudentList = groupStudentRepository.findAllByGroupId(dto.getGroupId());
            //DB'ye kayıt yapılacak
            Map<String,Boolean> groupStudents = groupStudentList.stream()
                    .collect(Collectors.toMap(student -> student.getName() + " " + student.getSurname(),attendance -> false));
            GroupAttendance groupAttendance = GroupAttendance.builder()
                    .groupId(dto.getGroupId())
                    .attendanceDate(dto.getCurrentDate())
                    .groupStudents(groupStudents)
                    .build();
            groupAttendanceService.save(groupAttendance);
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

    /*
    public Boolean updateGroupAttendance(UpdateGroupStudentAttendanceRequestDto dto){
        Optional<GroupAttendance> optionalGroupAttendance = groupAttendanceService
                .findByAttendanceDateAndGroupId(dto.getAttendanceDate(), dto.getGroupId());
        if (optionalGroupAttendance.isEmpty())
            throw new RuntimeException("Grup attendance veritabanında kayıtlı değildir");
        if(!dto.getGroupId().equals(optionalGroupAttendance.get().getGroupId())) {
            Map<String, Boolean> groupStudentList = optionalGroupAttendance.get().getGroupStudents();
            System.out.println(dto);
            dto.getGroupStudents().entrySet().forEach(x -> {
                groupStudentList.put(x.getKey(), x.getValue());
            });
            System.out.println(dto);
            optionalGroupAttendance.get().setGroupStudents(groupStudentList);
            groupAttendanceService.update(optionalGroupAttendance.get());
        }
        return true;
    }
    */




}
