package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateAlgorithmRequestDto;
import com.bilgeadam.dto.request.UpdateAlgorithmRequestDto;
import com.bilgeadam.dto.response.AlgorithmResponseDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.IAlgorithmMapper;
import com.bilgeadam.repository.IAlgorithmRepository;
import com.bilgeadam.repository.entity.Algorithm;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlgorithmService extends ServiceManager<Algorithm,String> {
    private final IAlgorithmRepository algorithmRepository;
    private final JwtTokenManager jwtTokenManager;
    private final IAlgorithmMapper algorithmMapper;
    public AlgorithmService(IAlgorithmRepository algorithmRepository, JwtTokenManager jwtTokenManager, IAlgorithmMapper algorithmMapper) {
        super(algorithmRepository);
        this.algorithmRepository = algorithmRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.algorithmMapper = algorithmMapper;
    }

    public Boolean createAlgorithmScore(CreateAlgorithmRequestDto dto){
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if(studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        double totalScore= (dto.getFirstScore()+dto.getSecondScore());
        Algorithm algorithm = algorithmMapper.toAlgorithm(dto);
        algorithm.setStudentId(studentId.get());
        algorithm.setFinalScore(totalScore);
        algorithm.setExempt(dto.isExempt());
        save(algorithm);
        return true;
    }

    public Boolean updateAlgorithmScore(UpdateAlgorithmRequestDto dto){
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if(studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        Optional<Algorithm> algorithm = algorithmRepository.findByStudentId(studentId.get());
        if (algorithm.isEmpty())
            throw new CardServiceException(ErrorType.ALGORITHM_NOT_FOUND);
        Algorithm updateAlgorithm = algorithm.get();
        updateAlgorithm.setFirstScore(dto.getFirstScore());
        updateAlgorithm.setSecondScore(dto.getSecondScore());
        updateAlgorithm.setExempt(dto.isExempt());
        double totalScore= (dto.getFirstScore()+dto.getSecondScore());
        updateAlgorithm.setFinalScore(totalScore);
        update(updateAlgorithm);
        return true;
    }

    public Boolean deleteAlgorithm(String algorithmId) {
        Optional<Algorithm> algorithm = findById(algorithmId);
        if (algorithm.isEmpty())
            throw new CardServiceException(ErrorType.ALGORITHM_NOT_FOUND);
        deleteById(algorithmId);
        return true;
    }
    public AlgorithmResponseDto getAlgorithm(String token){
        Optional<String> studentIdOptional = jwtTokenManager.getIdFromToken(token);
        if (studentIdOptional.isEmpty()) {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        String studentId = studentIdOptional.get();
        Optional<Algorithm> algorithmOptional = algorithmRepository.findByStudentId(studentId);
        if (algorithmOptional.isEmpty()) {
            return null;
        }
        Algorithm algorithm = algorithmOptional.get();
        AlgorithmResponseDto algorithmResponseDto= AlgorithmResponseDto.builder()
                .firstScore(algorithm.getFirstScore())
                .secondScore(algorithm.getSecondScore())
                .finalScore(algorithm.getFinalScore())
                .isExempt(algorithm.isExempt()).build();
        return algorithmResponseDto;
    }
}
