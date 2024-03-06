package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.dto.response.UserProfileResponseDto;
import com.bilgeadam.rabbitmq.model.GetUserModel;
import com.bilgeadam.repository.entity.Student;
import com.bilgeadam.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetUserConsumer {

    private final StudentService studentService;

    @RabbitListener(queues = "${rabbitmq.getUserQueue}")
    public Object GetUser(GetUserModel getUserModel) {
        Optional<Student> user = studentService.findByWithStudentId(getUserModel.getToken());
        UserProfileResponseDto userProfileResponseDto = UserProfileResponseDto.builder()
                .name(user.get().getName())
                .surname(user.get().getSurname())
                .build();
        return userProfileResponseDto;
    }

}
