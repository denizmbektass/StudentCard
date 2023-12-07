package com.bilgeadam.constants;

public class ApiUrls {

    public static final String VERSION = "api/v1";
    public static final String CARD = VERSION + "/card";

    //Istihtam
    public static final String EMPLOYMENT = CARD + "/employment";
    public static final String DOCUMENT_SUBMIT = EMPLOYMENT + "/document-submit";
    public static final String EMPLOYMENT_INTERVIEW = EMPLOYMENT + "/employment-interview";
    public static final String APPLICATION_PROCESS = EMPLOYMENT + "/application-process";
    public static final String CAREER_EDUCATION = EMPLOYMENT + "/career-education";
    //Ogrenci Secme
    public static final String STUDENT_CHOICE = CARD + "/student-choice";
    public static final String WRITTEN_EXAM = STUDENT_CHOICE + "/written-exam";
    public static final String INTERVIEW = STUDENT_CHOICE + "/interview";
    public static final String ALGORITHM = STUDENT_CHOICE + "/algorithm";
    public static final String TECHNICAL_INTERVIEW = STUDENT_CHOICE + "/technical-interview";

    //Egitim
    public static final String EDUCATION = CARD + "/education";
    public static final String EXAM = EDUCATION + "/exam";
    public static final String PROJECT_BEHAVIOR = EDUCATION + "/project-behavior";
    public static final String TRAINER_ASSESSMENT_COEFFICIENTS = EDUCATION + "/trainer-assessment-coefficients";
    public static final String ASSIGNMENT = EDUCATION + "/assignment";
    public static final String ABSENCE = EDUCATION + "/absence";
    public static final String GRADUATION_PROJECT = EDUCATION+ "/graduation-project";

    //Staj Basari
    public static final String INTERNSHIP_SUCCESS = CARD + "/internship-success";
    public static final String INTERNSHIP_TASKS = INTERNSHIP_SUCCESS + "/internship-tasks";
    public static final String TEAMWORK = INTERNSHIP_SUCCESS + "/teamwork";
    public static final String PERSONAL_MOTIVATION = INTERNSHIP_SUCCESS + "/personal-motivation";
    public static final String CONTRIBUTION = INTERNSHIP_SUCCESS + "/contribution";
    public static final String ATTENDANCE = INTERNSHIP_SUCCESS + "/attendance";
    public static final String TEAM_LEAD_ASSESSMENT = INTERNSHIP_SUCCESS + "/team-lead-assessment";

