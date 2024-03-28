package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.BaseApiStudentRequestDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.request.SaveStudentRequestDto;
import com.bilgeadam.dto.response.FindByGroupNameResponseDto;
import com.bilgeadam.dto.response.FindStudentProfileResponseDto;
import com.bilgeadam.dto.response.GetNameAndSurnameByIdResponseDto;
import com.bilgeadam.dto.response.GroupStudentResponseDto;
import com.bilgeadam.dto.response.SaveStudentResponseDto;
import com.bilgeadam.repository.entity.Student;
import com.bilgeadam.repository.enums.ERole;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-28T15:15:16+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class IStudentMapperImpl implements IStudentMapper {

    @Override
    public Student toStudent(SaveStudentRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Student.StudentBuilder<?, ?> student = Student.builder();

        student.rowNumber( dto.getRowNumber() );
        student.name( dto.getName() );
        student.surname( dto.getSurname() );
        student.channel( dto.getChannel() );
        student.applicationDate( dto.getApplicationDate() );
        student.education( dto.getEducation() );
        student.educationStatus( dto.getEducationStatus() );
        student.className( dto.getClassName() );
        student.englishLevel( dto.getEnglishLevel() );
        student.city( dto.getCity() );
        student.district( dto.getDistrict() );
        student.educationBranch( dto.getEducationBranch() );
        student.relevantBranch( dto.getRelevantBranch() );
        student.workshopDate( dto.getWorkshopDate() );
        student.workshopTime( dto.getWorkshopTime() );
        student.workshopPlace( dto.getWorkshopPlace() );
        student.participationStatus( dto.getParticipationStatus() );
        student.examStatus( dto.getExamStatus() );
        student.interviewDate( dto.getInterviewDate() );
        student.interviewParticipationStatus( dto.getInterviewParticipationStatus() );
        student.interviewer( dto.getInterviewer() );
        student.evaluation( dto.getEvaluation() );
        student.examAndInterviewResult( dto.getExamAndInterviewResult() );
        student.contract( dto.getContract() );
        student.notes( dto.getNotes() );
        student.phoneNumber( dto.getPhoneNumber() );
        student.address( dto.getAddress() );
        student.birthDate( dto.getBirthDate() );
        student.birthPlace( dto.getBirthPlace() );
        student.school( dto.getSchool() );
        student.department( dto.getDepartment() );
        student.email( dto.getEmail() );
        List<String> list = dto.getGroupNameList();
        if ( list != null ) {
            student.groupNameList( new ArrayList<String>( list ) );
        }
        List<ERole> list1 = dto.getRoleList();
        if ( list1 != null ) {
            student.roleList( new ArrayList<ERole>( list1 ) );
        }

        return student.build();
    }

    @Override
    public Iterable<Student> toUsers(Iterable<SaveStudentRequestDto> dto) {
        if ( dto == null ) {
            return null;
        }

        ArrayList<Student> iterable = new ArrayList<Student>();
        for ( SaveStudentRequestDto saveStudentRequestDto : dto ) {
            iterable.add( toStudent( saveStudentRequestDto ) );
        }

        return iterable;
    }

    @Override
    public SaveStudentResponseDto toStudentResponseDto(Student student) {
        if ( student == null ) {
            return null;
        }

        SaveStudentResponseDto.SaveStudentResponseDtoBuilder saveStudentResponseDto = SaveStudentResponseDto.builder();

        saveStudentResponseDto.studentId( student.getStudentId() );
        saveStudentResponseDto.name( student.getName() );
        saveStudentResponseDto.surname( student.getSurname() );
        saveStudentResponseDto.phoneNumber( student.getPhoneNumber() );
        saveStudentResponseDto.address( student.getAddress() );
        saveStudentResponseDto.birthDate( student.getBirthDate() );
        saveStudentResponseDto.birthPlace( student.getBirthPlace() );
        saveStudentResponseDto.school( student.getSchool() );
        saveStudentResponseDto.department( student.getDepartment() );
        saveStudentResponseDto.email( student.getEmail() );
        List<String> list = student.getGroupNameList();
        if ( list != null ) {
            saveStudentResponseDto.groupNameList( new ArrayList<String>( list ) );
        }
        List<ERole> list1 = student.getRoleList();
        if ( list1 != null ) {
            saveStudentResponseDto.roleList( new ArrayList<ERole>( list1 ) );
        }

        return saveStudentResponseDto.build();
    }

    @Override
    public FindStudentProfileResponseDto toFindStudentProfileResponseDto(Student student) {
        if ( student == null ) {
            return null;
        }

        FindStudentProfileResponseDto.FindStudentProfileResponseDtoBuilder findStudentProfileResponseDto = FindStudentProfileResponseDto.builder();

        findStudentProfileResponseDto.name( student.getName() );
        findStudentProfileResponseDto.surname( student.getSurname() );
        findStudentProfileResponseDto.phoneNumber( student.getPhoneNumber() );
        findStudentProfileResponseDto.birthDate( student.getBirthDate() );
        findStudentProfileResponseDto.birthPlace( student.getBirthPlace() );
        findStudentProfileResponseDto.school( student.getSchool() );
        findStudentProfileResponseDto.department( student.getDepartment() );
        List<String> list = student.getGroupNameList();
        if ( list != null ) {
            findStudentProfileResponseDto.groupNameList( new ArrayList<String>( list ) );
        }
        findStudentProfileResponseDto.email( student.getEmail() );
        findStudentProfileResponseDto.address( student.getAddress() );
        findStudentProfileResponseDto.profilePicture( student.getProfilePicture() );

        return findStudentProfileResponseDto.build();
    }

    @Override
    public GetNameAndSurnameByIdResponseDto toGetNameAndSurnameByIdResponseDto(Student user) {
        if ( user == null ) {
            return null;
        }

        GetNameAndSurnameByIdResponseDto.GetNameAndSurnameByIdResponseDtoBuilder getNameAndSurnameByIdResponseDto = GetNameAndSurnameByIdResponseDto.builder();

        getNameAndSurnameByIdResponseDto.name( user.getName() );
        getNameAndSurnameByIdResponseDto.surname( user.getSurname() );

        return getNameAndSurnameByIdResponseDto.build();
    }

    @Override
    public GroupStudentResponseDto toGroupStudentResponseDto(Student student) {
        if ( student == null ) {
            return null;
        }

        GroupStudentResponseDto.GroupStudentResponseDtoBuilder groupStudentResponseDto = GroupStudentResponseDto.builder();

        groupStudentResponseDto.studentId( student.getStudentId() );
        groupStudentResponseDto.name( student.getName() );
        groupStudentResponseDto.surname( student.getSurname() );

        return groupStudentResponseDto.build();
    }

    @Override
    public List<FindByGroupNameResponseDto> toFindByGroupNameListResponseDto(List<Student> students) {
        if ( students == null ) {
            return null;
        }

        List<FindByGroupNameResponseDto> list = new ArrayList<FindByGroupNameResponseDto>( students.size() );
        for ( Student student : students ) {
            list.add( studentToFindByGroupNameResponseDto( student ) );
        }

        return list;
    }

    @Override
    public Student toStudentFromRegisterRequestDto(RegisterRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Student.StudentBuilder<?, ?> student = Student.builder();

        student.name( dto.getName() );
        student.surname( dto.getSurname() );
        student.email( dto.getEmail() );

        return student.build();
    }

    @Override
    public GetNameAndSurnameByIdResponseDto toGetNameAndSurnameByIdResponseDtoFromUser(Student user) {
        if ( user == null ) {
            return null;
        }

        GetNameAndSurnameByIdResponseDto.GetNameAndSurnameByIdResponseDtoBuilder getNameAndSurnameByIdResponseDto = GetNameAndSurnameByIdResponseDto.builder();

        getNameAndSurnameByIdResponseDto.name( user.getName() );
        getNameAndSurnameByIdResponseDto.surname( user.getSurname() );

        return getNameAndSurnameByIdResponseDto.build();
    }

    @Override
    public Student studentToUser(BaseApiStudentRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Student.StudentBuilder<?, ?> student = Student.builder();

        student.updateDate( dto.getUpdateDate() );
        student.studentId( dto.getStudentId() );
        student.name( dto.getName() );
        student.surname( dto.getSurname() );
        student.phoneNumber( dto.getPhoneNumber() );
        student.address( dto.getAddress() );
        student.birthDate( dto.getBirthDate() );
        student.birthPlace( dto.getBirthPlace() );
        student.school( dto.getSchool() );
        student.department( dto.getDepartment() );
        student.email( dto.getEmail() );
        List<String> list = dto.getGroupNameList();
        if ( list != null ) {
            student.groupNameList( new ArrayList<String>( list ) );
        }
        student.status( dto.getStatus() );
        student.internShipStatus( dto.getInternShipStatus() );

        return student.build();
    }

    protected FindByGroupNameResponseDto studentToFindByGroupNameResponseDto(Student student) {
        if ( student == null ) {
            return null;
        }

        FindByGroupNameResponseDto.FindByGroupNameResponseDtoBuilder findByGroupNameResponseDto = FindByGroupNameResponseDto.builder();

        findByGroupNameResponseDto.studentId( student.getStudentId() );
        findByGroupNameResponseDto.name( student.getName() );
        findByGroupNameResponseDto.surname( student.getSurname() );
        findByGroupNameResponseDto.email( student.getEmail() );
        List<ERole> list = student.getRoleList();
        if ( list != null ) {
            findByGroupNameResponseDto.roleList( new ArrayList<ERole>( list ) );
        }
        List<String> list1 = student.getGroupNameList();
        if ( list1 != null ) {
            findByGroupNameResponseDto.groupNameList( new ArrayList<String>( list1 ) );
        }

        return findByGroupNameResponseDto.build();
    }
}
