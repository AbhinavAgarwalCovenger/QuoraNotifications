package org.coviam.quora.notificationmicroservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

@Getter
@Setter
public class NotificationDTO {

    @Builder.Default
    private String title = " ";

    private String message;
    private List<String> uidList;

    @Builder.Default
    private String platform = "quora";
}
