package com.doctor.dr.requested.help.service;

import com.doctor.dr.requested.help.dto.request.RequestedHelpRequestDTO;
import com.doctor.dr.requested.help.dto.response.RequestedHelpResponseDTO;
import com.doctor.dr.requested.help.entity.RequestedHelp;
import com.doctor.dr.requested.help.mapper.RequestedHelpMapper;
import com.doctor.dr.requested.help.repository.RequestedHelpRepository;
import com.doctor.dr.status.entity.Status;
import com.doctor.dr.status.service.information.StatusInformation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestedHelpServiceImpl implements RequestedHelpService {
    private final RequestedHelpRepository requestedHelpRepository;
    private final RequestedHelpMapper requestedHelpMapper;
    private final StatusInformation statusInformation;

    public RequestedHelpServiceImpl(RequestedHelpRepository requestedHelpRepository, RequestedHelpMapper requestedHelpMapper, StatusInformation statusInformation) {
        this.requestedHelpRepository = requestedHelpRepository;
        this.requestedHelpMapper = requestedHelpMapper;
        this.statusInformation = statusInformation;
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
        return this.requestedHelpRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found."));
    }

    @Override
    public void create(RequestedHelpRequestDTO requestedHelpRequestDTO) {
        RequestedHelp requestedHelp = this.requestedHelpMapper.toRequestedHelp(requestedHelpRequestDTO);
        requestedHelp.setIsActive(true);
        Status status = this.statusInformation.getStatusById(10003);
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
}
