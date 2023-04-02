package com.itmo.online.shop.response;

import com.itmo.online.shop.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResult implements Response {

  private ErrorCode code;
  private String description;
}
