package org.coviam.quora.notificationmicroservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class FollowDTO {

    private String followerUserId;
    private String followedUserId;
    private String followerName;
    private String followedName;
    private String followedIdType;
    private List<String> moderatorId;
    private Boolean isApproved;
}
