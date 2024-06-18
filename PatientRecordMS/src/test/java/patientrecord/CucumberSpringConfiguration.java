package patientrecord;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class CucumberSpringConfiguration {

    @Before
    public void setup_cucumber_spring_context(){
        // Setup for scenario
    }

    @After
    public void teardown_cucumber_spring_context(){
        // Teardown for scenario
    }
}
