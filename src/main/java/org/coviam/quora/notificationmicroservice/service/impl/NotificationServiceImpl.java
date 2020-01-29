package org.coviam.quora.notificationmicroservice.service.impl;

import org.coviam.quora.notificationmicroservice.dto.AnswerResponseDTO;
import org.coviam.quora.notificationmicroservice.dto.AskerResponseDTO;
import org.coviam.quora.notificationmicroservice.dto.ReactionDTO;
import org.coviam.quora.notificationmicroservice.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void newQues(AskerResponseDTO askerResponseDTO) {

    }

    @Override
    public void newAns(AnswerResponseDTO answerResponseDTO) {

    }

    @Override
    public void questionApproved(AskerResponseDTO askerResponseDTO) {

    }

    @Override
    public void questionRejected(String name) {

    }

    @Override
    public void answerApproved(AnswerResponseDTO answerResponseDTO) {

    }

    @Override
    public void answerRejected(String name) {

    }

    @Override
    public void newreaction(ReactionDTO reactionDTO) {

    }
}
