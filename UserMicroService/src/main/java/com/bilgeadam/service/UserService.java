package com.bilgeadam.service;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.FindStudentProfileResponseDto;
import com.bilgeadam.dto.response.UserResponseDto;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.exceptions.UserServiceException;
import com.bilgeadam.converter.UserConverter;
import com.bilgeadam.mapper.IUserMapper;
import com.bilgeadam.repository.IUserRepository;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.repository.entity.User;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService extends ServiceManager<User, String> {
    private final IUserRepository userRepository;
    private final UserConverter userConverter;
    private final JwtTokenManager jwtTokenManager;


    public UserService(IUserRepository userRepository, UserConverter userConverter,JwtTokenManager jwtTokenManager) {
        super(userRepository);
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.jwtTokenManager = jwtTokenManager;
    }

    public Boolean updateUser(UserUpdateRequestDto dto) {
        Optional<User> user = findById(dto.getId());
        if (user.isEmpty())
            throw new UserServiceException(ErrorType.USER_NOT_EXIST);
        //TODO mapper'a çevrilmeli
        User toUpdate = user.get();
        toUpdate.setName(dto.getName());
        toUpdate.setSurname(dto.getSurname());
        //toUpdate.setIdentityNumber(dto.getIdentityNumber());
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

    public UserResponseDto save (UserRequestDto dto){
        User user = IUserMapper.INSTANCE.toUser(dto);
        save(user);
       return IUserMapper.INSTANCE.toUserResponseDto(user);
    }
    public List<User> searchUser(SearchUserRequestDto dto){
      return   userRepository.findByNameContainingIgnoreCaseAndSurnameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndPhoneNumberContaining( dto.getName(), dto.getSurname(), dto.getEmail(), dto.getPhoneNumber());
    }
    public String createToken(SelectUserCreateTokenDto dto){
        Optional<User> user = findById(dto.getStudentId());
        Optional<String> token=jwtTokenManager.createToken(dto.getStudentId(), dto.getRole(),dto.getStatus(),user.get().getGroupNameList());
        if(token.isEmpty()) throw  new UserServiceException(ErrorType.TOKEN_NOT_CREATED);
        return token.get();
    }
    public String getIdFromToken(String token){
        Optional<String> userId=jwtTokenManager.getIdFromToken(token);
        if (userId.isEmpty())throw  new UserServiceException(ErrorType.INVALID_TOKEN);
        return  userId.get();
    }
    public Boolean saveUserList(List<UserRequestDto> dtoList ){
       dtoList.stream().forEach(dto -> {
         save(dto);
       });
        return  true;
    }

    public FindStudentProfileResponseDto  findStudentProfile(String token){
        String userId = jwtTokenManager.getIdFromToken(token).orElseThrow(() -> {
            throw new UserServiceException(ErrorType.INVALID_TOKEN);
        });
        User user = findById(userId).orElseThrow(()->{
            throw new UserServiceException(ErrorType.USER_NOT_EXIST);
        });
        return IUserMapper.INSTANCE.toFindStudentProfileResponseDto(user);
    }

    public String getNameAndSurnameWithId(String userId){
        Optional<User> optionalUser= userRepository.findById(userId);
        if (optionalUser.isEmpty()){
            throw new UserServiceException(ErrorType.USER_NOT_EXIST);
        }
        return optionalUser.get().getName() + " " + optionalUser.get().getSurname();

    }

    public List<TrainersMailReminderDto> getTrainers() {
        return findAll().stream()
                .filter(x -> x.getStatus().equals(EStatus.ACTIVE) && x.getRoleList().contains(ERole.ASSISTANT_TRAINER))
                .map(x -> TrainersMailReminderDto.builder()
                        .email(x.getEmail())
                        .groupName(x.getGroupNameList())
                        .build())
                .toList();
    }

    public List<MastersMailReminderDto> getMasters(){
        return findAll().stream()
                .filter(x -> x.getStatus().equals(EStatus.ACTIVE) && x.getRoleList().contains(ERole.MASTER))
                .map(x -> MastersMailReminderDto.builder()
                        .email(x.getEmail())
                        .groupName(x.getGroupNameList())
                        .build())
                .toList();
    }

    public List<StudentsMailReminderDto> getStudents(){
        return findAll().stream()
                    .filter(x -> x.getStatus().equals(EStatus.ACTIVE) && x.getRoleList().contains(ERole.STUDENT))
                    .map(x -> StudentsMailReminderDto.builder()
                            .studentId(x.getUserId())
                            .name(x.getName())
                            .surname(x.getSurname())
                            .groupName(x.getGroupNameList())
                            .egitimSaati(x.getEgitimSaati())
                            .build())
                    .toList();
    }
}
