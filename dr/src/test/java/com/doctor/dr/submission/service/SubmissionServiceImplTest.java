package com.doctor.dr.submission.service;

import com.doctor.dr.disease.stage.entity.DiseaseStage;
import com.doctor.dr.disease.stage.service.information.DiseaseStageInformation;
import com.doctor.dr.exception.ResourceNotFoundException;
import com.doctor.dr.externalapiservice.service.ModelClientService;
import com.doctor.dr.status.entity.Status;
import com.doctor.dr.status.service.information.StatusInformation;
import com.doctor.dr.submission.dto.response.DiseaseStageDetailResponseDTO;
import com.doctor.dr.submission.dto.response.StatusDetailResponseDTO;
import com.doctor.dr.submission.dto.response.SubmissionResponseDTO;
import com.doctor.dr.submission.dto.response.UserProfileDetailResponseDTO;
import com.doctor.dr.submission.entity.Submission;
import com.doctor.dr.submission.mapper.SubmissionMapper;
import com.doctor.dr.submission.repository.SubmissionRepository;
import com.doctor.dr.user.profile.entity.UserProfile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubmissionServiceImplTest {
    @Mock
    private SubmissionRepository submissionRepository;
    @Mock
    private SubmissionMapper submissionMapper;
    @Mock
    private StatusInformation statusInformation;
    @Mock
    private DiseaseStageInformation diseaseStageInformation;
    @Mock
    private ModelClientService modelClientService;
    @InjectMocks
    private SubmissionServiceImpl submissionService;

    @Test
    void getAll_shouldReturnActiveSubmissions_whenSubmissionsExist() {


        DiseaseStage diseaseStageLevelThree = this.getDiseaseStage(3);
        DiseaseStage diseaseStageLevelZero = this.getDiseaseStage(0);

        UserProfile userProfile = this.getUserProfile(1);
        Status completed = this.getStatus(1);
        Submission firstSubmission = this.createSubmission(1L, "patient_01", LocalDateTime.now(), true, diseaseStageLevelThree, userProfile, completed);
        Submission secondSubmission = this.createSubmission(7L, "patient_07", LocalDateTime.now(), true, diseaseStageLevelZero, userProfile, completed);

        List<Submission> submissionList = List.of(
                firstSubmission,
                secondSubmission
        );
        List<SubmissionResponseDTO> submissionResponseDTOList = submissionList.stream().map(this::submissionToSubmissionResponseDTO).toList();

        when(this.submissionRepository.findAllByIsActiveTrue()).thenReturn(submissionList);
        when(this.submissionMapper.toSubmissionResponseDTO(firstSubmission)).thenReturn(submissionResponseDTOList.get(0));
        when(this.submissionMapper.toSubmissionResponseDTO(secondSubmission)).thenReturn(submissionResponseDTOList.get(1));

        List<SubmissionResponseDTO> result = this.submissionService.getAll();
        assertAll("First submission",
                () -> assertEquals(firstSubmission.getSubmissionId(), result.get(0).getSubmissionId()),
                () -> assertEquals(firstSubmission.getPatientReferenceId(), result.get(0).getPatientReferenceId()),
                () -> assertEquals(firstSubmission.getCreatedDateTime(), result.get(0).getCreatedDateTime()),
                () -> assertEquals(firstSubmission.getIsActive(), result.get(0).getIsActive()),
                () -> assertEquals(firstSubmission.getDiseaseStage().getDiseaseLevel(), result.get(0).getDiseaseStage().getDiseaseLevel()),
                () -> assertEquals(firstSubmission.getUserProfile().getId(), result.get(0).getUserProfile().getId()),
                () -> assertEquals(firstSubmission.getStatus().getId(), result.get(0).getStatus().getId())
        );

        assertAll("Second submission",
                () -> assertEquals(secondSubmission.getSubmissionId(), result.get(1).getSubmissionId()),
                () -> assertEquals(secondSubmission.getPatientReferenceId(), result.get(1).getPatientReferenceId()),
                () -> assertEquals(secondSubmission.getCreatedDateTime(), result.get(1).getCreatedDateTime()),
                () -> assertEquals(secondSubmission.getIsActive(), result.get(1).getIsActive()),
                () -> assertEquals(secondSubmission.getDiseaseStage().getDiseaseLevel(), result.get(1).getDiseaseStage().getDiseaseLevel()),
                () -> assertEquals(secondSubmission.getUserProfile().getId(), result.get(1).getUserProfile().getId()),
                () -> assertEquals(secondSubmission.getStatus().getId(), result.get(1).getStatus().getId())
        );

        verify(this.submissionRepository, times(1)).findAllByIsActiveTrue();
        verify(this.submissionMapper, times(1)).toSubmissionResponseDTO(firstSubmission);
        verify(this.submissionMapper, times(1)).toSubmissionResponseDTO(secondSubmission);

    }


    @Test
    void getAll_shouldReturnEmptyList_whenNoActiveSubmissionsExist() {
        List<Submission> submissionList = Collections.emptyList();

        when(this.submissionRepository.findAllByIsActiveTrue()).thenReturn(submissionList);

        List<SubmissionResponseDTO> responseDTOS = this.submissionService.getAll();

        assertEquals(0, responseDTOS.size());
        verify(this.submissionRepository, times(1)).findAllByIsActiveTrue();
        verify(this.submissionMapper, times(0)).toSubmissionResponseDTO(null);
    }

    @Test
    void getSubmissionById_shouldReturnSubmission_whenValidSubmissionIdIsGiven() {
        UserProfile userProfile = this.getUserProfile(3);
        Status completed = this.getStatus(1);
        DiseaseStage diseaseStageLevelThree = this.getDiseaseStage(3);
        Submission submission = this.createSubmission(4L, "patient_04", LocalDateTime.now(), true, diseaseStageLevelThree, userProfile, completed);

        when(this.submissionRepository.findById(anyLong())).thenReturn(Optional.of(submission));
        SubmissionResponseDTO submissionResponseDTO = this.submissionToSubmissionResponseDTO(submission);
        when(this.submissionMapper.toSubmissionResponseDTO(any())).thenReturn(submissionResponseDTO);

        SubmissionResponseDTO result = this.submissionService.getSubmissionById(4L);

        assertEquals(4L, result.getSubmissionId());
        verify(this.submissionRepository, times(1)).findById(4L);
        verify(this.submissionMapper, times(1)).toSubmissionResponseDTO(submission);
    }

    @Test
    void getSubmissionById_shouldThrowException_whenInvalidSubmissionIdIsGiven() {
        when(this.submissionRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> this.submissionService.getSubmissionById(3L));
        assertEquals("Id not found.", exception.getMessage());
        verify(this.submissionRepository, times(1)).findById(3L);
        verify(this.submissionMapper, times(0)).toSubmissionResponseDTO(null);
    }

    @Test
    void deleteById_shouldDeactivateSubmission_whenValidActiveSubmissionIdIsGive() {

        DiseaseStage diseaseStage = this.getDiseaseStage(3);
        Status status = this.getStatus(1);
        UserProfile userProfile = mock(UserProfile.class);
        Submission submission = this.createSubmission(4L, "patient_04", LocalDateTime.now(), true, diseaseStage, userProfile, status);

        when(this.submissionRepository.findById(anyLong())).thenReturn(Optional.of(submission));
        this.submissionService.deleteById(4L);

        verify(this.submissionRepository, times(1)).findById(4L);
        verify(this.submissionRepository, times(1)).save(submission);
    }

    @Test
    void deleteById_shouldPerformNothing_whenValidDeactivatedSubmissionIdIsGive() {
        DiseaseStage diseaseStage = this.getDiseaseStage(3);
        Status status = this.getStatus(1);
        UserProfile userProfile = mock(UserProfile.class);
        Submission submission = this.createSubmission(4L, "patient_04", LocalDateTime.now(), false, diseaseStage, userProfile, status);

        when(this.submissionRepository.findById(anyLong())).thenReturn(Optional.of(submission));
        this.submissionService.deleteById(4L);

        verify(this.submissionRepository, times(1)).findById(4L);
        verify(this.submissionRepository, times(0)).save(submission);

    }

    @Test
    void deleteById_shouldThrowException_whenInvalidSubmissionIdIsGive() {

        when(this.submissionRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> this.submissionService.deleteById(4L));

        assertEquals("Id not found.", exception.getMessage());
        verify(this.submissionRepository, times(1)).findById(4L);
        verify(this.submissionMapper, times(0)).toSubmissionResponseDTO(null);

    }

    @Test
    void getSubmissionByDiseaseStageId_shouldReturnSubmissions_whenValidDiseaseStageIdIsGiven() {
        DiseaseStage diseaseStage = this.getDiseaseStage(3);
        when(diseaseStage.getId()).thenReturn(3L);
        UserProfile userProfile = this.getUserProfile(5L);
        Status status = this.getStatus(1);

        Submission firstSubmission = this.createSubmission(33L, "patient_33", LocalDateTime.now(), true, diseaseStage, userProfile, status);
        Submission secondSubmission = this.createSubmission(45L, "patient_45", LocalDateTime.now(), true, diseaseStage, userProfile, status);

        List<Submission> submissionList = List.of(firstSubmission, secondSubmission);
        List<SubmissionResponseDTO> submissionResponseDTOList = submissionList.stream().map(this::submissionToSubmissionResponseDTO).toList();

        when(this.submissionRepository.findByDiseaseStage_id(anyLong())).thenReturn(submissionList);

        when(this.submissionMapper.toSubmissionResponseDTO(firstSubmission)).thenReturn(submissionResponseDTOList.get(0));
        when(this.submissionMapper.toSubmissionResponseDTO(secondSubmission)).thenReturn(submissionResponseDTOList.get(1));

        List<SubmissionResponseDTO> result = this.submissionService.getSubmissionByDiseaseStageId(3L);

        assertEquals(2, result.size());
        assertAll("First submission",
                () -> assertEquals(3L, result.get(0).getDiseaseStage().getId()));

        assertAll("Second submission", () -> assertEquals(3L, result.get(1).getDiseaseStage().getId()));

        verify(this.submissionRepository, times(1)).findByDiseaseStage_id(3L);
        verify(this.submissionMapper, times(1)).toSubmissionResponseDTO(firstSubmission);
        verify(this.submissionMapper, times(1)).toSubmissionResponseDTO(secondSubmission);
    }

    @Test
    void getSubmissionByDiseaseStageId_shouldEmptyList_whenThereAreNoSubmissionsByGivenDiseaseStageId() {
        when(this.submissionRepository.findByDiseaseStage_id(anyLong())).thenReturn(Collections.emptyList());
        List<SubmissionResponseDTO> result = this.submissionService.getSubmissionByDiseaseStageId(3L);

        assertEquals(0, result.size());

        verify(this.submissionRepository, times(1)).findByDiseaseStage_id(3L);
        verify(this.submissionMapper, times(0)).toSubmissionResponseDTO(null);
    }

    @Test
    void getSubmissionsByUserProfileId_shouldReturnSubmissions_whenValidUserProfileIdIsGiven() {
        UserProfile userProfile = this.getUserProfile(5L);
        DiseaseStage diseaseStage = this.getDiseaseStage(2);
        Status status = this.getStatus(2);
        Submission submission = this.createSubmission(33L, "patient_33", LocalDateTime.now(), true, diseaseStage, userProfile, status);
        SubmissionResponseDTO dto = this.submissionToSubmissionResponseDTO(submission);

        when(this.submissionRepository.findByUserProfile_id(anyLong())).thenReturn(List.of(submission));
        when(this.submissionMapper.toSubmissionResponseDTO(submission)).thenReturn(dto);

        List<SubmissionResponseDTO> result = this.submissionService.getSubmissionsByUserProfileId(5L);
        assertEquals(1, result.size());
        assertEquals(5L, result.get(0).getUserProfile().getId());
        verify(this.submissionRepository,times(1)).findByUserProfile_id(5L);
        verify(this.submissionMapper,times(1)).toSubmissionResponseDTO(submission);
    }

    @Test
    void getSubmissionsByUserProfileId_shouldReturnEmptyList_whenThereAreNoSubmissionsByGivenUserProfileId(){

        when(this.submissionRepository.findByUserProfile_id(anyLong())).thenReturn(Collections.emptyList());
        List<SubmissionResponseDTO> result = this.submissionService.getSubmissionsByUserProfileId(5L);
        assertEquals(0,result.size());
        verify(this.submissionRepository,times(1)).findByUserProfile_id(5L);
        verify(this.submissionMapper,times(0)).toSubmissionResponseDTO(null);
    }

    private Submission createSubmission(long id, String patientReferenceId, LocalDateTime createdDateTime, boolean isActive, DiseaseStage diseaseStage, UserProfile userProfile, Status status) {
        boolean hasDisease = diseaseStage.getDiseaseLevel() != 0;

        Submission submission = new Submission(id, patientReferenceId, createdDateTime, isActive, hasDisease, diseaseStage, userProfile);
        submission.setStatus(status);
        return submission;
    }

    private Status getStatus(int index) {

        Status status = mock(Status.class);
//        if (index == 1) {
//            when(status.getId()).thenReturn(4L);
//            when(status.getName()).thenReturn("completed");
//            when(status.getValue()).thenReturn(2);
//        } else if (index == 2) {
//            when(status.getId()).thenReturn(5L);
//            when(status.getName()).thenReturn("rejected");
//            when(status.getValue()).thenReturn(3);
//        } else {
//            when(status.getId()).thenReturn(7L);
//            when(status.getName()).thenReturn("pending");
//            when(status.getValue()).thenReturn(0);
//        }

        return status;
    }

    private DiseaseStage getDiseaseStage(int diseaseLevel) {
        DiseaseStage diseaseStage = mock(DiseaseStage.class);
        when(diseaseStage.getDiseaseLevel()).thenReturn(diseaseLevel);
//        when(diseaseStage.getId()).thenReturn(3L);
//        when(diseaseStage.getName()).thenReturn("Disease test name");
        return diseaseStage;
    }

    private UserProfile getUserProfile(long id) {
        UserProfile userProfile = mock(UserProfile.class);
        when(userProfile.getId()).thenReturn(id);
        return userProfile;
    }


    private UserProfileDetailResponseDTO getUserProfileDetailResponseDTO(UserProfile userProfile) {
        return new UserProfileDetailResponseDTO(userProfile.getId());
    }

    private DiseaseStageDetailResponseDTO getDiseaseStageDetailResponseDTO(DiseaseStage ds) {
        return new DiseaseStageDetailResponseDTO(ds.getId(), ds.getName(), ds.getDiseaseLevel());
    }

    private StatusDetailResponseDTO getStatusDetailResponseDTO(Status s) {
        return new StatusDetailResponseDTO(s.getId(), s.getName());
    }

    private SubmissionResponseDTO submissionToSubmissionResponseDTO(Submission s) {

        UserProfileDetailResponseDTO userProfileDetail = this.getUserProfileDetailResponseDTO(s.getUserProfile());
        DiseaseStageDetailResponseDTO diseaseStageDetail = this.getDiseaseStageDetailResponseDTO(s.getDiseaseStage());

        StatusDetailResponseDTO statusDetail = this.getStatusDetailResponseDTO(s.getStatus());
        return new SubmissionResponseDTO(s.getSubmissionId(), s.getPatientReferenceId(), s.getCreatedDateTime(), s.getIsActive(), s.getHasDisease(), diseaseStageDetail, statusDetail, userProfileDetail);
    }

}