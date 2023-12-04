package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateEmploymentWeightsRequestDto;
import com.bilgeadam.dto.request.UpdateEmploymentWeightsRequestDto;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.exceptions.WeightsException;
import com.bilgeadam.mapper.IEmploymentWeightsMapper;
import com.bilgeadam.repository.IEmloymentWeightsRepository;
import com.bilgeadam.repository.entity.EmploymentWeights;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class EmploymentWeightsService extends ServiceManager<EmploymentWeights, String> {
    private final IEmloymentWeightsRepository employmentWeightsRepository;
    private final IEmploymentWeightsMapper employmentWeightsMapper;

    public EmploymentWeightsService(MongoRepository<EmploymentWeights, String> repository, IEmloymentWeightsRepository emloymentWeightsRepository, IEmploymentWeightsMapper employmentWeightsMapper) {
        super(repository);
        this.employmentWeightsRepository = emloymentWeightsRepository;
        this.employmentWeightsMapper = employmentWeightsMapper;
    }


    public EmploymentWeights getWeightsByGroupName(String groupName) {
        EmploymentWeights employmentWeights = employmentWeightsRepository.findByGroupName(groupName);
        return employmentWeights != null ? employmentWeights : new EmploymentWeights();
    }

    public boolean saveWeights(CreateEmploymentWeightsRequestDto createEmploymentWeightsRequestDto) {
        if (createEmploymentWeightsRequestDto == null) {
            throw new WeightsException(ErrorType.TOTAL_WEIGHTS_FORMAT);
        }
        double totalWeight = createEmploymentWeightsRequestDto.getEmploymentInterviewWeight() + createEmploymentWeightsRequestDto.getApplicationProcessWeight() + createEmploymentWeightsRequestDto.getCareerEducationWeight() + createEmploymentWeightsRequestDto.getDocumentSubmitWeight();
        if (totalWeight != 100) {
            throw new WeightsException(ErrorType.TOTAL_WEIGHTS_VALIDATE);
        }

        EmploymentWeights employmentWeights = employmentWeightsRepository.findByGroupName(createEmploymentWeightsRequestDto.getGroupName());

        if (employmentWeights == null) {
            save(employmentWeightsMapper.toSaveEmploymentWeights(createEmploymentWeightsRequestDto));
            return true;
        }
        return false;
    }

    public boolean updateWeights(UpdateEmploymentWeightsRequestDto updateEmploymentWeightsRequestDto) {
        if (updateEmploymentWeightsRequestDto == null) {
            throw new WeightsException(ErrorType.TOTAL_WEIGHTS_FORMAT);
        }
        double totalWeight = updateEmploymentWeightsRequestDto.getEmploymentInterviewWeight() + updateEmploymentWeightsRequestDto.getApplicationProcessWeight() + updateEmploymentWeightsRequestDto.getCareerEducationWeight() + updateEmploymentWeightsRequestDto.getDocumentSubmitWeight();
        if (totalWeight != 100) {
            throw new WeightsException(ErrorType.TOTAL_WEIGHTS_VALIDATE);
        }
        EmploymentWeights employmentWeights = employmentWeightsRepository.findByGroupName(updateEmploymentWeightsRequestDto.getGroupName());
        if (employmentWeights != null) {
            update(employmentWeightsMapper.toUpdateEmploymentWeights(updateEmploymentWeightsRequestDto));
            return true;
        }
        return false;
    }
}
