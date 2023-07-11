package com.bilgeadam.service;

import com.bilgeadam.dto.request.UserUpdateRequestDto;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.exceptions.UserServiceException;
import com.bilgeadam.converter.UserConverter;
import com.bilgeadam.repository.IUserRepository;
import com.bilgeadam.repository.entity.EStatus;
import com.bilgeadam.repository.entity.User;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends ServiceManager<User, String> {
    private final IUserRepository userRepository;
    private final UserConverter userConverter;

    public UserService(IUserRepository userRepository, UserConverter userConverter) {
        super(userRepository);
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    public Boolean updateUser(UserUpdateRequestDto dto) {
        Optional<User> user = findById(dto.getId());
        if (user.isEmpty())
            throw new UserServiceException(ErrorType.USER_NOT_EXIST);
        //TODO mapper'a çevrilmeli
        User toUpdate = user.get();
        toUpdate.setName(dto.getName());
        toUpdate.setSurname(dto.getSurname());
        toUpdate.setIdentityNumber(dto.getIdentityNumber());
        toUpdate.setPhoneNumber(dto.getPhoneNumber());
        toUpdate.setAddress(dto.getAddress());
        toUpdate.setBirthDate(dto.getBirthDate());
        toUpdate.setBirthPlace(dto.getBirthPlace());
        toUpdate.setSchool(dto.getSchool());
        toUpdate.setDepartment(dto.getDepartment());
        update(toUpdate);
        return true;
    }

    public Boolean doPassive(String id) {
        Optional<User> user = findById(id);
        if (user.isEmpty())
            throw new UserServiceException(ErrorType.USER_NOT_EXIST);
        user.get().setStatus(EStatus.PASSIVE);
        update(user.get());
        return true;
    }

    public Boolean safeDelete(String id) {
        Optional<User> user = findById(id);
        if (user.isEmpty())
            throw new UserServiceException(ErrorType.USER_NOT_EXIST);
        user.get().setStatus(EStatus.DELETED);
        update(user.get());
        return true;
    }
}
