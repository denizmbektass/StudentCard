package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateStudentChoiceWeightsRequestDto;
import com.bilgeadam.dto.request.UpdateStudentChoiceWeightsRequestDto;
import com.bilgeadam.mapper.IStudentChoiceWeightsMapper;
import com.bilgeadam.repository.IStudentChoiceWeightsRepository;
import com.bilgeadam.repository.entity.StudentChoiceWeights;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentChoiceWeightsService extends ServiceManager<StudentChoiceWeights, String> {

    private final IStudentChoiceWeightsRepository studentChoiceWeightsRepository;
    private final IStudentChoiceWeightsMapper studentChoiceWeightsMapper;

    public StudentChoiceWeightsService(MongoRepository<StudentChoiceWeights, String> repository, IStudentChoiceWeightsRepository studentChoiceWeightsRepository, IStudentChoiceWeightsMapper studentChoiceWeightsMapper) {
        super(repository);
        this.studentChoiceWeightsRepository = studentChoiceWeightsRepository;
        this.studentChoiceWeightsMapper = studentChoiceWeightsMapper;
    }

    public StudentChoiceWeights getWeightsByGroupName(String groupName) {
        StudentChoiceWeights studentChoiceWeights = studentChoiceWeightsRepository.findByGroupName(groupName);
        return studentChoiceWeights != null ? studentChoiceWeights : new StudentChoiceWeights();
    }

    public boolean saveWeights(CreateStudentChoiceWeightsRequestDto choiceWeightsRequestDto) {
        if (choiceWeightsRequestDto == null) {
            throw new RuntimeException("Hata");
        }
        double totalWeight = choiceWeightsRequestDto.getAlgorithmWeight() + choiceWeightsRequestDto.getCandidateInterviewWeight() + choiceWeightsRequestDto.getTechnicalInterviewWeight() + choiceWeightsRequestDto.getWrittenExamWeight();
        if (totalWeight > 100) {
            throw new RuntimeException("Hata");
        }
        StudentChoiceWeights studentChoiceWeights = studentChoiceWeightsRepository.findByGroupName(choiceWeightsRequestDto.getGroupName());
        if (studentChoiceWeights == null) {
            save(studentChoiceWeightsMapper.toSaveStudentChoiceWeights(choiceWeightsRequestDto));
            return true;
        }
        return false;
    }

    public boolean updateWeights(UpdateStudentChoiceWeightsRequestDto choiceWeightsRequestDto) {
        if (choiceWeightsRequestDto == null) {
            throw new RuntimeException("Hata");
        }
        double totalWeight = choiceWeightsRequestDto.getAlgorithmWeight() + choiceWeightsRequestDto.getCandidateInterviewWeight() + choiceWeightsRequestDto.getTechnicalInterviewWeight() + choiceWeightsRequestDto.getWrittenExamWeight();
        if (totalWeight > 100) {
            throw new RuntimeException("Hata");
        }
        StudentChoiceWeights studentChoiceWeights = studentChoiceWeightsRepository.findByGroupName(choiceWeightsRequestDto.getGroupName());
        if (studentChoiceWeights != null) {
            update(studentChoiceWeightsMapper.toUpdateStudentChoiceWeights(choiceWeightsRequestDto));
            return true;
        }
        return false;
    }
}
