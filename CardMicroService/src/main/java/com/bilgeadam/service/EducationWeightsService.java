package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateEducationWeightsRequestDto;
import com.bilgeadam.dto.request.UpdateEducationWeightsRequestDto;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.exceptions.WeightsException;
import com.bilgeadam.mapper.IEducationWeightsMapper;
import com.bilgeadam.repository.IEducationWeightsRepository;
import com.bilgeadam.repository.entity.EducationWeights;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class EducationWeightsService extends ServiceManager<EducationWeights, String> {
    private final IEducationWeightsRepository educationWeightsRepository;
    private final IEducationWeightsMapper educationWeightsMapper;

    public EducationWeightsService(MongoRepository<EducationWeights, String> repository, IEducationWeightsRepository educationWeightsRepository, IEducationWeightsMapper educationWeightsMapper) {
        super(repository);
        this.educationWeightsRepository = educationWeightsRepository;
        this.educationWeightsMapper = educationWeightsMapper;
    }

    public EducationWeights getEducationWeightsByGroupName(String groupName) {
        EducationWeights educationWeights = educationWeightsRepository.findByGroupName(groupName);
        return educationWeights != null ? educationWeights : new EducationWeights();
    }

    public boolean saveWeights(CreateEducationWeightsRequestDto educationWeightsRequestDto) {
        if (educationWeightsRequestDto == null) {
            throw new WeightsException(ErrorType.TOTAL_WEIGHTS_FORMAT);
        }
        double totalWeight = educationWeightsRequestDto.getAssessmentWeight() + educationWeightsRequestDto.getAssignmentWeight() + educationWeightsRequestDto.getExamWeight() + educationWeightsRequestDto.getObligationWeight() + educationWeightsRequestDto.getProjectBehaviorWeight() + educationWeightsRequestDto.getGraduationProjectWeight();
        if (totalWeight != 100) {
            throw new WeightsException(ErrorType.TOTAL_WEIGHTS_VALIDATE);
        }

        EducationWeights educationWeights = educationWeightsRepository.findByGroupName(educationWeightsRequestDto.getGroupName());

        if (educationWeights == null) {
            save(educationWeightsMapper.toSaveEducationWeights(educationWeightsRequestDto));
            return true;
        }
        return false;
    }

    public boolean updateWeights(UpdateEducationWeightsRequestDto educationWeightsRequestDto) {
        if (educationWeightsRequestDto == null) {
            throw new WeightsException(ErrorType.TOTAL_WEIGHTS_FORMAT);
        }
        double totalWeight = educationWeightsRequestDto.getAssessmentWeight() + educationWeightsRequestDto.getAssignmentWeight() + educationWeightsRequestDto.getExamWeight() + educationWeightsRequestDto.getObligationWeight() + educationWeightsRequestDto.getProjectBehaviorWeight() + educationWeightsRequestDto.getGraduationProjectWeight();
        if (totalWeight != 100) {
            throw new WeightsException(ErrorType.TOTAL_WEIGHTS_VALIDATE);
        }

        EducationWeights educationWeights = educationWeightsRepository.findByGroupName(educationWeightsRequestDto.getGroupName());

        if (educationWeights == null) {
            update(educationWeightsMapper.toUpdateEducationWeights(educationWeightsRequestDto));
            return true;
        }
        return false;
    }

}
