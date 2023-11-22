package com.bilgeadam.service;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.exceptions.UserServiceException;
import com.bilgeadam.converter.UserConverter;
import com.bilgeadam.manager.IAuthManager;
import com.bilgeadam.mapper.IUserMapper;
import com.bilgeadam.repository.IUserRepository;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.repository.entity.User;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.*;
import java.sql.Date;
import java.util.stream.Collectors;

@Service
public class UserService extends ServiceManager<User, String> {
    private final IUserRepository userRepository;
    private final UserConverter userConverter;
    private final JwtTokenManager jwtTokenManager;
    private final IAuthManager authManager;
    private final MainGroupService mainGroupService;
    private final GroupService groupService;


    public UserService(IUserRepository userRepository,
                       UserConverter userConverter,
                       JwtTokenManager jwtTokenManager,
                       MainGroupService mainGroupService,
                       GroupService groupService,
                       IAuthManager authManager) {
        super(userRepository);
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.jwtTokenManager = jwtTokenManager;
        this.authManager = authManager;
        this.mainGroupService = mainGroupService;
        this.groupService = groupService;
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
        groupService.addSubGroupToGroup(dto.getGroupNameList());
        User user = IUserMapper.INSTANCE.toUser(dto);
        save(user);
       return IUserMapper.INSTANCE.toUserResponseDto(user);
    }
    public List<User> searchUser(SearchUserRequestDto dto){
      return   userRepository.findByNameContainingIgnoreCaseAndSurnameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndPhoneNumberContaining( dto.getName(), dto.getSurname(), dto.getEmail(), dto.getPhoneNumber());
    }
    public String createToken(SelectUserCreateTokenDto dto){
        Optional<User> user = findById(dto.getStudentId());
        Optional<String> token=jwtTokenManager.createToken(dto.getStudentId(), dto.getRole(),dto.getStatus(),user.get().getGroupNameList(),user.get().getEmail());
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

    public FindStudentProfileResponseDto findStudentProfile(String token) {
        String userId = jwtTokenManager.getIdFromToken(token).orElseThrow(() -> {
            throw new UserServiceException(ErrorType.INVALID_TOKEN);
        });
        User user = findById(userId).orElseThrow(() -> {
            throw new UserServiceException(ErrorType.USER_NOT_EXIST);
        });
        return IUserMapper.INSTANCE.toFindStudentProfileResponseDto(user);
    }

    public String getNameAndSurnameWithId(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
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

    public List<MastersMailReminderDto> getMasters() {
        return findAll().stream()
                .filter(x -> x.getStatus().equals(EStatus.ACTIVE) && x.getRoleList().contains(ERole.MASTER_TRAINER))
                .map(x -> MastersMailReminderDto.builder()
                        .email(x.getEmail())
                        .groupName(x.getGroupNameList())
                        .build())
                .toList();
    }

    public List<StudentsMailReminderDto> getStudents() {
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
    public List<FindByGroupNameResponseDto> findByGroupNameList(String groupName) {
        return IUserMapper.INSTANCE.toFindByGroupNameListResponseDto(userRepository.findByGroupNameListIgnoreCase(groupName));
    }

    public TranscriptInfo getTranscriptInfoByUser(String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if (studentId.isEmpty()) throw new UserServiceException(ErrorType.INVALID_TOKEN);
        Optional<User> optionalUser = findById(studentId.get());
        if (optionalUser.isEmpty()) throw new UserServiceException(ErrorType.USER_NOT_EXIST);
        User user = optionalUser.get();
        List<User> users = findAll();
        String masterTrainer = users.stream()
                .filter(x -> x.getGroupNameList().stream().anyMatch(user.getGroupNameList()::contains)
                        && x.getRoleList().contains(ERole.MASTER_TRAINER))
                .map(u -> u.getName() +" " +u.getSurname())
                .collect(Collectors.joining(", "));
        String assistantTrainer = users.stream()
                .filter(x -> x.getGroupNameList().stream().anyMatch(user.getGroupNameList()::contains)
                        && x.getRoleList().contains(ERole.ASSISTANT_TRAINER))
                .map(User::getName)
                .collect(Collectors.joining(", "));
        return TranscriptInfo.builder().profilePicture(user.getProfilePicture()).startDate(new Date(user.getCreateDate()))
                .endDate(new Date(user.getUpdateDate())).masterTrainer(masterTrainer).assistantTrainer(assistantTrainer).group(user.getGroupNameList().get(0)).build();
    }

    public List<GroupStudentResponseDto> getAllStudentsWithoutInternship(GroupStudentRequestDto dto){
        List<User> userList = userRepository.findUsersByGroupNameListAndInternshipStatus(dto.getGroupName(),Arrays.asList(ERole.STUDENT));
        List<GroupStudentResponseDto> groupStudentResponseDtoList = userList.stream().map(user ->
            IUserMapper.INSTANCE.toGroupStudentResponseDto(user)
        ).collect(Collectors.toList());
        return groupStudentResponseDtoList;
    }

    public Boolean updateUserInternShipStatus(String userId) {
        User user = findById(userId).orElseThrow(()->{throw new UserServiceException(ErrorType.USER_NOT_EXIST);});
        user.setInternShipStatus(EStatus.ACTIVE);
        update(user);
        return true;
    }

    public Boolean updateUserInternShipStatusToDeleted(String userId) {
        User user = findById(userId).orElseThrow(()->{throw new UserServiceException(ErrorType.USER_NOT_EXIST);});
        user.setInternShipStatus(EStatus.DELETED);
        update(user);
        return true;
    }

    //Grup isimlerini almak için
    public List<String> findGroupNameForStudent(String userId) {
        Optional<User> user = findById(userId);
        if (user.isEmpty()) throw new UserServiceException(ErrorType.USER_NOT_EXIST);
        List<String> groupNames = new ArrayList<>();
        groupNames.addAll(user.get().getGroupNameList());
        return groupNames;
    }

    public String registerManagerForUser(RegisterRequestDto dto) {
        User user = IUserMapper.INSTANCE.toUserFromRegisterRequestDto(dto);
        user.setRoleList(List.of(ERole.ADMIN));
        save(user);
        return user.getUserId();
    }

    /**
     * Bu method giriş aksiyonu için yazılmış olup. Giriş yapan öğrenci-admin-trainer'lar'ın profil bilgilerinde kullanılacaktır.
     *
     * @param token
     * @return
     */
    public GetNameAndSurnameByIdResponseDto getUserNameAndSurnameFromToken(String token) {
        Optional<String> optionalUserId = jwtTokenManager.getIdFromTokenForUserId(token);
        if (optionalUserId.isEmpty()) {
            throw new UserServiceException(ErrorType.INVALID_TOKEN);
        }
        Optional<User> user = userRepository.findById(optionalUserId.get());
        if (user.isEmpty()) {
            throw new UserServiceException(ErrorType.USER_NOT_EXIST);
        }
        return IUserMapper.INSTANCE.toGetNameAndSurnameByIdResponseDtoFromUser(user.get());

    }

    public Boolean changePassword(ChangePasswordRequestDto dto,String token) {
        Optional<String> optionalUserId = jwtTokenManager.getIdFromTokenForUserId(token);
        if (optionalUserId.isEmpty()) {
            throw new UserServiceException(ErrorType.USER_NOT_EXIST);
        }
        GetAuthInfoForChangePassword getAuthInfoForChangePassword = authManager.getAuthInfoForChangePassword(optionalUserId.get()).getBody();
        if (!getAuthInfoForChangePassword.getPassword().equals(dto.getLastPassword())) {
            throw new UserServiceException(ErrorType.USER_WRONG_PASSWORD);
        }
        if (!dto.getNewPassword().equals(dto.getReNewPassword()))
            throw new UserServiceException(ErrorType.PASSWORD_UNMATCH);
        ChangePasswordResponseDto dto1=ChangePasswordResponseDto.builder()
                .newPassword(dto.getNewPassword())
                .userId(optionalUserId.get()).build();
        authManager.changePasswordFromUser(dto1);
        return true;
    }

    /**
     * Bu method giriş işlemi ardından öğrencinin profilini görüntüler --->
     * @param token
     */
    public FindStudentProfileResponseDto getUserProfile(String token){
        String userId= jwtTokenManager.getIdFromTokenForUserId(token).orElseThrow(
                ()-> {
                    throw new UserServiceException(ErrorType.INVALID_TOKEN);
                });
        Optional<User> user = findById(userId);
        if (user.isEmpty())
            throw new UserServiceException(ErrorType.USER_NOT_EXIST);
        return IUserMapper.INSTANCE.toFindStudentProfileResponseDto(user.get());
    }
    public Boolean saveProfileImage(SaveProfileImageRequestDto dto){
        Optional<String> userId = jwtTokenManager.getIdFromTokenForUserId(dto.getToken());
        if (userId.isEmpty()) throw new UserServiceException(ErrorType.INVALID_TOKEN);
        Optional<User> optionalUser= userRepository.findById(userId.get());
        optionalUser.get().setProfilePicture(dto.getProfilePicture());
        update(optionalUser.get());
        return true;
    }

    public String getProfileImage(String token){
        Optional<String> userId= jwtTokenManager.getIdFromTokenForUserId(token);
        if (userId.isEmpty()) throw new UserServiceException(ErrorType.INVALID_TOKEN);
        Optional<User> optionalUser= userRepository.findById(userId.get());
        if (optionalUser.isEmpty())throw new UserServiceException(ErrorType.USER_NOT_EXIST);
        String response= optionalUser.get().getProfilePicture();
        return response;
    }



}
