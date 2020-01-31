package org.coviam.quora.notificationmicroservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.coviam.quora.notificationmicroservice.dto.*;

public interface NotificationService {

    void newQues(AskerResponseDTO askerResponseDTO, QuestionDTO questionDTO) throws JsonProcessingException;
    void newAns(AnswerResponseDTO answerResponseDTO, AnswerDTO answerDTO) throws JsonProcessingException;
    void questionApproved(AskerResponseDTO askerResponseDTO, QuestionDTO questionDTO) throws JsonProcessingException;
    void questionRejected(QuestionDTO questionDTO) throws JsonProcessingException;
    void answerApproved(AnswerResponseDTO answerResponseDTO, AnswerDTO answerDTO) throws JsonProcessingException;
    void answerRejected(AnswerDTO answerDTO) throws JsonProcessingException;
    void newReaction(ReactionDTO reactionDTO) throws JsonProcessingException;
    void levelUp(LevelUpDTO levelUpDTO) throws JsonProcessingException;
    void followRequested(FollowDTO followDTO) throws JsonProcessingException;
    void followRequestAccepted(FollowDTO followDTO) throws JsonProcessingException;
    void threadClosed(AnswerDTO answerDTO) throws JsonProcessingException;
    void newComment(CommentDTO commentDTO, String id, String type) throws JsonProcessingException;
}
