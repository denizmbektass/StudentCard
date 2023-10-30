package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateGameInterviewRequestDto;
import com.bilgeadam.dto.request.CreateInterviewRequestDto;
import com.bilgeadam.dto.request.SaveGameInterviewRequestDto;
import com.bilgeadam.dto.request.UpdateGameInterviewRequestDto;
import com.bilgeadam.dto.response.CreateInterviewResponseDto;
import com.bilgeadam.dto.response.GetGameInterviewResponseDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.exceptions.InterviewServiceException;
import com.bilgeadam.mapper.IGameInterviewMapper;
import com.bilgeadam.mapper.IInterviewMapper;
import com.bilgeadam.repository.IGameInterviewRepository;
import com.bilgeadam.repository.entity.GameInterview;
import com.bilgeadam.repository.entity.Interview;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameInterviewService extends ServiceManager<GameInterview,String> {
    private final IGameInterviewRepository gameInterviewRepository;
    private final JwtTokenManager jwtTokenManager;
    private final IGameInterviewMapper iGameInterviewMapper;

public GameInterviewService(IGameInterviewRepository gameInterviewRepository,JwtTokenManager jwtTokenManager,IGameInterviewMapper iGameInterviewMapper) {
    super(gameInterviewRepository);
    this.gameInterviewRepository = gameInterviewRepository;
    this.jwtTokenManager=jwtTokenManager;
    this.iGameInterviewMapper=iGameInterviewMapper;
}


    public boolean saveGameInterview(SaveGameInterviewRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if(studentId.isPresent()){
            GameInterview gameInterview = IGameInterviewMapper.INSTANCE.fromSaveGameInterviewRequestDtoToGameInterview(dto);
            gameInterview.setStudentId(studentId.get());
            double gameInterviewAvaragePoint = (dto.getDirectionCorrect()*0.25) + (dto.getCompletionTime()*0.25)+ (dto.getLevelReached()*0.25)+(dto.getSupportTaken()*0.25);
            gameInterview.setGameInterviewAveragePoint(gameInterviewAvaragePoint);
            save(gameInterview);
            return true;
        }
        else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }

    }

    public GetGameInterviewResponseDto getGameInterview(String studentId) {
        GetGameInterviewResponseDto responseDto;
        if(!studentId.equals("")){
            if(gameInterviewRepository.findAllByStudentId(studentId).size()>0){
                GameInterview gameInterview = gameInterviewRepository.findAllByStudentId(studentId).get(0);
                responseDto = IGameInterviewMapper.INSTANCE.fromGameInterviewToGetGameInterviewResponseDto(gameInterview);
            } else {
                throw new CardServiceException(ErrorType.GAME_INTERVIEW_NOT_FOUND);
            }
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        return responseDto;
    }


    public Boolean updateGameInterview(UpdateGameInterviewRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if(studentId.isPresent()){
            GameInterview gameInterview = gameInterviewRepository.findAllByStudentId(studentId.get()).get(0);

            gameInterview.setDirectionCorrect(dto.getDirectionCorrect());
            gameInterview.setCompletionTime(dto.getCompletionTime());
            gameInterview.setLevelReached(dto.getLevelReached());
            gameInterview.setSupportTaken(dto.getSupportTaken());
            gameInterview.setComment(dto.getComment());
            double gameInterviewAvaragePoint = (dto.getDirectionCorrect()*0.25) + (dto.getCompletionTime()*0.25)+ (dto.getLevelReached()*0.25)+(dto.getSupportTaken()*0.25);
            gameInterview.setGameInterviewAveragePoint(gameInterviewAvaragePoint);
            gameInterview.setGameInterviewAveragePoint(gameInterviewAvaragePoint);
            update(gameInterview);
            return true;
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
    }
    public Boolean deleteGameInterview(String gameInterviewId) {
        Optional<GameInterview> algorithm = findById(gameInterviewId);
        if (algorithm.isEmpty())
            throw new CardServiceException(ErrorType.GAME_INTERVIEW_NOT_FOUND);
        deleteById(gameInterviewId);
        return true;
    }

    public boolean createGameInterview(CreateGameInterviewRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());

        if (studentId.isPresent()) {
            GameInterview gameInterview = iGameInterviewMapper.fromCreateGameInterviewRequestDtoToGameInterview(dto);
            gameInterview.setStudentId(studentId.get());
            double gameInterviewAvaragePoint = (dto.getDirectionCorrect()*0.25) + (dto.getCompletionTime()*0.25)+ (dto.getLevelReached()*0.25)+(dto.getSupportTaken()*0.25);
            gameInterview.setGameInterviewAveragePoint(gameInterviewAvaragePoint);
            save(gameInterview);

            return true;
        } else {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
    }



    public Integer getGameInterviewNumber(String studentId) {
        if (studentId == null || studentId.isEmpty()) {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }

        List<GameInterview> gameInterviewList = gameInterviewRepository.findAllByStudentId(studentId);

        if (gameInterviewList.isEmpty()) {
            return 0;
        } else {
            return 1;
        }
    }

    public Double getGameInterviewAveragePoint(String studentId) {

        if (studentId == null || studentId.isEmpty()) {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        List<GameInterview> gameInterviewList = gameInterviewRepository.findAllByStudentId(studentId);
        if (gameInterviewList.isEmpty()) {
            return null;
        }
        double totalAveragePoint = gameInterviewList.get(0).getGameInterviewAveragePoint();

        return totalAveragePoint ;
    }
}


