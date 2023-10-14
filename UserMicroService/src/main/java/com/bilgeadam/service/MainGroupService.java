package com.bilgeadam.service;

import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.exceptions.UserServiceException;
import com.bilgeadam.repository.IMainGroupRepository;
import com.bilgeadam.repository.entity.MainGroup;
import com.bilgeadam.repository.view.VwGroupName;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MainGroupService extends ServiceManager<MainGroup,String> {
    private final IMainGroupRepository mainGroupRepository;

    public MainGroupService(IMainGroupRepository mainGroupRepository){
        super(mainGroupRepository);
        this.mainGroupRepository = mainGroupRepository;
    }

    public Boolean saveGroup(String mainGroupName){
        save(MainGroup.builder().mainGroupName(mainGroupName).build());
        return true;
    }


    public Optional<MainGroup> findByGroupName(String groupName){
        return mainGroupRepository.findByMainGroupName(groupName);
    }

    public List<VwGroupName> getAllGroupNames(){
        return mainGroupRepository.findAllGroupNamesAsString();
    }

    public Set<String> getSubGroupNamesByMainGroupId(String mainGroupId){
        MainGroup mainGroup = findById(mainGroupId).orElseThrow(()->{
            throw new UserServiceException(ErrorType.GROUP_NOT_FOUND);
        });
        return mainGroup.getGroupNameList();
    }





}
