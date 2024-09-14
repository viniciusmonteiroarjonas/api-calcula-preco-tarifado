package br.com.api.calculo.exception;

import br.com.api.calculo.utils.ConstantesAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class APIExceptionHandler {


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleMissingRequestBody(HttpMessageNotReadableException ex, WebRequest request) {
        log.error("API CALCULO - ERROR HttpMessageNotReadableException : {}", ConstantesAPI.MESSAGE_400_REQUISICAO_MAL_FORMDA);
        return new ResponseEntity<>(getErrorsMessage(ConstantesAPI.STATUS_CODE_BAD_REQUEST, ConstantesAPI.MESSAGE_400_REQUISICAO_MAL_FORMDA), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        log.error("API CALCULO - ERROR MethodArgumentNotValidException : {}", ex.getMessage());
        List<Object> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsValidateRequestMessage(ConstantesAPI.STATUS_CODE_BAD_REQUEST, errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequestException(IllegalArgumentException ex) {
        log.error("API CALCULO - ERROR IllegalArgumentException : {}", ex.getMessage());
        return new ResponseEntity<>(getErrorsMessage(ConstantesAPI.STATUS_CODE_BAD_REQUEST, ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Map<String, Object>> handleGeneralExceptions(Exception ex) {
        log.error("API CALCULO - ERROR Exception : {}", ex.getMessage());
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMessage(ConstantesAPI.STATUS_CODE_INTERNAL_SERVER_ERROR, ex.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private Map<String, Object> getErrorsMessage(String code, String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", code);
        errorResponse.put("message", message);
        return errorResponse;
    }

    private Map<String, Object> getErrorsValidateRequestMessage(String code, Object erros) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", code);
        errorResponse.put("message", erros);
        return errorResponse;
    }

}
