package org.coviam.quora.notificationmicroservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NotificationDTO {

    String title;
    String message;
    List<String> userId;
}
