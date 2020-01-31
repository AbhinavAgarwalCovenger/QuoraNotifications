package org.coviam.quora.notificationmicroservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReactionDTO {

    private String reactedUserName;
    private String reactedUserId;
    private String reactionType;
    private String onWhomReactedId;
    private String postType;
}
