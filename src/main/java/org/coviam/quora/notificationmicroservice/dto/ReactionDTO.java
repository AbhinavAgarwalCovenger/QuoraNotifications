package org.coviam.quora.notificationmicroservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReactionDTO {

    String reactedUserName;
    String reactedUserId;
    String reactionType;
    String category;
    String onWhomReactedId;
    String postType;
}
