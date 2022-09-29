package com.r2s.findInternship.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ExceptionHandling {
	public final static Logger logger = LoggerFactory.getLogger("exception");
	// Handler Not found Entity
	@ExceptionHandler(value = ResourceNotFound.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponeMessage handlerNotFound(ResourceNotFound ex, HttpServletRequest request) {
		logger.warn(ex.toString());
		return new ResponeMessage(404, ex.toString(),request.getServletPath());
	}

	// Exception with Service
//	@ExceptionHandler(value = ServiceUnavailableErrorException.class)
//	@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
//	public ResponseEntity<?> handlerServiceUnavailableError(ServiceUnavailableErrorException ex,HttpServletRequest request) {
//		HashMap<String, String> response = new HashMap<>();
//		response.put("status", "503");
//		response.put("message", ex.getMessage());
//		response.put("path", request.getServletPath());
//		return ResponseEntity.badRequest().body(response);			
//	}
	
	// Exception with SQL
		@ExceptionHandler(value = InternalServerErrorException.class)
		@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
		public ResponseEntity<?> handlerInternalServerError(InternalServerErrorException ex,HttpServletRequest request) {
			if (ex.getErrors() == null)
				return ResponseEntity.badRequest().body(new ResponeMessage(500, ex.getMessage(),request.getServletPath()));
			else
			{
				Map<String, String> maps = ex.getErrors();
				maps.put("path", request.getServletPath());
				return ResponseEntity.badRequest().body(maps);
			}
				
		}

	@ExceptionHandler(value = ExceptionCustom.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponeMessage handlerExceptionCustom(ExceptionCustom ex,HttpServletRequest request) {
		logger.warn(ex.toString());
		return new ResponeMessage(401, ex.getMessage(),request.getServletPath());
	}

	// PASSWORD INVALID
	@ExceptionHandler(value = InvalidOldPasswordException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponeMessage handlerInvalidOldPassword(InvalidOldPasswordException ex,HttpServletRequest request) {
		logger.warn(ex.toString());
		return new ResponeMessage(400, ex.getMessage(),request.getServletPath());
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Map<String, String> handlerValidation(MethodArgumentNotValidException ex,HttpServletRequest request) {
		Map<String, String> maps = new HashMap<String, String>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String field = ((FieldError) error).getField();
			String msg = error.getDefaultMessage();
			maps.put(field, msg);
		});
		maps.put("path", request.getServletPath());
		return maps;
	}

	// Access Denied
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public ResponseEntity<?> handleAccessDenied(AccessDeniedException e) {
		Map<String, String> response = new HashMap<>();
		response.put("message", "Forbidden");
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	}
	
	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponeMessage handlerNotFound(Exception ex,HttpServletRequest request) {
		logger.error("Please check!");
		return new ResponeMessage(500, ex.getMessage(),request.getServletPath());
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<?> handleNoHandlerFound(NoHandlerFoundException e, WebRequest request) {
		HashMap<String, String> response = new HashMap<>();
		response.put("status", "404");
		response.put("message", e.getLocalizedMessage());
		response.put("url", request.getContextPath());
		
		return ResponseEntity.badRequest().body(response);
	}
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	@ResponseStatus(value = HttpStatus.PAYLOAD_TOO_LARGE)
	public ResponseEntity<?> handleMultipartException(MaxUploadSizeExceededException e) {

	    Map<String, String> result = new HashMap<>();
	    result.put("message", "Dung lượng quá lớn, vui lòng chọn dung lượng tối đa 50KB");
	    return  ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
	            .body(result);

	}
	
	
}
