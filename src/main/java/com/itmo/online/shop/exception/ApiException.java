package com.itmo.online.shop.exception;

import com.itmo.online.shop.response.ErrorResult;
import lombok.Getter;

@Getter
public class ApiException extends Exception {

  private final ErrorResult errorResult;

  public ApiException(ErrorCode errorCode, String message) {
    super(message);
    errorResult = ErrorResult.builder()
        .code(errorCode)
        .description(message)
        .build();
  }
}