    public static final String TRAINER_ASSESSMENT = CARD + "/trainer-assessment";
    public static final String TRANSCRIPT_DEFAULT = CARD + "/transcript-default";
    public static final String SAVE = "/save";
    public static final String UPDATE = "/update";
    public static final String DELETE = "/delete";
    public static final String FIND_BY_ID = "/find-by-id";
    public static final String FIND_ALL_ACTIVE_TRAINER_ASSESSMENT = "/find-all-active";
    public static final String ROLLCALL = CARD + "/rollcall";
    public static final String PROJECT = CARD + "/project";
    public static final String CREATE = "/create";
    public static final String FIND_ALL = "/find-all";
    public static final String CREATE_PROJECT_SCORE = "/create-project-score";
    public static final String INTERNSHIP = CARD + "/internship";
    public static final String ADD_SCORE_AND_COMMENT = "/add-score-and-comment";
    public static final String CARDPARAMETER = CARD + "/card-parameter";
    public static final String AVERAGE = "/average-exam/{studentId}";
    public static final String ASSIGNMENT_AVERAGE = "/get-assignment-average";
    public static final String FIND_GRADUATION_PROJECT = "/find-graduation-project";
    public static final String FIND_PROJECT_BEHAVIOR = "/find-project-behavior";
    public static final String FIND_ALGORITHM = "/find-algorithm";
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
    public static final String GET_EMPLOYMENT_INTERVIEW = "/get";
    public static final String GET_CAREER_EDUCATION_COUNT = "/get-career-education-count";
    public static final String SAVE_CAREER_EDUCATION = "/save-career-education";
    public static final String UPDATE_CAREER_EDUCATION = "/update-career-education";
    public static final String GET_CAREER_EDUCATION = "/get-career-education";
    public static final String GET_CAREER_EDUCATION_AVERAGE_POINT = "/get-career-education-average-point";
    public static final String GET_ATTENDANCE_SCORE = "/get-attendance-score/{token}";
    public static final String FIND_PERSONAL_MOTIVATION = "/find-personal-motivation";
    public static final String GET_TEAMWORK_COUNT = "/get-teamwork-count";
    public static final String SAVE_TEAMWORK = "/save-teamwork";
    public static final String UPDATE_TEAMWORK = "/update-teamwork";
    public static final String GET_TEAMWORK = "/get-teamwork";
    public static final String GET_TEAMWORK_SUCCESS_POINT = "/get-teamwork-success-point";
    public static final String SAVE_TEAM_LEAD_ASSESSMENT = "/save-team-lead-assessment";
    public static final String UPDATE_TEAM_LEAD_ASSESSMENT = "/update-team-lead-assessment";
    public static final String GET_TEAM_LEAD_ASSESSMENT_DETAILS = "/get-team-lead-assessment-details";
    public static final String SAVE_CONTRIBUTION = "/save-contribution";
    public static final String UPDATE_CONTRIBUTION = "/update-contribution";
    public static final String GET_CONTRIBUTION = "/get-contribution";
    public static final String GET_TOTAL_SCORE_CONTRIBUTION = "/get-total-score-contribution";
    public static final String SAVE_INTERNSHIP_TASK = "/save-internship-task";
    public static final String GET_INTERNSHIP_TASK_COUNT = "/get-internship-task-count";
    public static final String UPDATE_INTERNSHIP = "/update-internship";
    public static final String GET_INTERNSHIP = "/get-internship";
    public static final String GET_INTERNSHIP_SUCCESS_POINT = "/get-internship-success-point";
    public static final String MAIN_WEIGHTS = CARD + "/main-weights";
    public static final String SAVE_MAIN_WEIGHTS = "/save-main-weights";
    public static final String UPDATE_MAIN_WEIGHTS = "/update-main-weights";
    public static final String GET_ALL_MAIN_WEIGHTS = "/get-all-main-weights";
    public static final String STUDENT_CHOICE_WEIGHTS = CARD + "/student-choice-weights";
    public static final String SAVE_STUDENT_CHOICE_WEIGHTS = "/save-student-choice-weights";
    public static final String UPDATE_STUDENT_CHOICE_WEIGHTS = "/update-student-choice-weights";
    public static final String GET_ALL_STUDENT_CHOICE_WEIGHTS = "/get-all-student-choice-weights";
    public static final String EDUCATION_WEIGHTS = CARD + "/education-weights";
    public static final String SAVE_EDUCATION_WEIGHTS = "/save-education-weights";
    public static final String UPDATE_EDUCATION_WEIGHTS = "/update-education-weights";
    public static final String GET_ALL_EDUCATION_WEIGHTS = "/get-all-education-weights";
    public static final String INTERNSHIP_SUCCESS_SCORE_WEIGHTS = CARD + "/internship-success-score-weights";
    public static final String SAVE_INTERNSHIP_SUCCESS_SCORE_WEIGHTS = "/save-internship-success-score-weights";
    public static final String UPDATE_INTERNSHIP_SUCCESS_SCORE_WEIGHTS = "/update-internship-success-score-weights";
    public static final String GET_ALL_INTERNSHIP_SUCCESS_SCORE_WEIGHTS = "/get-all-internship-success-score-weights";
    public static final String EMPLOYMENT_WEIGHTS = CARD + "/employment-weights";
    public static final String SAVE_EMPLOYMENT_WEIGHTS = "/save-employment-weights";
    public static final String UPDATE_EMPLOYMENT_WEIGHTS = "/update-employment-weights";
    public static final String GET_ALL_EMPLOYMENT_WEIGHTS = "/get-all-employment-weights";
}
