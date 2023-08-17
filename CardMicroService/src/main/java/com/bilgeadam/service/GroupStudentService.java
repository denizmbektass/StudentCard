package com.bilgeadam.service;


import com.bilgeadam.dto.request.SaveGroupStudentRequestDto;
import com.bilgeadam.repository.view.VwGroupStudentResponseDto;

import com.bilgeadam.mapper.IGroupStudentMapper;
import com.bilgeadam.repository.IGroupStudentRepository;
import com.bilgeadam.repository.entity.GroupStudent;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupStudentService extends ServiceManager<GroupStudent, String> {
    private final IGroupStudentRepository groupStudentRepository;

    public GroupStudentService(IGroupStudentRepository addGroupStudentRepository) {
        super(addGroupStudentRepository);
        this.groupStudentRepository = addGroupStudentRepository;
    }

    public Boolean saveAddGroupStudent(SaveGroupStudentRequestDto dto){
        Optional<GroupStudent> optionalStudent = groupStudentRepository.findByNameAndSurnameAndGroupName(dto.getName(), dto.getSurname(), dto.getGroupName());
        if(optionalStudent.isEmpty()){
            save(IGroupStudentMapper.INSTANCE.fromSaveGroupStudentRequestDtoToGroupStudent(dto));
            return true;
        }
        throw new RuntimeException("USER ALREADY EXIST");
    }

    public List<VwGroupStudentResponseDto> findAllGroupStudentList(){
        List<VwGroupStudentResponseDto> groupStudentList = groupStudentRepository.findAllGroupStudentList();
        return groupStudentList;
    }

    public Boolean deleteGroupStudentById(String groupStudentId){
        if(groupStudentRepository.existsByGroupStudentId(groupStudentId)){
            deleteById(groupStudentId);
            return true;
        }
        throw new RuntimeException("Bu ID'ye sahip mevcut bir öğrenci bulunmamaktadır.");
    }



}
