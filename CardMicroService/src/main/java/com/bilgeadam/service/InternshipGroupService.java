package com.bilgeadam.service;

import com.bilgeadam.dto.request.GroupSaveRequestDto;
import com.bilgeadam.dto.response.AttendanceSearchResponseDto;
import com.bilgeadam.dto.response.FindAllUnRegisteredGroupListResponseDto;
import com.bilgeadam.dto.response.FindByMainGroupIdResponseDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.manager.IMainGroupManager;
import com.bilgeadam.mapper.IInternshipGroupMapper;
import com.bilgeadam.repository.IInternshipGroupRepository;
import com.bilgeadam.repository.entity.InternshipGroup;
import com.bilgeadam.repository.view.VwGroupResponseDto;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InternshipGroupService extends ServiceManager<InternshipGroup,String> {
    private final IInternshipGroupRepository internshipGroupRepository;
    private final IMainGroupManager mainGroupManager;
    public InternshipGroupService(IInternshipGroupRepository internshipGroupRepository,
                                  IMainGroupManager mainGroupManager) {
        super(internshipGroupRepository);
        this.internshipGroupRepository = internshipGroupRepository;
        this.mainGroupManager = mainGroupManager;
    }

    public Boolean saveGroup(GroupSaveRequestDto dto){
        if(!internshipGroupRepository.existsByGroupNameIgnoreCase(dto.getGroupName())){
            InternshipGroup internshipGroup = IInternshipGroupMapper.INSTANCE.fromGroupSaveRequestDtoToInternshipGroup(dto);
            save(internshipGroup);
            return true;
        }
        throw new CardServiceException(ErrorType.GROUP_ALREADY_EXIST);
    }

    public List<FindByMainGroupIdResponseDto> findByMainGroupId(String mainGroupId){
        List<InternshipGroup> groupList = internshipGroupRepository.findByMainGroupId(mainGroupId);
        List<FindByMainGroupIdResponseDto> dtoList = groupList.stream().map(x->
                IInternshipGroupMapper.INSTANCE.fromGroupToFindByMainGroupIdResponseDto(x)).collect(Collectors.toList());
        return dtoList;
    }

    public Optional<InternshipGroup> findGroupByGroupName(String groupName){
        Optional<InternshipGroup> group = internshipGroupRepository.findByGroupName(groupName);
        return group;
    }

    public List<VwGroupResponseDto> findAllGroupNames(){
        return internshipGroupRepository.findAllGroupList();
    }

    public List<AttendanceSearchResponseDto> showAttendanceSearchList(String groupId){
        Optional<InternshipGroup> optionalGroup = findById(groupId);
        if(optionalGroup.isEmpty()){
            throw new CardServiceException(ErrorType.GROUP_NOT_FOUND);
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

    public Optional<InternshipGroup> findById(String groupId){
        return internshipGroupRepository.findById(groupId);
    }

    public List<FindAllUnRegisteredGroupListResponseDto> findAllUnRegisteredGroupList(String mainGroupId) {
        List<InternshipGroup> internshipGroupList = internshipGroupRepository.findByMainGroupId(mainGroupId);
        Set<String> groupNameSet = mainGroupManager.getSubGroupNamesByMainGroupId(mainGroupId).getBody();
        List<FindAllUnRegisteredGroupListResponseDto> dtoList = groupNameSet.stream()
                .filter(groupName ->
                    internshipGroupList.stream()
                            .noneMatch(internshipGroup ->
                                    internshipGroup.getGroupName().equals(groupName.toUpperCase())
                            )
                )
                .map(groupName ->FindAllUnRegisteredGroupListResponseDto.builder()
                        .mainGroupId(mainGroupId)
                        .groupName(groupName.toUpperCase())
                        .build()).collect(Collectors.toList());
        return dtoList;
    }

    public List<InternshipGroup> findAllRegisteredGroupList(String mainGroupId) {
        return internshipGroupRepository.findByMainGroupId(mainGroupId);
    }




}
