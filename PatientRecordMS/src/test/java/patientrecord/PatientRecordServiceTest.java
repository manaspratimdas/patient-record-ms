package patientrecord;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import patientrecord.customexception.RecordNotFoundException;
import patientrecord.entity.PatientRecord;
import patientrecord.repository.PatientRecordRepository;
import patientrecord.service.PatientRecordService;
import patientrecord.service.PostPatientRecordService;

@SpringBootTest
public class PatientRecordServiceTest {

	@Autowired
	private PatientRecordService patientRecordService;

	@MockBean
	private PatientRecordRepository patientRecordRepository;

	@MockBean
	private PostPatientRecordService postPatientRecordService;

	@Autowired
	private CacheManager cacheManager;

	@Test
	public void testFetchRecordSuccess() {
		PatientRecord record = new PatientRecord();
		// Set properties of the record
		when(patientRecordRepository.findByPatientId(anyString())).thenReturn(record);

		PatientRecord fetchedRecord = patientRecordService.fetchRecord("1");

		assertEquals(record, fetchedRecord);
		verify(patientRecordRepository, times(1)).findByPatientId(eq("1"));

		Cache cache = cacheManager.getCache("patientRecord");
		assertNotNull(cache);
		assertSame(record, cache.get("1").get());

		// verify that the record is fetched from cache in the second call
		patientRecordService.fetchRecord("1");
		verify(patientRecordRepository, times(1)).findByPatientId(eq("1"));
	}

	@Test
    public void testFetchRecordFailureWithFallback() {
        when(patientRecordRepository.findByPatientId(anyString())).thenReturn(null);

        PatientRecord fetchedRecord = patientRecordService.fetchRecord("1");

        // Check that the returned record is the fallback record
        assertEquals("down", fetchedRecord.getPatientId());
      //  assertEquals(0L, fetchedRecord.getId());
        assertEquals("down", fetchedRecord.getHealthRecords());
        assertEquals("down", fetchedRecord.getTreatmentHistories());

        verify(patientRecordRepository, times(1)).findByPatientId(eq("1"));
    }

	@Test
	public void testFetchRecordsSuccess() {

		PatientRecord record1 = new PatientRecord();
		PatientRecord record2 = new PatientRecord();
		// Set properties of the records
		List<PatientRecord> records = Arrays.asList(record1, record2);
		Mockito.when(patientRecordRepository.findAll()).thenReturn(records);

		List<PatientRecord> fetchedRecords = patientRecordService.fetchRecords();

		assertNotNull(fetchedRecords);
		assertEquals(records.size(), fetchedRecords.size());
		Mockito.verify(patientRecordRepository, Mockito.times(1)).findAll();
	}

	@Test
	public void testFetchRecordsFailure() {
		// Cause the findAll method to throw an exception
		Mockito.when(patientRecordRepository.findAll()).thenThrow(new RuntimeException());

		// We expect a RuntimeException to be thrown when fetchRecords is called
		assertThrows(RuntimeException.class, () -> patientRecordService.fetchRecords());

		// Verify that findAll was called once
		Mockito.verify(patientRecordRepository, Mockito.times(1)).findAll();
	}

	@Test
	public void testCreateRecord() {
		PatientRecord newRecord = new PatientRecord();
		// Set properties of the record
		when(patientRecordRepository.save(any(PatientRecord.class))).thenReturn(newRecord);

		PatientRecord createdRecord = patientRecordService.createRecord(newRecord);

		assertEquals(newRecord, createdRecord);
		verify(patientRecordRepository, times(1)).save(eq(newRecord));
		verify(postPatientRecordService, times(1)).postRecord(eq(newRecord));
	}

	@Test
	public void testFetchRecordsFailureWithFallback() {
		
		when(patientRecordRepository.findAll()).thenThrow(new RuntimeException());

	    assertThrows(RuntimeException.class, () -> {
	        patientRecordService.fetchRecords();
	    });

	    verify(patientRecordRepository, times(1)).findAll();
	}

	@Test
	public void testUpdateRecordSuccess() {
		PatientRecord record = new PatientRecord();
		// Set properties of the record
		when(patientRecordRepository.existsById(anyLong())).thenReturn(true);
		when(patientRecordRepository.save(any(PatientRecord.class))).thenReturn(record);
		when(patientRecordRepository.findByPatientId(anyString())).thenReturn(record);

		PatientRecord updatedRecord = patientRecordService.updateRecord(1L, record);

		assertNotNull(updatedRecord);
		verify(patientRecordRepository, times(1)).existsById(eq(1L));
		verify(patientRecordRepository, times(1)).save(eq(record));
	}

	@Test
    public void testUpdateRecordFailure() {
        when(patientRecordRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(RecordNotFoundException.class, () -> {
            patientRecordService.updateRecord(1L, new PatientRecord());
        });

        verify(patientRecordRepository, times(1)).existsById(eq(1L));
    }

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