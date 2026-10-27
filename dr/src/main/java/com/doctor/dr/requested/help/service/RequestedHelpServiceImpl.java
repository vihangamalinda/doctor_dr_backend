package com.doctor.dr.requested.help.service;

import com.doctor.dr.contact.entity.Contact;
import com.doctor.dr.contact.service.information.ContactInformation;
import com.doctor.dr.exception.ResourceNotFoundException;
import com.doctor.dr.requested.help.dto.request.RequestedHelpFeedbackRequestDTO;
import com.doctor.dr.requested.help.dto.request.RequestedHelpRequestDTO;
import com.doctor.dr.requested.help.dto.response.RequestedHelpResponseDTO;
import com.doctor.dr.requested.help.entity.RequestedHelp;
import com.doctor.dr.requested.help.mapper.RequestedHelpMapper;
import com.doctor.dr.requested.help.repository.RequestedHelpRepository;
import com.doctor.dr.status.entity.Status;
import com.doctor.dr.status.service.information.StatusInformation;
import com.doctor.dr.user.profile.entity.UserProfile;
import com.doctor.dr.user.profile.service.information.UserProfileDetailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.doctor.dr.requested.help.constants.RequestedHelpStatusId.CREATED_STATUS_ID;
import static com.doctor.dr.requested.help.constants.RequestedHelpStatusId.FEEDBACK_PROVIDED_STATUS_ID;

@Service
public class RequestedHelpServiceImpl implements RequestedHelpService {
    private final RequestedHelpRepository requestedHelpRepository;
    private final RequestedHelpMapper requestedHelpMapper;
    private final StatusInformation statusInformation;
    private final ContactInformation contactInformation;
    private final UserProfileDetailService userProfileDetailService;

    public RequestedHelpServiceImpl(RequestedHelpRepository requestedHelpRepository, RequestedHelpMapper requestedHelpMapper, StatusInformation statusInformation, ContactInformation contactInformation, UserProfileDetailService userProfileDetailService) {
        this.requestedHelpRepository = requestedHelpRepository;
        this.requestedHelpMapper = requestedHelpMapper;
        this.statusInformation = statusInformation;
        this.contactInformation = contactInformation;
        this.userProfileDetailService = userProfileDetailService;
    }

    @Override
    public List<RequestedHelpResponseDTO> getAll() {
        List<RequestedHelp> requestedHelpList = this.requestedHelpRepository.findAllByIsActiveTrue();
        return requestedHelpList.stream().map(this.requestedHelpMapper::toRequestedHelpResponseDTO).collect(Collectors.toList());
    }

    @Override
    public RequestedHelpResponseDTO getById(long id) {
        RequestedHelp requestedHelp = findById(id);
        return this.requestedHelpMapper.toRequestedHelpResponseDTO(requestedHelp);
    }

    private RequestedHelp findById(long id) {
        return this.requestedHelpRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found."));
    }

    @Override
    public void create(RequestedHelpRequestDTO requestedHelpRequestDTO) {
        this.validate(requestedHelpRequestDTO);

        RequestedHelp requestedHelp = this.requestedHelpMapper.toRequestedHelp(requestedHelpRequestDTO);
        requestedHelp.setIsActive(true);
        Status status = this.statusInformation.getStatusById(CREATED_STATUS_ID);
        requestedHelp.setStatus(status);
        persistEntity(requestedHelp);
    }

    private void persistEntity(RequestedHelp requestedHelp) {
        this.requestedHelpRepository.save(requestedHelp);
    }

    @Override
    public void deleteById(long id) {
        RequestedHelp requestedHelp = findById(id);
        if (requestedHelp.getIsActive()) {
            requestedHelp.setIsActive(false);
            persistEntity(requestedHelp);
        }
    }

    @Override
    public List<RequestedHelpResponseDTO> getRequestedHelpByCreatedUserProfileId(long id) {
        List<RequestedHelp> requestedHelps = this.requestedHelpRepository.findByCreatedByUserProfile_id(id);

        return requestedHelps.stream().map(this.requestedHelpMapper::toRequestedHelpResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<RequestedHelpResponseDTO> getRequestedHelpByReviewedUserProfileId(long id) {
        List<RequestedHelp> requestedHelps = this.requestedHelpRepository.findByReviewedByUserProfile_id(id);

        return requestedHelps.stream().map(this.requestedHelpMapper::toRequestedHelpResponseDTO).collect(Collectors.toList());
    }

    @Override
    public void addFeedBack(Long id,RequestedHelpFeedbackRequestDTO dto) {
        // need to updated the status after reviewing and adding the feedbacks
        this.validate(dto);

        RequestedHelp requestedHelp =this.findById(id);
        Contact contact =this.contactInformation.getContactById(dto.getContactId());
        Status status =this.statusInformation.getStatusById(FEEDBACK_PROVIDED_STATUS_ID);
        UserProfile userProfile = this.userProfileDetailService.getUserProfileById(dto.getReviewedByUserProfileId());

        requestedHelp.setReviewedByUserProfile(userProfile);
        requestedHelp.setContact(contact);
        requestedHelp.setFeedback(dto.getFeedback());
        requestedHelp.setStatus(status);
        this.requestedHelpRepository.save(requestedHelp);
    }


    @Override
    public List<RequestedHelpResponseDTO> getRequestedHelpsByStatusValue(long value) {
        List<RequestedHelp> requestedHelps = this.requestedHelpRepository.findByStatus_valueAndIsActiveTrue(value);

        return requestedHelps.stream().map(this.requestedHelpMapper::toRequestedHelpResponseDTO).collect(Collectors.toList());
    }


    private void validate(RequestedHelpFeedbackRequestDTO dto) {
        if(dto.getContactId() ==null || dto.getFeedback() == null || dto.getFeedback().isBlank() || dto.getReviewedByUserProfileId() ==null){
            throw  new IllegalArgumentException("Should provide valid inputs to the feedback");
        }
    }

    private void validate(RequestedHelpRequestDTO dto){
        if(dto.getCreatedByUserProfile()==null || dto.getCreatedDateTime() ==null || dto.getTitle() ==null ||dto.getTitle().isBlank() ||dto.getDescription() == null ||dto.getDescription().isBlank()){
            throw new IllegalArgumentException("Invalid inputs.");
        }
    }
}
