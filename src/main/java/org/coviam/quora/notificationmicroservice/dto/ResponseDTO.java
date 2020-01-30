package org.coviam.quora.notificationmicroservice.dto;

import lombok.Data;

@Data
public class ResponseDTO<T> {

    private String message;
    private boolean success;
    private T data;
}
