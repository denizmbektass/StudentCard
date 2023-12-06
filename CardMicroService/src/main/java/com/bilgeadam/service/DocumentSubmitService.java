package com.bilgeadam.service;

import com.bilgeadam.dto.request.SaveDocumentSubmitRequestDto;
import com.bilgeadam.dto.request.UpdateDocumentSubmitRequestDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.repository.IDocumentSubmitRepository;
import com.bilgeadam.repository.entity.DocumentSubmit;
import com.bilgeadam.repository.entity.EmploymentWeights;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentSubmitService extends ServiceManager<DocumentSubmit, String> {

    private final JwtTokenManager jwtTokenManager;
    private final IDocumentSubmitRepository documentSubmitRepository;
    private final EmploymentWeightsService employmentWeightsService;

    public DocumentSubmitService(JwtTokenManager jwtTokenManager, IDocumentSubmitRepository documentSubmitRepository, EmploymentWeightsService employmentWeightsService) {
        super(documentSubmitRepository);
        this.documentSubmitRepository = documentSubmitRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.employmentWeightsService = employmentWeightsService;
    }

    public double calculateDocumentSubmitAverageScore(int documentSubmitScore, String token) {
        List<String> groupName = jwtTokenManager.getGroupNameFromToken(token);
        if (groupName.isEmpty()) {
            throw new RuntimeException("Hata");
        }
        EmploymentWeights employmentWeights = employmentWeightsService.getWeightsByGroupName(groupName.get(0));
        Double documentScorePercentage = employmentWeights.getDocumentSubmitWeight();
        int maxPercentage = 100;
        if (documentSubmitScore < 0 || documentSubmitScore > 100) {
            throw new CardServiceException(ErrorType.DOCUMENTSUBMIT_NUMBER_OUT_RANGE);
        }
        double averageScore = (documentSubmitScore * documentScorePercentage) / maxPercentage;
        return averageScore;

    }


    public DocumentSubmit saveDocumentSubmit(SaveDocumentSubmitRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isEmpty()) {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        DocumentSubmit submit = DocumentSubmit.builder()
                .documentSubmitAverageScore(calculateDocumentSubmitAverageScore(dto.getDocumentSubmitScore(), dto.getStudentToken()))
                .studentId(studentId.get())
                .documentSubmitScore(dto.getDocumentSubmitScore())
                .build();
        return save(submit);
    }

    public DocumentSubmit getDocumentSubmitByStudentId(String studentId) {
        Optional<DocumentSubmit> documentSubmit = documentSubmitRepository.findByStudentId(studentId);
        if (documentSubmit.isPresent()) {
            return documentSubmit.get();
        } else {
            return null;
        }
    }

    public MessageResponse updateDocumentSubmitScore(UpdateDocumentSubmitRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isEmpty()) {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        Optional<DocumentSubmit> submit = documentSubmitRepository.findByStudentId(studentId.get());
        if (submit.isEmpty()) {
            throw new CardServiceException(ErrorType.DOCUMENTSUBMIT_NOT_FOUND);
        }
        DocumentSubmit updateSubmit = submit.get();
        updateSubmit.setDocumentSubmitScore(dto.getDocumentSubmitScore());
        updateSubmit.setDocumentSubmitAverageScore(calculateDocumentSubmitAverageScore(dto.getDocumentSubmitScore(), dto.getStudentToken()));
        update(updateSubmit);
        return new MessageResponse("Evrak teslim puanı başarıyla güncellendi");
    }

    public Double getDocumentSubmitAveragePoint(String studentId) {
        Optional<DocumentSubmit> documentSubmit = documentSubmitRepository.findByStudentId(studentId);
        if (documentSubmit.isPresent()) {
            return documentSubmit.get().getDocumentSubmitAverageScore();
        } else {
            return null;
        }
    }
}
