package patientrecord.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import audit.AuditEvent;

@Service
public class AuditService {

	@Autowired
	KafkaTemplate<String, AuditEvent> kafkaTemplate;

	public String postAuditEvent() {

		String eventId = UUID.randomUUID().toString();

		AuditEvent auditEvent = new AuditEvent(eventId, "user-manas", "dateandtime-20240617", "service-patientrecord",
				"event-create record");
		
		
		CompletableFuture<SendResult<String, AuditEvent>> future = kafkaTemplate
				.send("audit-created-event", eventId, auditEvent).completable();

		future.whenComplete((result, exception) -> {
			if (exception != null) {
				System.out.println("exception " + exception.getMessage());
			} else {
				System.out.println("Message sent sucessfully to message broker- Compelte");
			}
		});
		
		System.out.println("Audit event sent compelted -Mans");

		return "success";

	}

}
