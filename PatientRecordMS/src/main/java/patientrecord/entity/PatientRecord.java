package patientrecord.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "patient_record")
public class PatientRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "patient_id")
	private String patientId;

	@Column(name = "health_records")
	private String healthRecords;

	@Column(name = "treatment_histories")
	private String treatmentHistories;
	
	@Column(name = "doctor")
	private String doctor;
	
	

	public PatientRecord() {
	
		
	}

	public PatientRecord(Long id, String patientId, String healthRecords, String treatmentHistories, String doctor) {
	
		this.id = id;
		this.patientId = patientId;
		this.healthRecords = healthRecords;
		this.treatmentHistories = treatmentHistories;
		this.doctor = doctor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getHealthRecords() {
		return healthRecords;
	}

	public void setHealthRecords(String healthRecords) {
		this.healthRecords = healthRecords;
	}

	public String getTreatmentHistories() {
		return treatmentHistories;
	}

	public void setTreatmentHistories(String treatmentHistories) {
		this.treatmentHistories = treatmentHistories;
	}
	
	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}


	@Override
	public String toString() {
		return "PatientRecord [id=" + id + ", patientId=" + patientId + ", healthRecords=" + healthRecords
				+ ", treatmentHistories=" + treatmentHistories + ", doctor=" + doctor + "]";
	}

	
	

}
