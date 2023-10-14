package com.bilgeadam.service;

import com.bilgeadam.dto.request.TranscriptDefaultRequestDto;
import com.bilgeadam.dto.response.GetDefaultTranscriptResponseDto;
import com.bilgeadam.dto.response.TranscriptDefaultResponseDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.manager.IMainGroupManager;
import com.bilgeadam.mapper.ITranscriptDefaultMapper;
import com.bilgeadam.repository.ITranskriptDefaultRepository;
import com.bilgeadam.repository.entity.TranskriptDefault;
import com.bilgeadam.repository.view.VwGroupName;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TranskriptDefaultService extends ServiceManager<TranskriptDefault,String> {
    private final JwtTokenManager jwtTokenManager;
    private final ITranskriptDefaultRepository transkriptDefaultRepository;
    private final IMainGroupManager mainGroupManager;
    private final ITranscriptDefaultMapper iTranscriptDefaultMapper;
    public TranskriptDefaultService(ITranskriptDefaultRepository transkriptDefaultRepository,JwtTokenManager jwtTokenManager,IMainGroupManager mainGroupManager,ITranscriptDefaultMapper iTranscriptDefaultMapper) {
        super(transkriptDefaultRepository);
        this.jwtTokenManager=jwtTokenManager;
        this.transkriptDefaultRepository=transkriptDefaultRepository;
        this.mainGroupManager=mainGroupManager;
        this.iTranscriptDefaultMapper=iTranscriptDefaultMapper;
    }
    public List<VwGroupName> takingGroupNames(){
        List<VwGroupName> vwGroupName=mainGroupManager.getAllGroupNames().getBody();
        return vwGroupName;
    }
    public Boolean creatingTranscriptDatas(TranscriptDefaultRequestDto transcriptDefaultRequestDto){
        Optional<TranskriptDefault> transkriptDefault1= transkriptDefaultRepository.
                findOptionalByMainGroupName(transcriptDefaultRequestDto.getMainGroupName());
        if(!transkriptDefault1.isEmpty()){
            iTranscriptDefaultMapper.updateTranscriptDefault(transcriptDefaultRequestDto,transkriptDefault1.get());
           update(transkriptDefault1.get());

        }else{
            TranskriptDefault transkriptDefault=ITranscriptDefaultMapper.INSTANCE.
                    transcriptDefaultRequestDtoToTranskriptDefault(transcriptDefaultRequestDto);
            save(transkriptDefault);
        }
        return true;
    }

    public GetDefaultTranscriptResponseDto getDefaultTranscriptInfoByName(String mainGroupName){
        TranskriptDefault transkriptDefault = transkriptDefaultRepository.findOptionalByMainGroupName( mainGroupName).orElseThrow(()->{
            throw new CardServiceException(ErrorType.TRANSCRIPT_NOT_FOUND);
        });
        return ITranscriptDefaultMapper.INSTANCE.fromTranscriptDefaultToGetDefaultTranscriptResponseDto(transkriptDefault);
    }

    public Optional<TranskriptDefault> findOptionalByMainGroupName(String mainGroupName){
        return transkriptDefaultRepository.findOptionalByMainGroupName(mainGroupName);
    }
}
