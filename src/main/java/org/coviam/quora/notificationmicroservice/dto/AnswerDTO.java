package org.coviam.quora.notificationmicroservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AnswerDTO {

    String questionAskerId;
    String questionAskerName;
    String taggedProfileId;
    String taggedProfileType;
    String taggedProfileName;
    String category;
    String answerUserId;
    String answerUserName;
    Boolean isApproved;

}
