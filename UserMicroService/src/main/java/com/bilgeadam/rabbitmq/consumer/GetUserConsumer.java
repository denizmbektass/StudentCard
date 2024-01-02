package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.dto.response.UserProfileResponseDto;
import com.bilgeadam.rabbitmq.model.GetUserModel;
import com.bilgeadam.repository.entity.User;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import nonapi.io.github.classgraph.utils.VersionFinder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetUserConsumer {

    private final UserService userService;
    @RabbitListener(queues = "${rabbitmq.getUserQueue}")
    public Object GetUser(GetUserModel getUserModel){
        Optional<User> user = userService.findByWithStudentId(getUserModel.getToken());
        UserProfileResponseDto userProfileResponseDto = UserProfileResponseDto.builder()
                .name(user.get().getName())
                .surname(user.get().getSurname())
                .build();
        return userProfileResponseDto;
    }
}
