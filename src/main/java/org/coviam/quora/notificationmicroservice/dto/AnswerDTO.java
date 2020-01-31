package org.coviam.quora.notificationmicroservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AnswerDTO {

    private String questionAskerId;
    private String questionAskerName;
    private String taggedProfileId;
    private String taggedProfileType;
    private String taggedProfileName;
    private String category;
    private String answerUserId;
    private String answerUserName;
    private Boolean isApproved;

}
