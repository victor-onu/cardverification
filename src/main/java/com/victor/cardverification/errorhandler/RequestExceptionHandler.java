package com.victor.cardverification.errorhandler;

import com.victor.cardverification.apiresponse.ApiResponse;
import com.victor.cardverification.exception.CustomException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        return buildResponseEntity(new ApiResponse<>(HttpStatus.BAD_REQUEST, error, ex));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ApiResponse<Object> apiError = new ApiResponse<>(HttpStatus.BAD_REQUEST);
        apiError.setError("Validation error");
        apiError.setDebugMessage("Some field(s) are empty or filled with wrong arguments");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiResponse<Object> apiError = new ApiResponse<>(HttpStatus.BAD_REQUEST);
        apiError.setMessage(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

   @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException ce) {
        ApiResponse<Object> ar = new ApiResponse<>(ce.getStatus());
        ar.setError(ce.getMessage());
        return buildResponseEntity(ar);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        ApiResponse<Object> apiError = new ApiResponse<>(HttpStatus.BAD_REQUEST);
        apiError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException ex,
                                                                      WebRequest request) {
        ApiResponse<Object> apiError = new ApiResponse<>(HttpStatus.BAD_REQUEST);
        apiError.setMessage(String.format("The details of the card number could not be found"));
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    protected ResponseEntity<Object> handleIndexOutOfBoundsException(HttpClientErrorException ex,
                                                                    WebRequest request) {
        ApiResponse<Object> apiError = new ApiResponse<>(HttpStatus.BAD_REQUEST);
        apiError.setMessage(String.format("The limit set in your url is higher than the number of hits using different car numbers(size of hit count)"));
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }



    private ResponseEntity<Object> buildResponseEntity(ApiResponse<Object> apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
