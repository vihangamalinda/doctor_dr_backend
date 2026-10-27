package com.doctor.dr.disease.stage.service.information;

import com.doctor.dr.disease.stage.entity.DiseaseStage;
import com.doctor.dr.disease.stage.repository.DiseaseStageRepository;
import com.doctor.dr.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiseaseStageInformationImplTest {

    @Mock
    private DiseaseStageRepository diseaseStageRepository;
    @InjectMocks
    private DiseaseStageInformationImpl diseaseStageInformation;

    @Test
    void getDiseaseStageById_shouldThrowResourceNotFoundException_whenInvalidIdIsGiven() {
        when(this.diseaseStageRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> this.diseaseStageInformation.getDiseaseStageById(445L));

        assertEquals("Id not found.", exception.getMessage());
        verify(this.diseaseStageRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void getDiseaseStageById_shouldReturnDiseaseStage_whenValidIdIsGiven() {
        DiseaseStage diseaseStage = mock(DiseaseStage.class);
        when(this.diseaseStageRepository.findById(anyLong())).thenReturn(Optional.of(diseaseStage));

        DiseaseStage result = this.diseaseStageInformation.getDiseaseStageById(445L);

        assertNotNull(result);
        verify(this.diseaseStageRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void getDiseaseStageByDiseaseLevel_shouldThrowResourceNotFoundException_whenInvalidDiseaseLevel() {
        when(this.diseaseStageRepository.findByDiseaseLevelAndIsActiveTrue(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> this.diseaseStageInformation.getDiseaseStageByDiseaseLevel(4));

        assertEquals("Disease level not found.", exception.getMessage());
        verify(this.diseaseStageRepository, times(1)).findByDiseaseLevelAndIsActiveTrue(any(Integer.class));
    }

    @Test
    void getDiseaseStageByDiseaseLevel_shouldReturnDiseaseStage_whenValidDiseaseLevel() {
        DiseaseStage diseaseStage = mock(DiseaseStage.class);
        when(this.diseaseStageRepository.findByDiseaseLevelAndIsActiveTrue(anyInt())).thenReturn(Optional.of(diseaseStage));

        DiseaseStage result = this.diseaseStageInformation.getDiseaseStageByDiseaseLevel(1);

        assertNotNull(result);
        verify(this.diseaseStageRepository, times(1)).findByDiseaseLevelAndIsActiveTrue(any(Integer.class));
    }

}