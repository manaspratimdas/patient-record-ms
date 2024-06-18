package patientrecord;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import patientrecord.customexception.RecordNotFoundException;
import patientrecord.repository.PatientRecordRepository;
import patientrecord.service.PatientRecordService;

@SpringBootTest
public class PostPatientRecordServiceTest {
	
	
	@MockBean
    private PatientRecordRepository patientRecordRepository;

    @Autowired
    private PatientRecordService patientRecordService;

    @Test
    public void testDeleteRecordSuccess() {
        when(patientRecordRepository.existsById(anyLong())).thenReturn(true);

        patientRecordService.deleteRecord(1L);

        verify(patientRecordRepository, times(1)).existsById(eq(1L));
        verify(patientRecordRepository, times(1)).deleteById(eq(1L));
    }
    
    @Test
    public void testDeleteRecordFailure() {
        when(patientRecordRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(RecordNotFoundException.class, () -> {
            patientRecordService.deleteRecord(1L);
        });

        verify(patientRecordRepository, times(1)).existsById(eq(1L));
        verify(patientRecordRepository, times(0)).deleteById(anyLong());
    }

}
