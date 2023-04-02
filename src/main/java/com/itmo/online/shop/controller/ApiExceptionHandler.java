package com.itmo.online.shop.controller;

import static java.util.stream.Collectors.joining;

import com.itmo.online.shop.db.entity.User;
import com.itmo.online.shop.exception.ApiException;
import com.itmo.online.shop.exception.ErrorCode;
import com.itmo.online.shop.response.ErrorResult;
import com.itmo.online.shop.response.Response;
import com.itmo.online.shop.service.HttpStatusService;
import com.itmo.online.shop.service.ShoppingCartService;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  private final HttpStatusService httpStatusService;
  private final ShoppingCartService shoppingCartService;

  public ApiExceptionHandler(HttpStatusService httpStatusService,
      ShoppingCartService shoppingCartService) {
    this.httpStatusService = httpStatusService;
    this.shoppingCartService = shoppingCartService;
  }

  @InitBinder
  public void initBinder(WebDataBinder dataBinder) {
    StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
    dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
  }

  @ModelAttribute
  public void populateModel(Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
      User user = (User) auth.getPrincipal();
      if (user != null) {
        model.addAttribute("shoppingCartItemNumber", shoppingCartService.getItemsNumber(user));
      }
    } else {
      model.addAttribute("shoppingCartItemNumber", 0);
    }
  }

  @ExceptionHandler(value = Exception.class)
  public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
    if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
      throw e;
    }
    ModelAndView mav = new ModelAndView();
    mav.addObject("timestamp", new Date(System.currentTimeMillis()));
    mav.addObject("path", req.getRequestURL());
    mav.addObject("message", e.getMessage());
    mav.setViewName("error");
    return mav;
  }

  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    return handleExceptionInternal(ex, null, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    return handleExceptionInternal(ex, null, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
      HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    return handleExceptionInternal(ex, null, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    writeLogWithRequestErrorInfo(ex, request);
    String description = String.format(
        "Переменная %s является обязательной и не может быть пустой", ex.getVariableName());
    return ResponseEntity.badRequest().body(
        ErrorResult.builder()
            .code(ErrorCode.BAD_REQUEST)
            .description(description)
            .build());
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    writeLogWithRequestErrorInfo(ex, request);
    String description = String.format("Параметр %s является обязательным и не может быть пустым",
        ex.getParameterName());
    return ResponseEntity.badRequest().body(
        ErrorResult.builder()
            .code(ErrorCode.BAD_REQUEST)
            .description(description)
            .build());
  }

  @Override
  protected ResponseEntity<Object> handleServletRequestBindingException(
      ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    return handleExceptionInternal(ex, null, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    return handleExceptionInternal(ex, null, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    return handleExceptionInternal(ex, null, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    return handleExceptionInternal(ex, null, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    return handleExceptionInternal(ex, null, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    BindingResult bindingResult = ex.getBindingResult();
    String errors = bindingResult
        .getFieldErrors()
        .stream()
        .map(e -> e.getField() + ' ' + e.getDefaultMessage())
        .collect(joining("," + System.lineSeparator() + " "));

    writeLogWithRequestErrorInfo(ex, request);
    return ResponseEntity.badRequest().body(
        ErrorResult.builder()
            .code(ErrorCode.BAD_REQUEST)
            .description(errors)
            .build());
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestPart(
      MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    return handleExceptionInternal(ex, null, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    return handleExceptionInternal(ex, null, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    return handleExceptionInternal(ex, null, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleAsyncRequestTimeoutException(
      AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatus status,
      WebRequest webRequest) {
    return handleExceptionInternal(ex, null, headers, status, webRequest);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    writeLogWithRequestErrorInfo(ex, request);
    return ResponseEntity.status(status).body(
        ErrorResult.builder()
            .code(httpStatusService.httpStatus2ErrorCode(status))
            .description(ex.getMessage())
            .build());
  }

  @ExceptionHandler
  public ResponseEntity<Response> handleApiException(ApiException ex) {
    ErrorResult errorResult = ex.getErrorResult();
    ErrorCode errorCode = errorResult.getCode();
    log.error(errorResult.getDescription());
    return ResponseEntity.status(httpStatusService.errorCode2HttpStatus(errorCode)).body(
        ex.getErrorResult());
  }

  @ExceptionHandler
  public ResponseEntity<Response> handleConstraintViolationException(
      ConstraintViolationException ex, WebRequest request) {
    String errors = ex.getConstraintViolations()
        .stream()
        .map(v -> v.getPropertyPath().toString() + ' ' + v.getMessage())
        .collect(joining("," + System.lineSeparator() + " "));

    writeLogWithRequestErrorInfo(ex, request);
    return ResponseEntity.badRequest().body(
        ErrorResult.builder()
            .code(ErrorCode.BAD_REQUEST)
            .description(errors)
            .build());
  }

  private void writeLogWithRequestErrorInfo(Exception ex, WebRequest request) {
    HttpServletRequest httpServletRequest = ((ServletWebRequest) request).getRequest();
    String url = httpServletRequest.getRequestURL().toString();
    String method = httpServletRequest.getMethod();
    String message = ex.getMessage();

    log.error(String.format(
        "Error while requesting %s %s. Cause: %s.", method, url, message));
  }
}
