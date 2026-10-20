package com.doctor.dr.disease.stage.service;

import com.doctor.dr.disease.stage.dto.DiseaseStageRequestDTO;
import com.doctor.dr.disease.stage.dto.DiseaseStageResponseDTO;
import com.doctor.dr.disease.stage.entity.DiseaseStage;
import com.doctor.dr.disease.stage.mapper.DiseaseStageMapper;
import com.doctor.dr.disease.stage.repository.DiseaseStageRepository;
import com.doctor.dr.exception.DataInvalidException;
import com.doctor.dr.exception.ResourceNotFoundException;
import com.doctor.dr.submission.dto.response.DiseaseStageDetailResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiseaseStageServiceImplTest {
    @Mock
    private DiseaseStageRepository diseaseStageRepository;
    @Mock
    private DiseaseStageMapper diseaseStageMapper;
    @InjectMocks
    private DiseaseStageServiceImpl diseaseStageService;


    @Test
    void getAll_shouldReturnDiseaseStages_whenDiseaseStagesExist() {
        DiseaseStage stageOne = getDiseaseStage(1L, "Healthy");
        DiseaseStage stageTwo = getDiseaseStage(2L, "Diseased_level_01");
        DiseaseStage stageThree = getDiseaseStage(3L, "Diseased_level_02");
        DiseaseStage stageFour = getDiseaseStage(4L, "Diseased_level_03");

        List<DiseaseStage> diseaseStageList = List.of(
                stageOne,
                stageTwo,
                stageThree,
                stageFour
        );

        List<DiseaseStageResponseDTO> diseaseStageResponseDTOList = diseaseStageList.stream().map(this::diseaseStageToDiseaseStageResponse).toList();

        when(this.diseaseStageRepository.findAllByIsActiveTrue()).thenReturn(diseaseStageList);

        when(this.diseaseStageMapper.toDiseaseStageResponseDTO(stageOne)).thenReturn(diseaseStageResponseDTOList.get(0));
        when(this.diseaseStageMapper.toDiseaseStageResponseDTO(stageTwo)).thenReturn(diseaseStageResponseDTOList.get(1));
        when(this.diseaseStageMapper.toDiseaseStageResponseDTO(stageThree)).thenReturn(diseaseStageResponseDTOList.get(2));
        when(this.diseaseStageMapper.toDiseaseStageResponseDTO(stageFour)).thenReturn(diseaseStageResponseDTOList.get(3));

        List<DiseaseStageResponseDTO> result = this.diseaseStageService.getAll();
        assertEquals(4, result.size());

        assertEquals("Healthy", result.get(0).getName());
        assertEquals("Diseased_level_01", result.get(1).getName());
        assertEquals("Diseased_level_02", result.get(2).getName());
        assertEquals("Diseased_level_03", result.get(3).getName());

        verify(this.diseaseStageRepository, times(1)).findAllByIsActiveTrue();
        verify(this.diseaseStageMapper, times(1)).toDiseaseStageResponseDTO(stageOne);
        verify(this.diseaseStageMapper, times(1)).toDiseaseStageResponseDTO(stageTwo);
        verify(this.diseaseStageMapper, times(1)).toDiseaseStageResponseDTO(stageThree);
        verify(this.diseaseStageMapper, times(1)).toDiseaseStageResponseDTO(stageFour);
    }

    @Test
    void getAll_shouldReturnEmptyList_whenNoDiseaseStagesExist() {
        when(this.diseaseStageRepository.findAllByIsActiveTrue()).thenReturn(Collections.emptyList());

        List<DiseaseStageResponseDTO> result = this.diseaseStageService.getAll();
        assertEquals(0, result.size());
        verify(this.diseaseStageRepository, times(1)).findAllByIsActiveTrue();
        verify(this.diseaseStageMapper, times(0)).toDiseaseStageResponseDTO(null);
    }

    @Test
    void getById_shouldReturnDiseaseStage_whenValidIdIsGiven() {
        DiseaseStage diseaseStage = this.getDiseaseStage(1L, "Healthy");
        DiseaseStageResponseDTO responseDTO = mock(DiseaseStageResponseDTO.class);
        when(responseDTO.getId()).thenReturn(diseaseStage.getId());


        when(this.diseaseStageRepository.findById(anyLong())).thenReturn(Optional.of(diseaseStage));
        when(this.diseaseStageMapper.toDiseaseStageResponseDTO(diseaseStage)).thenReturn(responseDTO);

        DiseaseStageResponseDTO result = this.diseaseStageService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Healthy", diseaseStage.getName());
        verify(this.diseaseStageRepository, times(1)).findById(1L);
        verify(this.diseaseStageMapper, times(1)).toDiseaseStageResponseDTO(diseaseStage);

    }

    @Test
    void getById_shouldThrowResourceNotFoundException_whenInvalidIdIsGiven() {

        when(this.diseaseStageRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> this.diseaseStageService.getById(1L));

        assertEquals("Id not found.", exception.getMessage());
        verify(this.diseaseStageRepository, times(1)).findById(1L);
        verify(this.diseaseStageMapper, times(0)).toDiseaseStageResponseDTO(null);
    }

    @Test
    void deleteById_shouldDeactivateDiseaseStage_whenValidDiseaseStageIdIsGiven() {
        DiseaseStage diseaseStage = this.getDiseaseStage(1L, "Healthy");
        diseaseStage.setIsActive(true);

        when(this.diseaseStageRepository.findById(anyLong())).thenReturn(Optional.of(diseaseStage));

        this.diseaseStageService.deleteById(1L);

        verify(this.diseaseStageRepository, times(1)).findById(1L);
        verify(this.diseaseStageRepository, times(1)).save(diseaseStage);
    }

    @Test
    void deleteById_shouldNotDeactivateDiseaseStage_whenDeactivatedDiseaseStageIdIsGiven() {
        DiseaseStage diseaseStage = this.getDiseaseStage(1L, "Healthy");

        when(this.diseaseStageRepository.findById(anyLong())).thenReturn(Optional.of(diseaseStage));

        this.diseaseStageService.deleteById(1L);

        verify(this.diseaseStageRepository, times(1)).findById(1L);
        verify(this.diseaseStageRepository, times(0)).save(diseaseStage);
    }

    @Test
    void deleteById_shouldThrowResourceNotFoundException_whenInvalidDiseaseStageIdIsGiven() {

        when(this.diseaseStageRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> this.diseaseStageService.deleteById(1L));

        assertEquals("Id not found.", exception.getMessage());
        verify(this.diseaseStageRepository, times(1)).findById(1L);
    }

    @Test
    void create_shouldCreate_whenValidInformationIsProvided() {
        DiseaseStageRequestDTO requestDTO = mock(DiseaseStageRequestDTO.class);
        when(requestDTO.getName()).thenReturn("Disease level 01");
        when(requestDTO.getDescription()).thenReturn("Initial disease stage.");
        when(requestDTO.getDiseaseLevel()).thenReturn(1);
        DiseaseStage diseaseStage = mock(DiseaseStage.class);
        when(this.diseaseStageMapper.toDiseaseStage(requestDTO)).thenReturn(diseaseStage);

        this.diseaseStageService.create(requestDTO);

        verify(this.diseaseStageMapper, times(1)).toDiseaseStage(requestDTO);
        verify(this.diseaseStageRepository, times(1)).save(diseaseStage);
    }

    @Test
    void create_shouldThrowDataInvalidException_whenDiseaseLevelIsNull() {
        DiseaseStageRequestDTO requestDTO = mock(DiseaseStageRequestDTO.class);
//        when(requestDTO.getName()).thenReturn("Disease level 01");
//        when(requestDTO.getDescription()).thenReturn("Initial disease stage.");
        when(requestDTO.getDiseaseLevel()).thenReturn(null);


        Exception exception = assertThrows(DataInvalidException.class, () -> this.diseaseStageService.create(requestDTO));
        assertEquals("valid input has not been provided", exception.getMessage());
        verify(this.diseaseStageMapper, times(0)).toDiseaseStage(requestDTO);
        verify(this.diseaseStageRepository, never()).save(any(DiseaseStage.class));
    }

    @Test
    void create_shouldThrowDataInvalidException_whenNameIsNull() {
        DiseaseStageRequestDTO requestDTO = mock(DiseaseStageRequestDTO.class);
        when(requestDTO.getName()).thenReturn(null);
//        when(requestDTO.getDescription()).thenReturn("Initial disease stage.");
        when(requestDTO.getDiseaseLevel()).thenReturn(2);


        Exception exception = assertThrows(DataInvalidException.class, () -> this.diseaseStageService.create(requestDTO));
        assertEquals("valid input has not been provided", exception.getMessage());
        verify(this.diseaseStageMapper, times(0)).toDiseaseStage(requestDTO);
        verify(this.diseaseStageRepository, never()).save(any(DiseaseStage.class));
    }

    @Test
    void create_shouldThrowDataInvalidException_whenNameIsBlank() {
        DiseaseStageRequestDTO requestDTO = mock(DiseaseStageRequestDTO.class);
        when(requestDTO.getName()).thenReturn("  ");
//        when(requestDTO.getDescription()).thenReturn("Initial disease stage.");
        when(requestDTO.getDiseaseLevel()).thenReturn(2);


        Exception exception = assertThrows(DataInvalidException.class, () -> this.diseaseStageService.create(requestDTO));
        assertEquals("valid input has not been provided", exception.getMessage());
        verify(this.diseaseStageMapper, times(0)).toDiseaseStage(requestDTO);
        verify(this.diseaseStageRepository, never()).save(any(DiseaseStage.class));
    }

    @Test
    void create_shouldThrowDataInvalidException_whenDescriptionIsNull() {
        DiseaseStageRequestDTO requestDTO = mock(DiseaseStageRequestDTO.class);
        when(requestDTO.getName()).thenReturn("Disease name");
        when(requestDTO.getDescription()).thenReturn(null);
        when(requestDTO.getDiseaseLevel()).thenReturn(2);


        Exception exception = assertThrows(DataInvalidException.class, () -> this.diseaseStageService.create(requestDTO));
        assertEquals("valid input has not been provided", exception.getMessage());
        verify(this.diseaseStageMapper, times(0)).toDiseaseStage(requestDTO);
        verify(this.diseaseStageRepository, never()).save(any(DiseaseStage.class));
    }

    @Test
    void create_shouldThrowDataInvalidException_whenDescriptionIsBlank() {
        DiseaseStageRequestDTO requestDTO = mock(DiseaseStageRequestDTO.class);
        when(requestDTO.getName()).thenReturn("Disease name");
        when(requestDTO.getDescription()).thenReturn("   ");
        when(requestDTO.getDiseaseLevel()).thenReturn(2);


        Exception exception = assertThrows(DataInvalidException.class, () -> this.diseaseStageService.create(requestDTO));
        assertEquals("valid input has not been provided", exception.getMessage());
        verify(this.diseaseStageMapper, times(0)).toDiseaseStage(requestDTO);
        verify(this.diseaseStageRepository, never()).save(any(DiseaseStage.class));
    }


    private DiseaseStageResponseDTO diseaseStageToDiseaseStageResponse(DiseaseStage response) {
        DiseaseStageResponseDTO diseaseStageResponseDTO = mock(DiseaseStageResponseDTO.class);
        when(diseaseStageResponseDTO.getName()).thenReturn(response.getName());
//        when(diseaseStageResponseDTO.getId()).thenReturn(response.getId());
        return diseaseStageResponseDTO;
    }

    private DiseaseStage getDiseaseStage(long id, String name) {
        DiseaseStage diseaseStage = new DiseaseStage();
        diseaseStage.setId(id);
        diseaseStage.setName(name);
        return diseaseStage;
    }
}