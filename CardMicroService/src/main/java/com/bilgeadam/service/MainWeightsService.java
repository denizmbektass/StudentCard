package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateMainWeightsRequestDto;
import com.bilgeadam.dto.request.UpdateMainWeightsRequestDto;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.exceptions.WeightsException;
import com.bilgeadam.mapper.IMainWeightsMapper;
import com.bilgeadam.repository.IMainWeightsRepository;
import com.bilgeadam.repository.entity.MainWeights;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class MainWeightsService extends ServiceManager<MainWeights, String> {
    private final IMainWeightsRepository mainWeightsRepository;
    private final IMainWeightsMapper mainWeightsMapper;

    public MainWeightsService(MongoRepository<MainWeights, String> repository, IMainWeightsRepository mainWeightsRepository, IMainWeightsMapper mainWeightsMapper) {
        super(repository);
        this.mainWeightsRepository = mainWeightsRepository;
        this.mainWeightsMapper = mainWeightsMapper;
    }

    public MainWeights getWeightsByGroupName(String groupName) {
        MainWeights mainWeights = mainWeightsRepository.findByGroupName(groupName);
        return mainWeights != null ? mainWeights : new MainWeights();
    }

    public boolean saveWeights(CreateMainWeightsRequestDto mainWeightsRequestDto) {
        if (mainWeightsRequestDto == null) {
            throw new WeightsException(ErrorType.TOTAL_WEIGHTS_FORMAT);
        }
        double totalWeight = mainWeightsRequestDto.getEducationWeight() + mainWeightsRequestDto.getEmploymentWeight() + mainWeightsRequestDto.getInternshipSuccessWeight()
                + mainWeightsRequestDto.getStudentChoiceWeight();
        if (totalWeight != 100) {
            throw new WeightsException(ErrorType.TOTAL_WEIGHTS_VALIDATE);
        }

        MainWeights mainWeights = mainWeightsRepository.findByGroupName(mainWeightsRequestDto.getGroupName());

        if (mainWeights == null) {
            save(mainWeightsMapper.toSaveMainWeights(mainWeightsRequestDto));
            return true;
        }
        return false;
    }

    public boolean updateWeights(UpdateMainWeightsRequestDto mainWeightsRequestDto) {
        if (mainWeightsRequestDto == null) {
            throw new WeightsException(ErrorType.TOTAL_WEIGHTS_FORMAT);
        }
        double totalWeight = mainWeightsRequestDto.getEducationWeight() + mainWeightsRequestDto.getEmploymentWeight() + mainWeightsRequestDto.getInternshipSuccessWeight()
                + mainWeightsRequestDto.getStudentChoiceWeight();
        if (totalWeight != 100) {
            throw new WeightsException(ErrorType.TOTAL_WEIGHTS_VALIDATE);
        }
        MainWeights mainWeights = mainWeightsRepository.findByGroupName(mainWeightsRequestDto.getGroupName());
        if (mainWeights != null) {
            update(mainWeightsMapper.toUpdateMainWeights(mainWeightsRequestDto));
            return true;
        }
        return false;
    }
}
