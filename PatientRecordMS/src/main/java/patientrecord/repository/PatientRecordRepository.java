package patientrecord.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import patientrecord.entity.PatientRecord;

public interface PatientRecordRepository extends JpaRepository<PatientRecord, Long>{

	PatientRecord findByPatientId(String patientId);
}
