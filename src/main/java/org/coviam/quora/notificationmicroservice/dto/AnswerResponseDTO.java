package org.coviam.quora.notificationmicroservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AnswerResponseDTO {

    private List<String> askerFollowerList;
    private List<String> categoryFollowerList;
    private List<String> tagFollowerList;
    private List<String> moderatorList;
    private List<String> answerFollowerList;
}
