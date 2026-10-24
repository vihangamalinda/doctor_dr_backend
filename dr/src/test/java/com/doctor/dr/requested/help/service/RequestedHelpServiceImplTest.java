package com.doctor.dr.requested.help.service;

import com.doctor.dr.contact.entity.Contact;
import com.doctor.dr.contact.service.information.ContactInformation;
import com.doctor.dr.exception.ResourceNotFoundException;
import com.doctor.dr.requested.help.dto.request.RequestedHelpFeedbackRequestDTO;
import com.doctor.dr.requested.help.dto.request.RequestedHelpRequestDTO;
import com.doctor.dr.requested.help.dto.request.UserProfileDetailRequestDTO;
import com.doctor.dr.requested.help.dto.response.RequestedHelpResponseDTO;
import com.doctor.dr.requested.help.entity.RequestedHelp;
import com.doctor.dr.requested.help.mapper.RequestedHelpMapper;
import com.doctor.dr.requested.help.repository.RequestedHelpRepository;
import com.doctor.dr.status.entity.Status;
import com.doctor.dr.status.service.information.StatusInformation;
import com.doctor.dr.user.profile.entity.UserProfile;
import com.doctor.dr.user.profile.service.information.UserProfileDetailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestedHelpServiceImplTest {
    @Mock
    private RequestedHelpRepository requestedHelpRepository;
    @Mock
    private RequestedHelpMapper requestedHelpMapper;
    @Mock
    private StatusInformation statusInformation;
    @Mock
    private ContactInformation contactInformation;
    @Mock
    private UserProfileDetailService userProfileDetailService;
    @InjectMocks
    private RequestedHelpServiceImpl requestedHelpService;

    @Test
    void getAll_shouldReturnEmptyList_whenNoRequestedHelpExist() {
        when(this.requestedHelpRepository.findAllByIsActiveTrue()).thenReturn(Collections.emptyList());

        List<RequestedHelpResponseDTO> result = this.requestedHelpService.getAll();

        assertEquals(0, result.size());
        verify(this.requestedHelpRepository, times(1)).findAllByIsActiveTrue();
        verify(this.requestedHelpMapper, never()).toRequestedHelpResponseDTO(any(RequestedHelp.class));
    }

    @Test
    void getAll_shouldReturnRequestedHelps_whenRequestedHelpExist() {
        RequestedHelp requestedHelp = mock(RequestedHelp.class);
        RequestedHelpResponseDTO responseDTO = mock(RequestedHelpResponseDTO.class);
        when(this.requestedHelpRepository.findAllByIsActiveTrue()).thenReturn(List.of(requestedHelp));
        when(this.requestedHelpMapper.toRequestedHelpResponseDTO(any(RequestedHelp.class))).thenReturn(responseDTO);

        List<RequestedHelpResponseDTO> result = this.requestedHelpService.getAll();

        assertEquals(1, result.size());
        verify(this.requestedHelpRepository, times(1)).findAllByIsActiveTrue();
        verify(this.requestedHelpMapper, times(1)).toRequestedHelpResponseDTO(any(RequestedHelp.class));
    }

    @Test
    void getById_shouldThrowResourceNotFoundException_whenInvalidIdIsGiven() {
        when(this.requestedHelpRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> this.requestedHelpService.getById(44L));

        assertEquals("Id not found.", exception.getMessage());
        verify(this.requestedHelpRepository, times(1)).findById(any(Long.class));
        verify(this.requestedHelpMapper, never()).toRequestedHelpResponseDTO(any(RequestedHelp.class));
    }

    @Test
    void getById_shouldReturnRequestedHelp_whenValidIdIsGiven() {
        RequestedHelp requestedHelp = mock(RequestedHelp.class);
        RequestedHelpResponseDTO responseDTO = mock(RequestedHelpResponseDTO.class);

        when(this.requestedHelpRepository.findById(anyLong())).thenReturn(Optional.of(requestedHelp));
        when(this.requestedHelpMapper.toRequestedHelpResponseDTO(any(RequestedHelp.class))).thenReturn(responseDTO);

        RequestedHelpResponseDTO result = this.requestedHelpService.getById(44L);

        assertNotNull(result);
        verify(this.requestedHelpRepository, times(1)).findById(any(Long.class));
        verify(this.requestedHelpMapper, times(1)).toRequestedHelpResponseDTO(any(RequestedHelp.class));
    }

    @Test
    void deleteById_shouldThrowResourceNotFoundException_whenInvalidIdIsGiven() {
        when(this.requestedHelpRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> this.requestedHelpService.deleteById(555L));

        assertEquals("Id not found.", exception.getMessage());
        verify(this.requestedHelpRepository, times(1)).findById(any(Long.class));
        verify(this.requestedHelpRepository, never()).save(any(RequestedHelp.class));
    }

    @Test
    void deleteById_shouldIgnore_whenDeactivatedRequestedHelpIdIsGiven() {
        RequestedHelp requestedHelp = mock(RequestedHelp.class);
        when(requestedHelp.getIsActive()).thenReturn(false);

        when(this.requestedHelpRepository.findById(anyLong())).thenReturn(Optional.of(requestedHelp));

        this.requestedHelpService.deleteById(555L);

        verify(this.requestedHelpRepository, times(1)).findById(any(Long.class));
        verify(this.requestedHelpRepository, never()).save(any(RequestedHelp.class));
    }

    @Test
    void deleteById_shouldDeactivated_whenActiveRequestedHelpIdIsGiven() {
        RequestedHelp requestedHelp = mock(RequestedHelp.class);
        when(requestedHelp.getIsActive()).thenReturn(true);
        when(this.requestedHelpRepository.findById(anyLong())).thenReturn(Optional.of(requestedHelp));

        this.requestedHelpService.deleteById(555L);

        verify(this.requestedHelpRepository, times(1)).findById(any(Long.class));
        verify(this.requestedHelpRepository, times(1)).save(any(RequestedHelp.class));
    }

    @Test
    void getRequestedHelpByCreatedUserProfileId_shouldReturnEmptyList_whenInvalidUserProfileIdIsGiven() {
        when(this.requestedHelpRepository.findByCreatedByUserProfile_id(anyLong())).thenReturn(Collections.emptyList());
        List<RequestedHelpResponseDTO> result = this.requestedHelpService.getRequestedHelpByCreatedUserProfileId(5L);
        assertEquals(0, result.size());
        verify(this.requestedHelpRepository, times(1)).findByCreatedByUserProfile_id(any(Long.class));
        verify(this.requestedHelpMapper, never()).toRequestedHelpResponseDTO(any(RequestedHelp.class));
    }

    @Test
    void getRequestedHelpByCreatedUserProfileId_shouldReturnRequestedHelps_whenValidUserProfileIdIsGiven() {
        RequestedHelp requestedHelp = mock(RequestedHelp.class);

        RequestedHelpResponseDTO responseDTO = mock(RequestedHelpResponseDTO.class);

        when(this.requestedHelpRepository.findByCreatedByUserProfile_id(anyLong())).thenReturn(List.of(requestedHelp));
        when(this.requestedHelpMapper.toRequestedHelpResponseDTO(any(RequestedHelp.class))).thenReturn(responseDTO);

        List<RequestedHelpResponseDTO> result = this.requestedHelpService.getRequestedHelpByCreatedUserProfileId(5L);
        assertEquals(1, result.size());
        verify(this.requestedHelpRepository, times(1)).findByCreatedByUserProfile_id(any(Long.class));
        verify(this.requestedHelpMapper, times(1)).toRequestedHelpResponseDTO(any(RequestedHelp.class));
    }

    @Test
    void getRequestedHelpByReviewedUserProfileId_shouldReturnEmptyList_whenInvalidUserProfileIdIsGiven() {
        when(this.requestedHelpRepository.findByReviewedByUserProfile_id(anyLong())).thenReturn(Collections.emptyList());

        List<RequestedHelpResponseDTO> result = this.requestedHelpService.getRequestedHelpByReviewedUserProfileId(5L);

        assertEquals(0, result.size());
        verify(this.requestedHelpRepository, times(1)).findByReviewedByUserProfile_id(any(Long.class));
        verify(this.requestedHelpMapper, never()).toRequestedHelpResponseDTO(any(RequestedHelp.class));
    }

    @Test
    void getRequestedHelpByReviewedUserProfileId_shouldReturnRequestedHelps_whenValidUserProfileIdIsGiven() {
        RequestedHelp requestedHelp = mock(RequestedHelp.class);

        RequestedHelpResponseDTO responseDTO = mock(RequestedHelpResponseDTO.class);

        when(this.requestedHelpRepository.findByReviewedByUserProfile_id(anyLong())).thenReturn(List.of(requestedHelp));
        when(this.requestedHelpMapper.toRequestedHelpResponseDTO(any(RequestedHelp.class))).thenReturn(responseDTO);

        List<RequestedHelpResponseDTO> result = this.requestedHelpService.getRequestedHelpByReviewedUserProfileId(5L);

        assertEquals(1, result.size());
        verify(this.requestedHelpRepository, times(1)).findByReviewedByUserProfile_id(any(Long.class));
        verify(this.requestedHelpMapper, times(1)).toRequestedHelpResponseDTO(any(RequestedHelp.class));
    }

    @Test
    void addFeedBack_shouldThrowIllegalArgumentException_whenContactIdIsNull() {
        RequestedHelpFeedbackRequestDTO requestDTO = mock(RequestedHelpFeedbackRequestDTO.class);
        when(requestDTO.getContactId()).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> this.requestedHelpService.addFeedBack(4L, requestDTO));

        assertEquals("Should provide valid inputs to the feedback", exception.getMessage());
        verify(this.requestedHelpRepository, never()).findById(any(Long.class));
        verify(this.contactInformation, never()).getContactById(any(Long.class));
        verify(this.statusInformation, never()).getStatusById(any(Long.class));
        verify(this.userProfileDetailService, never()).getUserProfileById(any(Long.class));
        verify(this.requestedHelpRepository, never()).save(any(RequestedHelp.class));

    }

    @Test
    void addFeedBack_shouldThrowIllegalArgumentException_whenFeedbackIsNull() {
        RequestedHelpFeedbackRequestDTO requestDTO = mock(RequestedHelpFeedbackRequestDTO.class);
        when(requestDTO.getContactId()).thenReturn(4L);
        when(requestDTO.getFeedback()).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> this.requestedHelpService.addFeedBack(4L, requestDTO));

        assertEquals("Should provide valid inputs to the feedback", exception.getMessage());
        verify(this.requestedHelpRepository, never()).findById(any(Long.class));
        verify(this.contactInformation, never()).getContactById(any(Long.class));
        verify(this.statusInformation, never()).getStatusById(any(Long.class));
        verify(this.userProfileDetailService, never()).getUserProfileById(any(Long.class));
        verify(this.requestedHelpRepository, never()).save(any(RequestedHelp.class));
    }

    @Test
    void addFeedBack_shouldThrowIllegalArgumentException_whenFeedbackIsBlank() {
        RequestedHelpFeedbackRequestDTO requestDTO = mock(RequestedHelpFeedbackRequestDTO.class);
        when(requestDTO.getContactId()).thenReturn(4L);
        when(requestDTO.getFeedback()).thenReturn("          ");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> this.requestedHelpService.addFeedBack(4L, requestDTO));

        assertEquals("Should provide valid inputs to the feedback", exception.getMessage());
        verify(this.requestedHelpRepository, never()).findById(any(Long.class));
        verify(this.contactInformation, never()).getContactById(any(Long.class));
        verify(this.statusInformation, never()).getStatusById(any(Long.class));
        verify(this.userProfileDetailService, never()).getUserProfileById(any(Long.class));
        verify(this.requestedHelpRepository, never()).save(any(RequestedHelp.class));

    }

    @Test
    void addFeedBack_shouldThrowIllegalArgumentException_whenReviewedByUserProfileIdIsNull() {
        RequestedHelpFeedbackRequestDTO requestDTO = mock(RequestedHelpFeedbackRequestDTO.class);
        when(requestDTO.getContactId()).thenReturn(4L);
        when(requestDTO.getFeedback()).thenReturn(" Feedback provided. ");
        when(requestDTO.getReviewedByUserProfileId()).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> this.requestedHelpService.addFeedBack(4L, requestDTO));

        assertEquals("Should provide valid inputs to the feedback", exception.getMessage());
        verify(this.requestedHelpRepository, never()).findById(any(Long.class));
        verify(this.contactInformation, never()).getContactById(any(Long.class));
        verify(this.statusInformation, never()).getStatusById(any(Long.class));
        verify(this.userProfileDetailService, never()).getUserProfileById(any(Long.class));
        verify(this.requestedHelpRepository, never()).save(any(RequestedHelp.class));

    }

    @Test
    void addFeedBack_shouldThrowResourceNotFoundException_whenInvalidIdIsGiven() {
        RequestedHelpFeedbackRequestDTO requestDTO = mock(RequestedHelpFeedbackRequestDTO.class);
        when(requestDTO.getContactId()).thenReturn(4L);
        when(requestDTO.getFeedback()).thenReturn(" Feedback provided. ");
        when(requestDTO.getReviewedByUserProfileId()).thenReturn(4L);

        when(this.requestedHelpRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> this.requestedHelpService.addFeedBack(4L, requestDTO));

        assertEquals("Id not found.", exception.getMessage());
        verify(this.requestedHelpRepository, times(1)).findById(any(Long.class));
        verify(this.contactInformation, never()).getContactById(any(Long.class));
        verify(this.statusInformation, never()).getStatusById(any(Long.class));
        verify(this.userProfileDetailService, never()).getUserProfileById(any(Long.class));
        verify(this.requestedHelpRepository, never()).save(any(RequestedHelp.class));

    }

    @Test
    void addFeedBack_shouldAddFeedBack_whenValidInformationIsGiven() {
        RequestedHelpFeedbackRequestDTO requestDTO = mock(RequestedHelpFeedbackRequestDTO.class);
        when(requestDTO.getContactId()).thenReturn(414L);
        when(requestDTO.getFeedback()).thenReturn(" Feedback provided. ");
        when(requestDTO.getReviewedByUserProfileId()).thenReturn(4L);


        RequestedHelp requestedHelp = mock(RequestedHelp.class);
        Contact contact = mock(Contact.class);
        Status status = mock(Status.class);
        UserProfile userProfile = mock(UserProfile.class);

        when(this.requestedHelpRepository.findById(anyLong())).thenReturn(Optional.of(requestedHelp));
        when(this.contactInformation.getContactById(anyLong())).thenReturn(contact);
        when(this.statusInformation.getStatusById(anyLong())).thenReturn(status);
        when(this.userProfileDetailService.getUserProfileById(anyLong())).thenReturn(userProfile);

        this.requestedHelpService.addFeedBack(45L, requestDTO);

        verify(this.requestedHelpRepository, times(1)).findById(any(Long.class));
        verify(this.contactInformation, times(1)).getContactById(any(Long.class));
        verify(this.statusInformation, times(1)).getStatusById(any(Long.class));
        verify(this.userProfileDetailService, times(1)).getUserProfileById(any(Long.class));
        verify(this.requestedHelpRepository, times(1)).save(any(RequestedHelp.class));
        verify(this.requestedHelpRepository, times(1)).save(any(RequestedHelp.class));
    }

    @Test
    void getRequestedHelpsByStatusValue_shouldReturnEmptyList_whenNoRequestedHelpsExistsForGivenStatusValue() {
        when(this.requestedHelpRepository.findByStatus_valueAndIsActiveTrue(anyLong())).thenReturn(Collections.emptyList());
        List<RequestedHelpResponseDTO> result = this.requestedHelpService.getRequestedHelpsByStatusValue(4L);
        assertEquals(0, result.size());
        verify(this.requestedHelpRepository, times(1)).findByStatus_valueAndIsActiveTrue(any(Long.class));
        verify(this.requestedHelpMapper, never()).toRequestedHelpResponseDTO(any(RequestedHelp.class));
    }

    @Test
    void getRequestedHelpsByStatusValue_shouldReturnEmptyList_whenRequestedHelpsExistsForGivenStatusValue(){
        RequestedHelp requestedHelp = mock(RequestedHelp.class);
        RequestedHelpResponseDTO responseDTO =mock(RequestedHelpResponseDTO.class);

        when(this.requestedHelpRepository.findByStatus_valueAndIsActiveTrue(anyLong())).thenReturn(List.of(requestedHelp));
        when(this.requestedHelpMapper.toRequestedHelpResponseDTO(any(RequestedHelp.class))).thenReturn(responseDTO);

        List<RequestedHelpResponseDTO> result = this.requestedHelpService.getRequestedHelpsByStatusValue(4L);
        assertEquals(1, result.size());
        verify(this.requestedHelpRepository, times(1)).findByStatus_valueAndIsActiveTrue(any(Long.class));
        verify(this.requestedHelpMapper, times(1)).toRequestedHelpResponseDTO(any(RequestedHelp.class));
    }

    @Test
    void create_shouldThrowIllegalArgumentException_whenCreatedByUserProfileIsNull() {
        RequestedHelpRequestDTO request = mock(RequestedHelpRequestDTO.class);
        when(request.getCreatedByUserProfile()).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> this.requestedHelpService.create(request));

        assertEquals("Invalid inputs.", exception.getMessage());
        verify(this.requestedHelpMapper, never()).toRequestedHelp(any(RequestedHelpRequestDTO.class));
        verify(this.statusInformation, never()).getStatusById(any(Long.class));
        verify(this.requestedHelpRepository, never()).save(any(RequestedHelp.class));
    }

    @Test
    void create_shouldThrowIllegalArgumentException_whenCreatedDateTimeIsNull(){
        UserProfileDetailRequestDTO userProfileDetail = mock(UserProfileDetailRequestDTO.class);
        RequestedHelpRequestDTO request =mock(RequestedHelpRequestDTO.class);
        when(request.getCreatedByUserProfile()).thenReturn(userProfileDetail);
        when(request.getCreatedDateTime()).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class,()->this.requestedHelpService.create(request));

        assertEquals("Invalid inputs.",exception.getMessage());
        verify(this.requestedHelpMapper,never()).toRequestedHelp(any(RequestedHelpRequestDTO.class));
        verify(this.statusInformation,never()).getStatusById(any(Long.class));
        verify(this.requestedHelpRepository,never()).save(any(RequestedHelp.class));
    }

    @Test
    void create_shouldThrowIllegalArgumentException_whenTitleIsNull(){
        UserProfileDetailRequestDTO userProfileDetail = mock(UserProfileDetailRequestDTO.class);

        RequestedHelpRequestDTO request =mock(RequestedHelpRequestDTO.class);
        when(request.getCreatedByUserProfile()).thenReturn(userProfileDetail);
        when(request.getCreatedDateTime()).thenReturn(LocalDateTime.now());
        when(request.getTitle()).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class,()->this.requestedHelpService.create(request));

        assertEquals("Invalid inputs.",exception.getMessage());
        verify(this.requestedHelpMapper,never()).toRequestedHelp(any(RequestedHelpRequestDTO.class));
        verify(this.statusInformation,never()).getStatusById(any(Long.class));
        verify(this.requestedHelpRepository,never()).save(any(RequestedHelp.class));

    }

    @Test
    void create_shouldThrowIllegalArgumentException_whenTitleIsBlank(){
        UserProfileDetailRequestDTO userProfileDetail = mock(UserProfileDetailRequestDTO.class);

        RequestedHelpRequestDTO request =mock(RequestedHelpRequestDTO.class);
        when(request.getCreatedByUserProfile()).thenReturn(userProfileDetail);
        when(request.getCreatedDateTime()).thenReturn(LocalDateTime.now());
        when(request.getTitle()).thenReturn("             ");

        Exception exception = assertThrows(IllegalArgumentException.class,()->this.requestedHelpService.create(request));

        assertEquals("Invalid inputs.",exception.getMessage());
        verify(this.requestedHelpMapper,never()).toRequestedHelp(any(RequestedHelpRequestDTO.class));
        verify(this.statusInformation,never()).getStatusById(any(Long.class));
        verify(this.requestedHelpRepository,never()).save(any(RequestedHelp.class));
    }

    @Test
    void create_shouldThrowIllegalArgumentException_whenDescriptionIsNull(){
        UserProfileDetailRequestDTO userProfileDetail = mock(UserProfileDetailRequestDTO.class);

        RequestedHelpRequestDTO request =mock(RequestedHelpRequestDTO.class);
        when(request.getCreatedByUserProfile()).thenReturn(userProfileDetail);
        when(request.getCreatedDateTime()).thenReturn(LocalDateTime.now());
        when(request.getTitle()).thenReturn("VALID_TITLE");
        when(request.getDescription()).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class,()->this.requestedHelpService.create(request));

        assertEquals("Invalid inputs.",exception.getMessage());
        verify(this.requestedHelpMapper,never()).toRequestedHelp(any(RequestedHelpRequestDTO.class));
        verify(this.statusInformation,never()).getStatusById(any(Long.class));
        verify(this.requestedHelpRepository,never()).save(any(RequestedHelp.class));
    }

    @Test
    void create_shouldThrowIllegalArgumentException_whenDescriptionIsBlank(){
        UserProfileDetailRequestDTO userProfileDetail = mock(UserProfileDetailRequestDTO.class);

        RequestedHelpRequestDTO request =mock(RequestedHelpRequestDTO.class);
        when(request.getCreatedByUserProfile()).thenReturn(userProfileDetail);
        when(request.getCreatedDateTime()).thenReturn(LocalDateTime.now());
        when(request.getTitle()).thenReturn("VALID_TITLE");
        when(request.getDescription()).thenReturn("                     ");

        Exception exception = assertThrows(IllegalArgumentException.class,()->this.requestedHelpService.create(request));

        assertEquals("Invalid inputs.",exception.getMessage());
        verify(this.requestedHelpMapper,never()).toRequestedHelp(any(RequestedHelpRequestDTO.class));
        verify(this.statusInformation,never()).getStatusById(any(Long.class));
        verify(this.requestedHelpRepository,never()).save(any(RequestedHelp.class));
    }

    @Test
    void create_shouldCreate_whenValidInputsAreGiven(){
        RequestedHelp requestedHelp = mock(RequestedHelp.class);
        Status status = mock(Status.class);
        UserProfileDetailRequestDTO userProfileDetail = mock(UserProfileDetailRequestDTO.class);

        RequestedHelpRequestDTO request =mock(RequestedHelpRequestDTO.class);
        when(request.getCreatedByUserProfile()).thenReturn(userProfileDetail);
        when(request.getCreatedDateTime()).thenReturn(LocalDateTime.now());
        when(request.getTitle()).thenReturn("VALID_TITLE");
        when(request.getDescription()).thenReturn("Valid Description.");

        when(this.requestedHelpMapper.toRequestedHelp(any())).thenReturn(requestedHelp);
        when(this.statusInformation.getStatusById(anyLong())).thenReturn(status);
        this.requestedHelpService.create(request);

        verify(this.requestedHelpMapper,times(1)).toRequestedHelp(any(RequestedHelpRequestDTO.class));
        verify(this.statusInformation,times(1)).getStatusById(any(Long.class));
        verify(this.requestedHelpRepository,times(1)).save(any(RequestedHelp.class));
    }


}