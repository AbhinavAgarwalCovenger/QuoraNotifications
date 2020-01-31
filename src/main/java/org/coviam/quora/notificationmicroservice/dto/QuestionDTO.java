package org.coviam.quora.notificationmicroservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuestionDTO {

    private String askerId;
    private String askerUserName;
    private String taggedProfileId;
    private String taggedProfileType;
    private String taggedProfileName;
    private String category;
    private Boolean isApproved;
}
