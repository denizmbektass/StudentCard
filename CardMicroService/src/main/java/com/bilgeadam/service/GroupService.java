package com.bilgeadam.service;

import com.bilgeadam.dto.request.GroupSaveRequestDto;
import com.bilgeadam.dto.response.AttendanceSearchResponseDto;
import com.bilgeadam.mapper.IGroupMapper;
import com.bilgeadam.repository.IGroupRepository;
import com.bilgeadam.repository.entity.Group;
import com.bilgeadam.repository.view.VwGroupResponseDto;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

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

    public List<AttendanceSearchResponseDto> showAttendanceSearchList(String groupId){
        Optional<Group> optionalGroup = findById(groupId);
        if(optionalGroup.isEmpty()){
            throw new RuntimeException("Grup Bulunamadı");
        }
        List<AttendanceSearchResponseDto> responseDtoList = new ArrayList<>();
            Date start = optionalGroup.get().getStartingDate();
            Date end = optionalGroup.get().getEndingDate();
            String groupName = optionalGroup.get().getGroupName();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            while(start.before(end) || start.equals(end)){
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
                    responseDtoList.add(AttendanceSearchResponseDto.builder()
                            .currentDate(start)
                            .groupName(groupName)
                            .build());
                }
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                start = calendar.getTime();
            }
        return responseDtoList;
    }

    public Optional<Group> findById(String groupId){
        return groupRepository.findById(groupId);
    }
}
