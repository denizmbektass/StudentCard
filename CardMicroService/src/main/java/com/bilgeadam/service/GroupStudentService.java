package com.bilgeadam.service;


import com.bilgeadam.dto.request.SaveGroupStudentRequestDto;
import com.bilgeadam.dto.request.UpdateGroupStudentRequestDto;
import com.bilgeadam.dto.response.GroupStudentsResponseDto;
import com.bilgeadam.dto.response.ShowGroupInformationListResponseDto;
import com.bilgeadam.repository.entity.Group;
import com.bilgeadam.repository.view.VwGroupResponseDto;
import com.bilgeadam.repository.view.VwGroupStudentResponseDto;

import com.bilgeadam.mapper.IGroupStudentMapper;
import com.bilgeadam.repository.IGroupStudentRepository;
import com.bilgeadam.repository.entity.GroupStudent;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupStudentService extends ServiceManager<GroupStudent, String> {
    private final IGroupStudentRepository groupStudentRepository;
    private final GroupService groupService;

    public GroupStudentService(IGroupStudentRepository addGroupStudentRepository,
                               GroupService groupService) {
        super(addGroupStudentRepository);
        this.groupStudentRepository = addGroupStudentRepository;
        this.groupService = groupService;
    }

    public Boolean saveAddGroupStudent(SaveGroupStudentRequestDto dto){
        Optional<Group> group = groupService.findGroupByGroupName(dto.getGroupName());
        if(group.isEmpty()){
            throw new RuntimeException("Grup isminin veritabanında kaydı bulunmamaktadır.");
        }
        GroupStudent groupStudent = IGroupStudentMapper.INSTANCE.fromSaveGroupStudentRequestDtoToGroupStudent(dto);
        groupStudent.setGroupId(group.get().getGroupId());
        save(groupStudent);
        return true;
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
        System.out.println(vwGroupList);
        List<ShowGroupInformationListResponseDto> dto = new ArrayList<>();
        vwGroupList.stream().forEach(group->{
            Integer groupCount = groupStudentRepository.countAllByGroupId(group.getGroupId());
            if(groupCount>0){
                dto.add(ShowGroupInformationListResponseDto.builder()
                        .groupName(group.getGroupName())
                        .numberOfStudent(groupCount)
                        .build());
            }
        });
        System.out.println(dto);
        return dto;
    }

    public List<GroupStudent> findAllGroupStudents(String groupId){
        List<GroupStudent> groupStudentList = groupStudentRepository.findAllByGroupId(groupId);
        return groupStudentList;
    }





}
