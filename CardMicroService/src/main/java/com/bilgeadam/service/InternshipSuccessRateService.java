package com.bilgeadam.service;

import com.bilgeadam.dto.request.InternshipSuccessRateRequestDto;
import com.bilgeadam.dto.request.UpdateInternshipRequestDto;
import com.bilgeadam.dto.response.InternshipResponseDto;
import com.bilgeadam.exceptions.AssignmentException;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.IInternshipSuccessRateMapper;
import com.bilgeadam.repository.IInternshipSuccessRateRepository;
import com.bilgeadam.repository.entity.InternshipSuccessRate;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InternshipSuccessRateService extends ServiceManager<InternshipSuccessRate,String> {

    private final IInternshipSuccessRateRepository internshipSuccessRateRepository;
    private final JwtTokenManager jwtTokenManager;

    public InternshipSuccessRateService(IInternshipSuccessRateRepository internshipSuccessRateRepository,
                                        JwtTokenManager jwtTokenManager) {
        super(internshipSuccessRateRepository);
        this.internshipSuccessRateRepository = internshipSuccessRateRepository;
        this.jwtTokenManager = jwtTokenManager;
    }

    public Boolean addScoreAndCommentForStudent(InternshipSuccessRateRequestDto dto) {
        Optional<String> userId = jwtTokenManager.getIdFromToken(dto.getToken());
        if (userId.isEmpty()) {
            throw new CardServiceException(ErrorType.USER_NOT_FOUND);
        }
        InternshipSuccessRate internshipSuccessRate = IInternshipSuccessRateMapper.INSTANCE.toInternshipSuccessRateDtoFromInternshipSuccessRate(dto);
        internshipSuccessRate.setUserId(userId.get());
        if (dto.getScore() < 0 || dto.getScore() >100 ) {
            throw new CardServiceException(ErrorType.POINT_SUCCESS_RATE);
        }
        if(dto.getComment().length() > 255) {
            throw new CardServiceException(ErrorType.COMMENT_LENGTH_VERGE);
        }
        save(internshipSuccessRate);
        return true;
    }

    public List<InternshipResponseDto> findAllInternshipWithUser(String token) {
        Optional<String> userId = jwtTokenManager.getIdFromToken(token);
        if (userId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        List<InternshipResponseDto> internshipSuccessRateList = internshipSuccessRateRepository.findAllByUserId(userId.get());
        return internshipSuccessRateList;
    }
    public Integer getInternshipNote(String studentId){
        return (int) Math.floor(internshipSuccessRateRepository.findAllByUserId(studentId).stream()
                .mapToLong(x->x.getScore()).average().orElse(0));
    }
    public Boolean deleteSelectedInternship(String internshipId) {
        Optional<InternshipSuccessRate> deletedInternship = findById(internshipId);
        if (deletedInternship.isEmpty())
            throw new CardServiceException(ErrorType.INTERNSHIP_NOT_FOUND);
        deleteById(deletedInternship.get().getInternshipSuccessRateId());
        return true;
    }
    public Boolean updateSelectedInternship(UpdateInternshipRequestDto dto) {
        Optional<InternshipSuccessRate> getInternship = internshipSuccessRateRepository.findById(dto.getInternshipSuccessRateId());
        if (getInternship.isEmpty())
            throw new CardServiceException(ErrorType.INTERNSHIP_NOT_FOUND);
        InternshipSuccessRate updatedInternship = IInternshipSuccessRateMapper.INSTANCE.toUpdateInternshipFromInternship(dto, getInternship.get());
        update(updatedInternship);
        return true;
    }
}
