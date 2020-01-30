package org.coviam.quora.notificationmicroservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class FinalNotifyDTO {

    List<NotificationDTO> notificationDTOList;
    String channel;
    String category;
    String action;
    String userId;
    String tag;
}
