package patientrecord.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import patientrecord.entity.PatientRecord;
import shared.events.PatientRecordEvent;

@Service
public class PostPatientRecordService {

	@Autowired
	KafkaTemplate<String, PatientRecordEvent> kafkaTemplate;

	public String postRecord(PatientRecord record) {

		String eventId = UUID.randomUUID().toString();

		PatientRecordEvent patientRecordEvent = new PatientRecordEvent(eventId, record.getPatientId(),
				record.getHealthRecords(), record.getTreatmentHistories(), record.getDoctor());

		System.out.println("patientRecordEvent   " + patientRecordEvent);

		// kafkaTemplate.send("patient-record-upsert-event",eventId,patientRecordEvent);
		
		

		CompletableFuture<SendResult<String, PatientRecordEvent>> future = kafkaTemplate
				.send("patient-record-upsert-event", eventId, patientRecordEvent).completable();

		future.whenComplete((result, exception) -> {
			if (exception != null) {
				System.out.println("exception " + exception.getMessage());
			} else {
				System.out.println("Message sent sucessfully to message broker- Compelte");
			}
		});
		
		System.out.println("Processing compelted -Mans");

		return "success";

	}

}
