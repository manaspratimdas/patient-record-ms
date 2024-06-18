package patientrecord.customexception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ResponseBody
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<?> handleRecordNotFoundException(RecordNotFoundException ex) {
		// Create a custom error structure, or just return the error message
	    String errorMessage = ex.getMessage();

	    // You might want to log the error as well
	    // logger.error(errorMessage, ex);

	    // Return the error message as response with a 404 status code
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		
    }

}
