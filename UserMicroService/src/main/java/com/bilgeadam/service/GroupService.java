package com.bilgeadam.service;

import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.exceptions.UserServiceException;
import com.bilgeadam.repository.IGroupRepository;
import com.bilgeadam.repository.entity.MainGroup;
import com.bilgeadam.repository.entity.Group;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService extends ServiceManager<Group,String> {
    private final IGroupRepository groupRepository;
    private final MainGroupService mainGroupService;

    public GroupService(IGroupRepository groupRepository,
                        MainGroupService mainGroupService){
        super(groupRepository);
        this.groupRepository = groupRepository;
        this.mainGroupService = mainGroupService;
    }



    public void addSubGroupToGroup(List<String> subGroupNameList){
        List<MainGroup> groupList = mainGroupService.findAll();
        List<String> groupNames = groupList.stream().map(x->
            x.getMainGroupName()
        ).collect(Collectors.toList());
        subGroupNameList.stream().forEach(subGroupName->{
            if(!groupRepository.existsByGroupName(subGroupName)){
                groupNames.forEach(x->{
                    if(subGroupName.toUpperCase().contains(x.toUpperCase())){
                        System.out.println(mainGroupService.findByGroupName(x));
                        MainGroup group = mainGroupService.findByGroupName(x).orElseThrow(()->{
                            throw new UserServiceException(ErrorType.GROUP_NOT_FOUND);
                        });
                        group.getGroupNameList().add(subGroupName);
                        save(Group.builder()
                                .groupName(subGroupName)
                                .build());
                        mainGroupService.update(group);
                    }
                });
            }
        });
    }





}
