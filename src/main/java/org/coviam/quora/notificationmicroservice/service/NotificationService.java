package org.coviam.quora.notificationmicroservice.service;

import org.coviam.quora.notificationmicroservice.dto.*;

public interface NotificationService {

    void newQues(AskerResponseDTO askerResponseDTO, QuestionDTO questionDTO);
    void newAns(AnswerResponseDTO answerResponseDTO, AnswerDTO answerDTO);
    void questionApproved(AskerResponseDTO askerResponseDTO, QuestionDTO questionDTO);
    void questionRejected(QuestionDTO questionDTO);
    void answerApproved(AnswerResponseDTO answerResponseDTO, AnswerDTO answerDTO);
    void answerRejected(AnswerDTO answerDTO);
    void newReaction(ReactionDTO reactionDTO);
    void levelUp(LevelUpDTO levelUpDTO);
    void followRequested(FollowDTO followDTO);
    void followRequestAccepted(FollowDTO followDTO);
}
