package patientrecord.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import patientrecord.entity.PatientRecord;
import patientrecord.service.AuditService;
import patientrecord.service.PatientRecordService;

@RestController
public class PatientRecordController {

	@Value("${my.property}")
	private String propertyName;

	private static final Logger logger = LogManager.getLogger(PatientRecordController.class);

	@Autowired
	private PatientRecordService patientRecordService;
	
	@Autowired
	private AuditService auditService;

	@GetMapping("/getPatientRecord/{patientId}")
	public PatientRecord getPatientRecord(@PathVariable String patientId) {
		logger.info("Fetching patient record for ID {}", patientId);
		auditService.postAuditEvent(OPERATION.GETPATIENTRECORDBYID.toString());
		return patientRecordService.fetchRecord(patientId);
	}

	@GetMapping("/getPatientRecords")
	public List<PatientRecord> getPatientRecords() {
		logger.info("Fetching all patient record");
		auditService.postAuditEvent(OPERATION.GETALLPATIENTRECORDS.toString());
		return patientRecordService.fetchRecords();
	}

	@PostMapping("/createPatientRecord")
	public ResponseEntity<String> createPatientRecord(@RequestBody PatientRecord newRecord) {
		logger.info("createPatientRecord{}", newRecord);
		auditService.postAuditEvent(OPERATION.CREATEPATIENTRECORD.toString());
		patientRecordService.createRecord(newRecord);
		return ResponseEntity.ok().body(newRecord.toString());
	}

	@PostMapping("/updatePatientRecord/{id}")
	public PatientRecord updatePatientRecord(@PathVariable Long id, @RequestBody PatientRecord updatedRecord) {
		auditService.postAuditEvent(OPERATION.UPDATEPATIENTRECORD.toString());
		return patientRecordService.updateRecord(id, updatedRecord);
	}

	@DeleteMapping("/deletePatientRecord/{id}")
	public String deletePatientRecord(@PathVariable Long id) {
		auditService.postAuditEvent(OPERATION.DELETEPATIENTRECORD.toString());
		patientRecordService.deleteRecord(id);
		return "Record deleted successfully";
	}

	

}


enum OPERATION {
	  GETPATIENTRECORDBYID,
	  GETALLPATIENTRECORDS,
	  CREATEPATIENTRECORD,
	  UPDATEPATIENTRECORD,
	  DELETEPATIENTRECORD
	 
	}

