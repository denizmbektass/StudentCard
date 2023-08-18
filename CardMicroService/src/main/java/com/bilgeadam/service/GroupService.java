package com.bilgeadam.service;

import com.bilgeadam.dto.request.GroupSaveRequestDto;
import com.bilgeadam.mapper.IGroupMapper;
import com.bilgeadam.repository.IGroupRepository;
import com.bilgeadam.repository.entity.Group;
import com.bilgeadam.repository.view.VwGroupResponseDto;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService extends ServiceManager<Group,String> {
    private final IGroupRepository groupRepository;

    public GroupService(IGroupRepository groupRepository) {
        super(groupRepository);
        this.groupRepository = groupRepository;
    }

    public Boolean saveGroup(GroupSaveRequestDto dto){
        if(!groupRepository.existsByGroupNameIgnoreCase(dto.getGroupName())){
            save(IGroupMapper.INSTANCE.fromGroupSaveRequestDtoToGroup(dto));
            return true;
        }
        throw new RuntimeException("Group Mevcutta Kayıtlı Durumda");
    }

    public List<VwGroupResponseDto> showGroupList(){
        List<VwGroupResponseDto> groupList = groupRepository.findAllGroupList();
        return groupList;
    }

    public Optional<Group> findGroupByGroupName(String groupName){
        Optional<Group> group = groupRepository.findByGroupName(groupName);
        return group;
    }

    public List<VwGroupResponseDto> findAllGroupNames(){
        return groupRepository.findAllGroupList();
    }
}
