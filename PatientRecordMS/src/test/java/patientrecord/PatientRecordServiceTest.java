package patientrecord;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import patientrecord.entity.PatientRecord;
import patientrecord.repository.PatientRecordRepository;
import patientrecord.service.PatientRecordService;

@SpringBootTest
public class PatientRecordServiceTest {

    @Autowired
    private PatientRecordService patientRecordService;

    @MockBean
    private PatientRecordRepository patientRecordRepository;

    @Test
    public void whenValidPatientId_thenRecordShouldBeFound() {
        String patientId = "john123";
        PatientRecord john = new PatientRecord();
        john.setPatientId(patientId);
        when(patientRecordRepository.findByPatientId(patientId)).thenReturn(john);

        PatientRecord found = patientRecordService.fetchRecord(patientId);

        assertEquals(patientId, found.getPatientId());
    }

    @Test
    public void whenInvalidPatientId_thenRecordShouldNotBeFound() {
        String invalidId = "invalid123";
        when(patientRecordRepository.findByPatientId(invalidId)).thenReturn(null);

        PatientRecord found = patientRecordService.fetchRecord(invalidId);

        assertNull(found);
    }
}