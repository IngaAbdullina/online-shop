package com.itmo.online.shop.service.impl;

import com.itmo.online.shop.exception.ErrorCode;
import com.itmo.online.shop.service.HttpStatusService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class HttpStatusServiceImpl implements HttpStatusService {

  private static final HttpStatus defaultHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
  private static final ErrorCode defaultErrorCodeStatus = ErrorCode.INTERNAL_ERROR;

  private static final List<ErrorCode> unauthorized = Collections.singletonList(
      ErrorCode.AUTH_ERROR);
  private static final List<ErrorCode> badRequest = Arrays.asList(ErrorCode.VALIDATION_ERROR, ErrorCode.BAD_REQUEST);
  private static final List<ErrorCode> forbidden = Collections.singletonList(ErrorCode.FORBIDDEN);
  private static final List<ErrorCode> notFound = Collections.singletonList(ErrorCode.NOT_FOUND);
  private static final List<ErrorCode> internalError = Collections.singletonList(
      ErrorCode.INTERNAL_ERROR);

  @Override
  public HttpStatus errorCode2HttpStatus(ErrorCode errorCode) {
    if (unauthorized.contains(errorCode)) {
      return HttpStatus.UNAUTHORIZED;
    }

    if (badRequest.contains(errorCode)) {
      return HttpStatus.BAD_REQUEST;
    }

    if (forbidden.contains(errorCode)) {
      return HttpStatus.FORBIDDEN;
    }

    if (notFound.contains(errorCode)) {
      return HttpStatus.NOT_FOUND;
    }

    if (internalError.contains(errorCode)) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    return defaultHttpStatus;
  }

  @Override
  public ErrorCode httpStatus2ErrorCode(HttpStatus status) {
    if (status.is4xxClientError()) {

      if (status.equals(HttpStatus.BAD_REQUEST)) {
        return ErrorCode.BAD_REQUEST;
      }

      if (status.equals(HttpStatus.UNAUTHORIZED)) {
        return ErrorCode.AUTH_ERROR;
      }

      if (status.equals(HttpStatus.FORBIDDEN)) {
        return ErrorCode.FORBIDDEN;
      }

      if (status.equals(HttpStatus.NOT_FOUND)) {
        return ErrorCode.NOT_FOUND;
      }

      return ErrorCode.BAD_REQUEST;
    }

    return defaultErrorCodeStatus;
  }
}
