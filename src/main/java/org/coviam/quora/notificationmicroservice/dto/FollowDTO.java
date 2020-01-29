package org.coviam.quora.notificationmicroservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class FollowDTO {

    String followerUserId;
    String followedUserId;
    String followerName;
    String followedName;
    String followedIdType;
    List<String> moderatorId;
    Boolean isApproved;
}
