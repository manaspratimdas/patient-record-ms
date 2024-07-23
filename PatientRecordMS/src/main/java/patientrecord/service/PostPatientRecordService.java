package patientrecord.service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import patientrecord.entity.PatientRecord;
import shared.events.PatientRecordEvent;

@Service
public class PostPatientRecordService {

	@Autowired
	KafkaTemplate<String, PatientRecordEvent> kafkaTemplate;

	@Value("${pr.message-broker-name}")
	private String messageBrokerName;

	private static final Logger logger = LogManager.getLogger(PostPatientRecordService.class);

	public String postRecord(PatientRecord record) {

		String eventId = UUID.randomUUID().toString();

		PatientRecordEvent patientRecordEvent = new PatientRecordEvent(eventId, record.getPatientId(),
				record.getHealthRecords(), record.getTreatmentHistories(), record.getDoctor());

		logger.info("patientRecordEvent {}  ", patientRecordEvent);

		CompletableFuture<SendResult<String, PatientRecordEvent>> future = kafkaTemplate
				.send(messageBrokerName, eventId, patientRecordEvent).completable();

		future.whenComplete((result, exception) -> {
			if (exception != null) {
				logger.info("Error sending message to broker " + exception.getMessage());
			} else {
				logger.info("Message process and sent to broker");
			}
		});

		//logger.info("Patient record sent to message queue {} at {}", messageBrokerName, LocalDateTime.now());
		return "success";

	}

}
