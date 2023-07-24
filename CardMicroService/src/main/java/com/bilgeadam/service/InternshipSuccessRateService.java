package com.bilgeadam.service;

import com.bilgeadam.dto.request.InternshipSuccessRateRequestDto;
import com.bilgeadam.mapper.IInternshipSuccessRateMapper;
import com.bilgeadam.repository.IInternshipSuccessRateRepository;
import com.bilgeadam.repository.entity.InternshipSuccessRate;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

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
        InternshipSuccessRate internshipSuccessRate = IInternshipSuccessRateMapper.INSTANCE.toInternshipSuccessRateDtoFromInternshipSuccessRate(dto);
        save(internshipSuccessRate);
        return true;
    }
}
