package com.doctor.dr.requested.help.service;

import com.doctor.dr.requested.help.dto.request.RequestedHelpRequestDTO;
import com.doctor.dr.requested.help.dto.response.RequestedHelpResponseDTO;

import java.util.List;

public interface RequestedHelpService {
    List<RequestedHelpResponseDTO> getAll();

    RequestedHelpResponseDTO getById(long id);

    void create(RequestedHelpRequestDTO requestedHelpRequestDTO);

    void deleteById(long id);

    List<RequestedHelpResponseDTO> getRequestedHelpByCreatedUserProfileId(long id);

    List<RequestedHelpResponseDTO> getRequestedHelpByReviewedUserProfileId(long id);
}
