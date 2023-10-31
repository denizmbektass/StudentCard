package com.bilgeadam.dto.response;

import com.bilgeadam.dto.request.TranscriptInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class TranscriptResponseDto {
    List<TrainerAssessmentForTranscriptResponseDto> trainerAssessment;
    List<AssignmentResponseDto> assignment;
    List<ExamResponseDto> exam;
    List<InternshipResponseDto> intership;
    List<InterviewForTranscriptResponseDto> interview;
    Double absence;
    List<StudentProjectListResponseDto> project;
    TranscriptInfo transcriptInfo;
    StudentChoiceResponseDto studentChoice;
    Long StudentAvarageScore;

}
