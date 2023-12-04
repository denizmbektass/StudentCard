package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateInternshipSuccessScoreWeightsRequestDto;
import com.bilgeadam.dto.request.UpdateInternshipSuccsessScoreWeightsRequestDto;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.exceptions.WeightsException;
import com.bilgeadam.mapper.IInternshipSuccessScoreWeightsMapper;
import com.bilgeadam.repository.IInternshipSuccessScoreWeightsRepository;
import com.bilgeadam.repository.entity.InternshipSuccessScoreWeights;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class InternshipSuccessScoreWeightsService extends ServiceManager<InternshipSuccessScoreWeights, String> {
    private final IInternshipSuccessScoreWeightsRepository internshipSuccessScoreRepository;
    private final IInternshipSuccessScoreWeightsMapper internshipSuccessScoreWeightsMapper;

    public InternshipSuccessScoreWeightsService(MongoRepository<InternshipSuccessScoreWeights, String> repository, IInternshipSuccessScoreWeightsRepository internshipSuccessScoreRepository, IInternshipSuccessScoreWeightsMapper internshipSuccessScoreWeightsMapper) {
        super(repository);
        this.internshipSuccessScoreRepository = internshipSuccessScoreRepository;
        this.internshipSuccessScoreWeightsMapper = internshipSuccessScoreWeightsMapper;
    }

    public InternshipSuccessScoreWeights getWeightsByGroupName(String groupName) {
        InternshipSuccessScoreWeights internshipSuccessScoreWeights = internshipSuccessScoreRepository.findByGroupName(groupName);
        return internshipSuccessScoreWeights != null ? internshipSuccessScoreWeights : new InternshipSuccessScoreWeights();
    }

    public boolean saveWeights(CreateInternshipSuccessScoreWeightsRequestDto createInternshipSuccessScoreWeightsRequestDto) {
        if (createInternshipSuccessScoreWeightsRequestDto == null) {
            throw new WeightsException(ErrorType.TOTAL_WEIGHTS_FORMAT);
        }
        double totalWeight = createInternshipSuccessScoreWeightsRequestDto.getAlgorithmWeight() + createInternshipSuccessScoreWeightsRequestDto.getTechnicalInterviewWeight() + createInternshipSuccessScoreWeightsRequestDto.getCandidateInterviewWeight() + createInternshipSuccessScoreWeightsRequestDto.getWrittenExamWeight();
        if (totalWeight != 100) {
            throw new WeightsException(ErrorType.TOTAL_WEIGHTS_VALIDATE);
        }

        InternshipSuccessScoreWeights internshipSuccessScoreWeights = internshipSuccessScoreRepository.findByGroupName(createInternshipSuccessScoreWeightsRequestDto.getGroupName());
        if (internshipSuccessScoreWeights == null) {
            save(internshipSuccessScoreWeightsMapper.toSaveInternshipSuccessScoreWeights(createInternshipSuccessScoreWeightsRequestDto));
            return true;
        }
        return false;
    }

    public boolean updateWeights(UpdateInternshipSuccsessScoreWeightsRequestDto updateInternshipSuccsessScoreWeightsRequestDto) {
        if (updateInternshipSuccsessScoreWeightsRequestDto == null) {
            throw new WeightsException(ErrorType.TOTAL_WEIGHTS_FORMAT);
        }
        double totalWeight = updateInternshipSuccsessScoreWeightsRequestDto.getAlgorithmWeight() + updateInternshipSuccsessScoreWeightsRequestDto.getTechnicalInterviewWeight() + updateInternshipSuccsessScoreWeightsRequestDto.getCandidateInterviewWeight() + updateInternshipSuccsessScoreWeightsRequestDto.getWrittenExamWeight();
        if (totalWeight != 100) {
            throw new WeightsException(ErrorType.TOTAL_WEIGHTS_VALIDATE);
        }

        InternshipSuccessScoreWeights internshipSuccessScoreWeights = internshipSuccessScoreRepository.findByGroupName(updateInternshipSuccsessScoreWeightsRequestDto.getGroupName());
        if (internshipSuccessScoreWeights != null) {
            update(internshipSuccessScoreWeightsMapper.toUpdateInternshipSuccessScoreWeights(updateInternshipSuccsessScoreWeightsRequestDto));
            return true;
        }
        return false;
    }
}
