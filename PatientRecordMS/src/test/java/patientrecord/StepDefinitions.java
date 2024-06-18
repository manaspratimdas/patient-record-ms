package patientrecord;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {


	    @Given("a patient with id {string}")
	    public void a_patient_with_id(String patientId) {
	        // Write code here to setup patient
	    }

	    @When("I retrieve the patient record with id {string}")
	    public void i_retrieve_the_patient_record_with_id(String patientId) {
	        // Write code here to retrieve patient record
	    }

	    @Then("I should get the patient record of {string}")
	    public void i_should_get_the_patient_record_of(String patientId) {
	        // Write code here to assert that you got the correct patient record
	    }

	    @When("I update the patient record with id {string}")
	    public void i_update_the_patient_record_with_id(String patientId) {
	        // Write code here to update patient record
	    }

	    @Then("the patient record with id {string} should be updated")
	    public void the_patient_record_with_id_should_be_updated(String patientId) {
	        // Write code here to assert that the patient record was updated
	    }
	}

