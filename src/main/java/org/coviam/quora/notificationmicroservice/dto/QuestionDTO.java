package org.coviam.quora.notificationmicroservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuestionDTO {

    String askerId;
    String askerUserName;
    String taggedProfileId;
    String taggedProfileType;
    String taggedProfileName;
    String category;
    Boolean isApproved;
}
