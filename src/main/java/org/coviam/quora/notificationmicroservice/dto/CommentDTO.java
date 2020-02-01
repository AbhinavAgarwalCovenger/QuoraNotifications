package org.coviam.quora.notificationmicroservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDTO {

    private String userId;
    private String commentId;
    private String parentId;
    private String comment;
    private int level;
    private int likes;
    private int dislikes;
    private String questionOrAnswerUserId;

}
