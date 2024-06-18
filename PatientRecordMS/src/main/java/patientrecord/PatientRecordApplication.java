package patientrecord;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableCaching
public class PatientRecordApplication {

  public static void main(String[] args) {
    SpringApplication.run(PatientRecordApplication.class, args);
  }
}
