package com.bilgeadam.service;

import com.bilgeadam.dto.request.InternshipSuccessRateRequestDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.IInternshipSuccessRateMapper;
import com.bilgeadam.repository.IInternshipSuccessRateRepository;
import com.bilgeadam.repository.entity.InternshipSuccessRate;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

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
}
