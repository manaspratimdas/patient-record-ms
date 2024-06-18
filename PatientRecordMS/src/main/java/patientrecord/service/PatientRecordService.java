package patientrecord.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import patientrecord.customexception.RecordNotFoundException;
import patientrecord.entity.PatientRecord;
import patientrecord.repository.PatientRecordRepository;

@Service
public class PatientRecordService {

	@Autowired
	private PatientRecordRepository patientRecordRepository;
	
	@Autowired
	PostPatientRecordService postPatientRecordService;

	@Cacheable("patientRecord")
	@CircuitBreaker(name = "fetchRecord", fallbackMethod = "fallbackForFetchRecord")
	public PatientRecord fetchRecord(String patientId) {

		// Try block is added to illustrate caching
		try {
			Thread.sleep(120);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		PatientRecord record = patientRecordRepository.findByPatientId(patientId);
		
		// Try block is added to illustrate caching
		System.out.println("fetching from DB");
		if (record == null) {
			throw new RecordNotFoundException("No patient record found with ID: " + patientId);
		}
		
		return record;

	}
	
	
	public PatientRecord fallbackForFetchRecord(String patientId, RecordNotFoundException ex) {
		PatientRecord patientRecord=new PatientRecord();
		patientRecord.setPatientId("down");
		patientRecord.setId(0L);
		patientRecord.setHealthRecords("down");
		patientRecord.setTreatmentHistories("down");
		return patientRecord;
        
    }

	
	
	public PatientRecord createRecord(PatientRecord newRecord) {
		System.out.println("in createRecord");
		patientRecordRepository.save(newRecord);
		postPatientRecordService.postRecord(newRecord);
		return newRecord;
	}

	
	
	public PatientRecord updateRecord(Long id, PatientRecord updatedRecord) {
		if (!patientRecordRepository.existsById(id)) {
			throw new RecordNotFoundException("No record exists for given id");
		}
		updatedRecord.setId(id);
		patientRecordRepository.save(updatedRecord);
		PatientRecord record = patientRecordRepository.findByPatientId(Long.toString(id));
		postPatientRecordService.postRecord(record);
		
		return record;
	}

	public void deleteRecord(Long id) {
		if (!patientRecordRepository.existsById(id)) {
			throw new RecordNotFoundException("No record exists for given id");
		}
		patientRecordRepository.deleteById(id);
	}

	public List<PatientRecord> fetchRecords() {

		return patientRecordRepository.findAll();
	}
}
