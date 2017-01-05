package io.doug.exception;

import io.doug.rest.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	private final static Logger LOGGER = (Logger) LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ResponseBody
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> handleAbbyBusinessException(HttpServletRequest request, BusinessException ex) {
		ApiResponse<Object> apiResponse = new ApiResponse<Object>(ex);

		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	/**
	 * This method is encountered when JSON conversion is unsuccessful.
	 * 
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		return new ResponseEntity<>("HttpMessageNotReadableException encountered. Suppressing stack trace" + ex.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {
		ApiResponse<Object> apiResponse = handleInternalException(ex);
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	//TODO: Need to fix this to not initialize the APIResponse with the actual Exception in the payload
	private ApiResponse<Object> handleInternalException(Exception ex) {

		ApiResponse<Object> apiResponse = new ApiResponse<Object>(ex);
		//ex.printStackTrace();
		String errId = String.valueOf(System.currentTimeMillis());
		String classname = apiResponse.getMessage();
		String methodname = "";
		String linenumber = "";

		if (ex.getStackTrace()[0] != null) {
			classname = ex.getStackTrace()[0].getClassName();
			methodname = ex.getStackTrace()[0].getMethodName();
			linenumber = String.valueOf(ex.getStackTrace()[0].getLineNumber());
		}
		// log the message including the id so we can track it
		LOGGER.error("environment: classname:" + classname + " methodname:" + methodname + " linenumber:"
				+ linenumber, ex);
		
		return apiResponse;
	}

}