package org.coviam.quora.notificationmicroservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NotificationDTO {

    @Builder.Default
    private String title = "Quora";

    private String message;
    private List<String> uidList;

    @Builder.Default
    private String platform = "quora";

    @Builder.Default
    private String jsonData = "";
}
