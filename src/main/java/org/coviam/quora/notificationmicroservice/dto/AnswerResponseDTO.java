package org.coviam.quora.notificationmicroservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AnswerResponseDTO {
    List<String> askerFollowerList;
    List<String> categoryFollowerList;
    List<String> tagFollowerList;
    List<String> moderatorList;
    List<String> answerFollowerList;
}
