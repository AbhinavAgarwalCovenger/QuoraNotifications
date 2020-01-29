package org.coviam.quora.notificationmicroservice.service;

import org.coviam.quora.notificationmicroservice.dto.*;

public interface NotificationService {

    void newQues(AskerResponseDTO askerResponseDTO);
    void newAns(AnswerResponseDTO answerResponseDTO);
    void questionApproved(AskerResponseDTO askerResponseDTO);
    void questionRejected(String name);
    void answerApproved(AnswerResponseDTO answerResponseDTO);
    void answerRejected(String name);
    void newreaction(ReactionDTO reactionDTO);
}
