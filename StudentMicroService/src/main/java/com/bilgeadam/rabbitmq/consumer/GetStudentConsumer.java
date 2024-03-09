package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.dto.response.StudentProfileResponseDto;
import com.bilgeadam.rabbitmq.model.GetStudentModel;
import com.bilgeadam.repository.entity.Student;
import com.bilgeadam.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetStudentConsumer {

    private final StudentService studentService;

    @RabbitListener(queues = "${rabbitmq.getStudentQueue}")
    public Object GetStudent(GetStudentModel getStudentModel) {
        Optional<Student> student = studentService.findByWithStudentId(getStudentModel.getToken());
        StudentProfileResponseDto studentProfileResponseDto = StudentProfileResponseDto.builder()
                .name(student.get().getName())
                .surname(student.get().getSurname())
                .build();
        return studentProfileResponseDto;
    }

}
