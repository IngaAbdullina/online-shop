package com.itmo.online.shop.service;

import com.itmo.online.shop.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public interface HttpStatusService {

  HttpStatus errorCode2HttpStatus(ErrorCode errorCode);

  ErrorCode httpStatus2ErrorCode(HttpStatus status);
}
