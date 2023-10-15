package com.bilgeadam.service;

import com.bilgeadam.dto.request.UpdateRollcallRequestDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;

import com.bilgeadam.exceptions.RollcallException;
import com.bilgeadam.repository.IRollcallRepository;
import com.bilgeadam.repository.entity.Rollcall;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RollcallService extends ServiceManager <Rollcall,String> {
    private final JwtTokenManager jwtTokenManager;
    private final IRollcallRepository rollcallRepository;
    public RollcallService(IRollcallRepository rollcallRepository, JwtTokenManager jwtTokenManager) {
        super(rollcallRepository);
        this.rollcallRepository = rollcallRepository;
        this.jwtTokenManager =jwtTokenManager;
    }


    public Boolean deleteRollcall(String rollcallId){
    Optional<Rollcall> rollcall = findById(rollcallId);
    if(rollcall.isEmpty())
        throw new CardServiceException(ErrorType.ROLLCALL_NOT_FOUND);
        deleteById(rollcallId);
        return true ;
}

   public Set<String> getAllTitles(String token){
       List<String> groupNames = jwtTokenManager.getGroupNameFromToken(token);
       if(groupNames.isEmpty())
           throw  new CardServiceException(ErrorType.INVALID_TOKEN);
       return findAll().stream().filter(x -> x.getGroupNames().stream().anyMatch(groupNames::contains))
               .map(y->y.getTitle()).collect(Collectors.toSet());
   }

   public MessageResponse updateRollcall(UpdateRollcallRequestDto dto){
        Optional<Rollcall> rollcall = findById(dto.getRollcallId());
        if(rollcall.isEmpty())
            throw new CardServiceException(ErrorType.ROLLCALL_NOT_FOUND);
        Rollcall update =rollcall.get();
        update.setTitle(dto.getTitle());
        update(update);
        return  new MessageResponse("Grup başarıyla güncellendi.");
   }
}
