package patientrecord;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.concurrent.ListenableFuture;

import audit.AuditEvent;
import patientrecord.service.AuditService;

@SpringBootTest
public class AuditServiceTest {
	
	
	@MockBean
    private KafkaTemplate<String, AuditEvent> kafkaTemplate;

    @Autowired
    private AuditService auditService;
    
    
    
    @Test
    public void testPostAuditEvent() {
        String userName = "testUser";
        String event = "testEvent";

        Authentication auth = new UsernamePasswordAuthenticationToken(userName, null);
        SecurityContextHolder.getContext().setAuthentication(auth);  

        // create a mock ListenableFuture
        ListenableFuture<SendResult<String, AuditEvent>> future = Mockito.mock(ListenableFuture.class);
        // create a mock CompletableFuture
        CompletableFuture<SendResult<String, AuditEvent>> completableFuture = new CompletableFuture<>();

        // when kafkaTemplate.send is called, return the mock future
        when(kafkaTemplate.send(anyString(), anyString(), any(AuditEvent.class))).thenReturn(future);
        // when completable() is called on the mock future, return the mock CompletableFuture
        when(future.completable()).thenReturn(completableFuture);

        String result = auditService.postAuditEvent(event);

        assertEquals("success", result);

        ArgumentCaptor<AuditEvent> argument = ArgumentCaptor.forClass(AuditEvent.class);
        verify(kafkaTemplate, times(1)).send(anyString(), anyString(), argument.capture());

        AuditEvent auditEvent = argument.getValue();
        assertEquals(event, auditEvent.getEvent());
    }


}
