package org.coviam.quora.notificationmicroservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class LevelUpDTO {

    private String userId;
    private List<String> followerUserId;
    private String level;
    private String userName;
}
