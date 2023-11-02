package com.bilgeadam.constants;

public class ApiUrls {

    public static final String VERSION = "api/v1";
    public static final String CARD = VERSION+"/card";
    public static final String TRAINER_ASSESSMENT = VERSION+"/trainer-assessment";
    public static final String TRANSCRIPT_DEFAULT = VERSION+"/transcript-default";
    public static final String SAVE = "/save";
    public static final String UPDATE = "/update";
    public static final String DELETE = "/delete";
    public static final String FIND_ALL_ACTIVE_TRAINER_ASSESSMENT ="/find-all-active";
    public static final String ASSIGNMENT = VERSION+"/assignment";
    public static final String EXAM = VERSION+"/exam";
    public static final String ROLLCALL = VERSION+"/rollcall";
    public static final String INTERVIEW = VERSION+"/interview";
    public static final String GAME_INTERVIEW = CARD+"/game-interview";
    public static final String PROJECT=VERSION+"/project";
    public static final String APPLICATION_PROCESS = VERSION + "/application-process";
    public static final String CREATE ="/create";
    public static final String FIND_ALL ="/find-all";
    public static final String WRITTENEXAM = VERSION+"/written-exam";
    public static final String CREATE_PROJECT_SCORE ="/create-project-score";
    public static final String INTERNSHIP = VERSION + "/internship";
    public static final String ADD_SCORE_AND_COMMENT = "/add-score-and-comment";
    public static final String ABSENCE = "/absence";
    public static final String CARDPARAMETER = VERSION + "/card-parameter";
    public static final String AVERAGE = "/average-exam/{studentId}";
    public static final String ASSIGNMENT_AVERAGE = "/get-assignment-average";
    public static final String GRADUATION_PROJECT = CARD + "/graduation-project";
    public static final String FIND_GRADUATION_PROJECT ="/find-graduation-project";
    public static final String PROJECT_BEHAVIOR= VERSION+"/project-behavior";
    public static final String FIND_PROJECT_BEHAVIOR= "/find-project-behavior";

    public static final String ALGORITHM= VERSION+"/algorithm";
    public static final String FIND_ALGORITHM= VERSION+"/find-algorithm";
    public static final String TRAINER_ASSESSMENT_COEFFICIENTS = CARD+"/trainer-assessment-coefficients";
    public static final String WRITTENEXAMSCORE = "/written-exam-score/{correctAnswers}";
    public static final String SAVEWRITTENEXAM = "/save-written-exam";
    public static final String GETWRITTENEXAM = "/get-written-exam/{studentId}";
    public static final String SAVE_CANDIDATE_INTERVIEW = "/save-candidate-interview";
    public static final String GET_CANDIDATE_INTERVIEW = "/get-candidate-interview";
    public static final String UPDATE_CANDIDATE_INTERVIEW = "/update-candidate-interview";
    public static final String GET_CANDIDATE_INTERVIEW_COUNT = "/get-candidate-interview-count";
    public static final String GET_CANDIDATE_INTERVIEW_AVERAGE_POINT = "/get-candidate-interview-average-point";

    public static final String GET_GAME_INTERVIEW = "/get-all";
    public static final String GET_GAME_INTERVIEW_NUMBER = "/get-number";
    public static final String GET_GAME_INTERVIEW_AVERAGE_POINT = "/get-average-point";

    public static final String APPLICATION_PROCESS_TOTAL_SCORE = "/total-score";
    public static final String EMPLOYMENT_INTERVIEW =CARD+"/employment-interview";
    public static final String GET_EMPLOYMENT_INTERVIEW = "/get";


}
