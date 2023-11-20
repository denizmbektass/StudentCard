package com.bilgeadam.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class FallbackController {
    @GetMapping("/auth")
    public ResponseEntity<String> fallbackAuthService(){
        return ResponseEntity.ok("Auth servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/user")
    public ResponseEntity<String> fallbackUserService(){
        return ResponseEntity.ok("User servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/mail")
    public ResponseEntity<String> fallbackMailService(){
        return ResponseEntity.ok("Mail servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card")
    public ResponseEntity<String> fallbackCardService(){
        return ResponseEntity.ok("Card servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/absence")
    public ResponseEntity<String> fallbackCardAbsenceService(){
        return ResponseEntity.ok("Absence servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/algorithm")
    public ResponseEntity<String> fallbackCardAlgorithmService(){
        return ResponseEntity.ok("Algorithm servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/application-process")
    public ResponseEntity<String> fallbackCardApplicationProcessService(){
        return ResponseEntity.ok("Application Process servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/assignment")
    public ResponseEntity<String> fallbackCardAssignmentService(){
        return ResponseEntity.ok("Assignment servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/attendance")
    public ResponseEntity<String> fallbackCardAttendanceService(){
        return ResponseEntity.ok("Attendance servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/card-parameter")
    public ResponseEntity<String> fallbackCardParameterService(){
        return ResponseEntity.ok("Card Parameter servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/career-education")
    public ResponseEntity<String> fallbackCardCareerEducationService(){
        return ResponseEntity.ok("Career Education servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/contribution")
    public ResponseEntity<String> fallbackCardContributionService(){
        return ResponseEntity.ok("Contribution servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/document-submit")
    public ResponseEntity<String> fallbackCardDocumentSubmitService(){
        return ResponseEntity.ok("Document Submit servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/employment-interview")
    public ResponseEntity<String> fallbackCardEmploymentInterviewService(){
        return ResponseEntity.ok("Employment Interview servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/exam")
    public ResponseEntity<String> fallbackCardExamService(){
        return ResponseEntity.ok("Exam servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/graduation-project")
    public ResponseEntity<String> fallbackCardGraduationProjectService(){
        return ResponseEntity.ok("Graduation Project servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/group-student")
    public ResponseEntity<String> fallbackCardGroupStudentService(){
        return ResponseEntity.ok("Group Student servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/internship-group")
    public ResponseEntity<String> fallbackCardInternshipGroupService(){
        return ResponseEntity.ok("Internship Group servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/internship")
    public ResponseEntity<String> fallbackCardInternshipService(){
        return ResponseEntity.ok("Internship Success Rate servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/internship-tasks")
    public ResponseEntity<String> fallbackCardInternshipTaskService(){
        return ResponseEntity.ok("Internship Tasks servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/interview")
    public ResponseEntity<String> fallbackCardInterviewService(){
        return ResponseEntity.ok("Interview servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/personal-motivation")
    public ResponseEntity<String> fallbackCardPersonalMotivationService(){
        return ResponseEntity.ok("Personal Motivation servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/project-behavior")
    public ResponseEntity<String> fallbackCardProjectBehaviorService(){
        return ResponseEntity.ok("Project Behavior servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/project")
    public ResponseEntity<String> fallbackCardProjectService(){
        return ResponseEntity.ok("Project servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/rollcall")
    public ResponseEntity<String> fallbackCardRollcallService(){
        return ResponseEntity.ok("Rollcall servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/team-lead-assessment")
    public ResponseEntity<String> fallbackCardTeamLeadAssessmentService(){
        return ResponseEntity.ok("Team Lead Assessment servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/teamwork")
    public ResponseEntity<String> fallbackCardTeamworkService(){
        return ResponseEntity.ok("Team Work servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/technical-interview")
    public ResponseEntity<String> fallbackCardTechnicalInterviewService(){
        return ResponseEntity.ok("Technical Interview servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/trainer-assessment-coefficients")
    public ResponseEntity<String> fallbackCardTrainerAssessmentCoefficientsService(){
        return ResponseEntity.ok("Trainer Assessment Coefficient servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/trainer-assessment")
    public ResponseEntity<String> fallbackCardTrainerAssessmentsService(){
        return ResponseEntity.ok("Trainer Assessment servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/transcript-default")
    public ResponseEntity<String> fallbackCardTranscriptDefaultService(){
        return ResponseEntity.ok("Transcript Default servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
    @GetMapping("/card/written-exam")
    public ResponseEntity<String> fallbackCardWrittenExamService(){
        return ResponseEntity.ok("Written Exam servisi şu anda hizmet verememektedir. Lütfen daha sonra tekrar deneyiniz");
    }
}
