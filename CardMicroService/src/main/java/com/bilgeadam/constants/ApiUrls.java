package com.bilgeadam.constants;

public class ApiUrls {

    public static final String VERSION = "api/v1";
    public static final String CARD = VERSION+"/card";
    public static final String TRAINER_ASSESSMENT = VERSION+"/trainer-assessment";
    public static final String TRANSCRIPT_DEFAULT = VERSION+"/transcript-default";
    public static final String SAVE = "/save";
    public static final String UPDATE = "/update";
    public static final String DELETE = "/delete";
    public static final String FIND_BY_ID = "/find-by-id";
    public static final String FIND_ALL_ACTIVE_TRAINER_ASSESSMENT ="/find-all-active";
    public static final String ASSIGNMENT = CARD + "/assignment";
    public static final String EXAM = VERSION+"/exam";
    public static final String ROLLCALL = VERSION+"/rollcall";
    public static final String INTERVIEW = VERSION+"/interview";
    public static final String TECHNICAL_INTERVIEW = CARD+"/technical-interview";
    public static final String PROJECT=VERSION+"/project";
    public static final String APPLICATION_PROCESS = CARD + "/application-process";
    public static final String CREATE ="/create";
    public static final String FIND_ALL ="/find-all";
    public static final String WRITTEN_EXAM = VERSION+"/written-exam";
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
    public static final String DOCUMENTSUBMIT = VERSION+"/document-submit";
    public static final String ATTENDANCE = VERSION + "/attendance";

    public static final String ALGORITHM= VERSION+"/algorithm";
    public static final String FIND_ALGORITHM= VERSION+"/find-algorithm";
    public static final String TRAINER_ASSESSMENT_COEFFICIENTS = CARD+"/trainer-assessment-coefficients";
    public static final String WRITTEN_EXAM_SCORE = "/written-exam-score/{correctAnswers}";
    public static final String SAVE_WRITTEN_EXAM = "/save-written-exam";
    public static final String GET_WRITTEN_EXAM = "/get-written-exam/{studentId}";
    public static final String SAVE_CANDIDATE_INTERVIEW = "/save-candidate-interview";
    public static final String GET_CANDIDATE_INTERVIEW = "/get-candidate-interview";
    public static final String UPDATE_CANDIDATE_INTERVIEW = "/update-candidate-interview";
    public static final String GET_CANDIDATE_INTERVIEW_COUNT = "/get-candidate-interview-count";
    public static final String GET_CANDIDATE_INTERVIEW_AVERAGE_POINT = "/get-candidate-interview-average-point";

    public static final String GET_TECHNICAL_INTERVIEW = "/get-technical-interview";
    public static final String GET_TECHNICAL_INTERVIEW_NUMBER = "/get-technical-interview-number";
    public static final String GET_TECHNICAL_INTERVIEW_AVERAGE_POINT = "/get-technical-interview-average-point";

    public static final String APPLICATION_PROCESS_TOTAL_SCORE = "/total-score";
    public static final String EMPLOYMENT_INTERVIEW =CARD+"/employment-interview";
    public static final String GET_EMPLOYMENT_INTERVIEW = "/get";

    public static final String CAREER_EDUCATION = VERSION + "/career-education";
    public static final String GET_CAREER_EDUCATION_COUNT = "/get-career-education-count";
    public static final String SAVE_CAREER_EDUCATION = "/save-career-education";
    public static final String UPDATE_CAREER_EDUCATION = "/update-career-education";
    public static final String GET_CAREER_EDUCATION = "/get-career-education";
    public static final String GET_CAREER_EDUCATION_AVERAGE_POINT = "/get-career-education-average-point";
    public static final String GET_ATTENDANCE_SCORE = "/get-attendance-score/{token}";

    public static final String TEAMWORK = VERSION + "/teamwork";
    public static final String GET_TEAMWORK_COUNT = "/get-teamwork-count";
    public static final String SAVE_TEAMWORK = "/save-teamwork";
    public static final String UPDATE_TEAMWORK = "/update-teamwork";
    public static final String GET_TEAMWORK = "/get-teamwork";
    public static final String GET_TEAMWORK_SUCCESS_POINT = "/get-teamwork-success-point";
    public static final String TEAM_LEAD_ASSESSMENT = CARD + "/team-lead-assessment";
    public static final String SAVE_TEAM_LEAD_ASSESSMENT = "/save-team-lead-assessment";
    public static final String UPDATE_TEAM_LEAD_ASSESSMENT = "/update-team-lead-assessment";
    public static final String GET_TEAM_LEAD_ASSESSMENT_DETAILS = "/get-team-lead-assessment-details";
    public static final String CONTRIBUTION = VERSION + "/contribution";
    public static final String SAVE_CONTRIBUTION = "/save-contribution";
    public static final String UPDATE_CONTRIBUTION = "/update-contribution";
    public static final String GET_CONTRIBUTION = "/get-contribution";
    public static final String GET_TOTAL_SCORE_CONTRIBUTION = "/get-total-score-contribution";

}
