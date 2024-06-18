package patientrecord.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	
	private final String DOWN="down";
	private final Long ZERO=0L;
	
	private static final Logger logger = LogManager.getLogger(PatientRecordService.class);

	@Cacheable("patientRecord")
	@CircuitBreaker(name = "fetchRecord", fallbackMethod = "fallbackForFetchRecord")
	public PatientRecord fetchRecord(String patientId) {

		logger.info(" fetching record for Record{}",patientId );
		// Try block is added to illustrate caching
		try {
			Thread.sleep(120);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		PatientRecord record = patientRecordRepository.findByPatientId(patientId);
		
		// Try block is added to illustrate caching
		
		if (record == null) {
			throw new RecordNotFoundException("No patient record found with ID: " + patientId);
		}
		
		return record;

	}
	
	
	public PatientRecord fallbackForFetchRecord(String patientId, RecordNotFoundException ex) {
		logger.info(" fetching record from fallback methods for Record{}",patientId );
		PatientRecord patientRecord=new PatientRecord();
		patientRecord.setPatientId(DOWN);
		patientRecord.setId(ZERO);
		patientRecord.setHealthRecords(DOWN);
		patientRecord.setTreatmentHistories(DOWN);
		return patientRecord;
        
    }

	
	
	public PatientRecord createRecord(PatientRecord newRecord) {
		logger.info("Creating for {} ",newRecord );
		System.out.println("in createRecord");
		patientRecordRepository.save(newRecord);
		postPatientRecordService.postRecord(newRecord);
		return newRecord;
	}

	
	
	public PatientRecord updateRecord(Long id, PatientRecord updatedRecord) {
		logger.info("Updating record for {} ",id );
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
		logger.info("Deleting record for {} ",id );
		if (!patientRecordRepository.existsById(id)) {
			throw new RecordNotFoundException("No record exists for given id");
		}
		patientRecordRepository.deleteById(id);
	}

	

	public List<PatientRecord> fetchRecords() {
		logger.info(" Fethcing all records");
		return patientRecordRepository.findAll();
	}
	

}
