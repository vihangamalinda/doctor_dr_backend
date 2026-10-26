package com.doctor.dr.reported.issue.service;

import com.doctor.dr.exception.ResourceNotFoundException;
import com.doctor.dr.reported.issue.dto.request.ReportedIssueRequestDTO;
import com.doctor.dr.reported.issue.dto.response.ReportedIssueResponseDTO;
import com.doctor.dr.reported.issue.entity.ReportedIssue;
import com.doctor.dr.reported.issue.mapper.ReportedIssueMapper;
import com.doctor.dr.reported.issue.repository.ReportedIssueRepository;
import com.doctor.dr.status.entity.Status;
import com.doctor.dr.status.service.information.StatusInformation;
import io.jsonwebtoken.lang.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportedIssueServiceImplTest {

    @Mock
    private ReportedIssueRepository reportedIssueRepository;
    @Mock
    private ReportedIssueMapper reportedIssueMapper;
    @Mock
    private StatusInformation statusInformation;
    @InjectMocks
    private ReportedIssueServiceImpl reportedIssueService;

    @Test
    void getAll_shouldReturnEmptyList_whenNoReportedIssuesExist() {
        when(this.reportedIssueRepository.findAllByIsActiveTrue()).thenReturn(Collections.emptyList());

        List<ReportedIssueResponseDTO> result = this.reportedIssueService.getAll();

        assertEquals(0, result.size());
        verify(this.reportedIssueRepository, times(1)).findAllByIsActiveTrue();
        verify(this.reportedIssueMapper, never()).toReportedIssueResponseDTO(any(ReportedIssue.class));
    }

    @Test
    void getAll_shouldReturnReportedIssues_whenReportedIssuesExist() {
        ReportedIssue reportedIssue = mock(ReportedIssue.class);
        ReportedIssueResponseDTO responseDTO = mock(ReportedIssueResponseDTO.class);

        when(this.reportedIssueRepository.findAllByIsActiveTrue()).thenReturn(List.of(reportedIssue));
        when(this.reportedIssueMapper.toReportedIssueResponseDTO(any(ReportedIssue.class))).thenReturn(responseDTO);

        List<ReportedIssueResponseDTO> result = this.reportedIssueService.getAll();

        assertEquals(1, result.size());
        verify(this.reportedIssueRepository, times(1)).findAllByIsActiveTrue();
        verify(this.reportedIssueMapper, times(1)).toReportedIssueResponseDTO(any(ReportedIssue.class));
    }

    @Test
    void getById_shouldThrowResourceNotFoundException_whenInvalidIdIsGiven() {
        when(this.reportedIssueRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> this.reportedIssueService.getById(55L));
        verify(this.reportedIssueRepository, times(1)).findById(any(Long.class));
        verify(this.reportedIssueMapper, never()).toReportedIssueResponseDTO(any(ReportedIssue.class));
    }

    @Test
    void getById_shouldReturnReportedIssue_whenValidIdIsGiven() {
        ReportedIssue reportedIssue = mock(ReportedIssue.class);
        ReportedIssueResponseDTO responseDTO = mock(ReportedIssueResponseDTO.class);

        when(this.reportedIssueRepository.findById(anyLong())).thenReturn(Optional.of(reportedIssue));
        when(this.reportedIssueMapper.toReportedIssueResponseDTO(any())).thenReturn(responseDTO);

        ReportedIssueResponseDTO result = this.reportedIssueService.getById(55L);
        assertNotNull(result);
        verify(this.reportedIssueRepository, times(1)).findById(any(Long.class));
        verify(this.reportedIssueMapper, times(1)).toReportedIssueResponseDTO(any(ReportedIssue.class));
    }

    @Test
    void deleteById_shouldThrowResourceNotFoundException_whenInvalidIdIsGiven() {
        when(this.reportedIssueRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> this.reportedIssueService.deleteById(556L));

        verify(this.reportedIssueRepository, times(1)).findById(any(Long.class));
        verify(this.reportedIssueRepository, never()).save(any(ReportedIssue.class));
    }

    @Test
    void deleteById_shouldIgnore_whenDeactivatedValidIdIsGiven() {
        ReportedIssue reportedIssue = mock(ReportedIssue.class);
        when(reportedIssue.getIsActive()).thenReturn(false);
        when(this.reportedIssueRepository.findById(anyLong())).thenReturn(Optional.of(reportedIssue));

        this.reportedIssueService.deleteById(15L);

        verify(this.reportedIssueRepository, times(1)).findById(any(Long.class));
        verify(this.reportedIssueRepository, never()).save(any(ReportedIssue.class));
    }

    @Test
    void deleteById_shouldDeactivateReportedIssue_whenActivatedValidIdIsGiven() {
        ReportedIssue reportedIssue = mock(ReportedIssue.class);
        when(reportedIssue.getIsActive()).thenReturn(true);
        when(this.reportedIssueRepository.findById(anyLong())).thenReturn(Optional.of(reportedIssue));

        this.reportedIssueService.deleteById(15L);

        verify(this.reportedIssueRepository, times(1)).findById(any(Long.class));
        verify(this.reportedIssueRepository, times(1)).save(any(ReportedIssue.class));
    }

    @Test
    void getReportedIssueByUserProfileId_shouldReturnEmptyList_whenNoReportedIssuesExistForGivenUserProfileId() {
        when(this.reportedIssueRepository.findByUserProfile_id(anyLong())).thenReturn(Collections.emptyList());

        List<ReportedIssueResponseDTO> result = this.reportedIssueService.getReportedIssueByUserProfileId(44L);

        assertEquals(0, result.size());
        verify(this.reportedIssueRepository, times(1)).findByUserProfile_id(any(Long.class));
        verify(this.reportedIssueMapper, never()).toReportedIssueResponseDTO(any(ReportedIssue.class));
    }

    @Test
    void getReportedIssueByUserProfileId_shouldReturnReportedIssue_whenReportedIssuesExistForGivenUserProfileId(){
        ReportedIssue reportedIssue = mock(ReportedIssue.class);
        ReportedIssueResponseDTO responseDTO = mock(ReportedIssueResponseDTO.class);

        when(this.reportedIssueRepository.findByUserProfile_id(anyLong())).thenReturn(List.of(reportedIssue));
        when(this.reportedIssueMapper.toReportedIssueResponseDTO(any(ReportedIssue.class))).thenReturn(responseDTO);

        List<ReportedIssueResponseDTO> result = this.reportedIssueService.getReportedIssueByUserProfileId(44L);

        assertEquals(1, result.size());
        verify(this.reportedIssueRepository, times(1)).findByUserProfile_id(any(Long.class));
        verify(this.reportedIssueMapper, times(1)).toReportedIssueResponseDTO(any(ReportedIssue.class));
    }

    @Test
    void  create_shouldCreateReportedIssue_whenValidInformationIsGiven(){
        ReportedIssueRequestDTO requestDTO = mock(ReportedIssueRequestDTO.class);
        Status status =mock(Status.class);
        ReportedIssue reportedIssue = mock(ReportedIssue.class);

        when(this.reportedIssueMapper.toReportedIssue(any(ReportedIssueRequestDTO.class))).thenReturn(reportedIssue);
        when(this.statusInformation.getStatusById(any(Long.class))).thenReturn(status);
        when(this.reportedIssueRepository.save(any(ReportedIssue.class))).thenReturn(reportedIssue);
        this.reportedIssueService.create(requestDTO);

        verify(this.reportedIssueMapper,times(1)).toReportedIssue(any(ReportedIssueRequestDTO.class));
        verify(this.statusInformation,times(1)).getStatusById(any(Long.class));
        verify(this.reportedIssueRepository,times(1)).save(any(ReportedIssue.class));
    }
}