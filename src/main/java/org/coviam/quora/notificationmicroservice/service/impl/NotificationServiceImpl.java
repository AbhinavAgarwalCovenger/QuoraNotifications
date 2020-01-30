package org.coviam.quora.notificationmicroservice.service.impl;

import org.coviam.quora.notificationmicroservice.dto.*;
import org.coviam.quora.notificationmicroservice.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void newQues(AskerResponseDTO askerResponseDTO, QuestionDTO questionDTO) {
        String name = questionDTO.getAskerUserName();
        String category = questionDTO.getCategory();
        String taggedId = questionDTO.getTaggedProfileId();
        String taggedName = questionDTO.getTaggedProfileName();

        NotificationDTO notificationDTO = new NotificationDTO();
        NotificationDTO taggedFollowerNotificationDTO = new NotificationDTO();
        NotificationDTO followerNotificationDTO = new NotificationDTO();
        NotificationDTO categoryNotificationDTO = new NotificationDTO();
        ArrayList<String> userId = new ArrayList<>();
        ArrayList<String> followersId = new ArrayList<>();
        ArrayList<String> categoryFollowers = new ArrayList<>();
        ArrayList<String> taggedFollowers = new ArrayList<>();

        if(askerResponseDTO.getModeratorList()!=null){
            userId.addAll(askerResponseDTO.getModeratorList());
            notificationDTO.setMessage(name+" has requested to post a question in your channel");
            notificationDTO.setUserId(userId);
        }
        else {

            if (askerResponseDTO.getTagFollowerList()!=null){
                userId.add(taggedId);
                notificationDTO.setMessage(name+" has asked you a question");
                notificationDTO.setUserId(userId);

                taggedFollowers.addAll(askerResponseDTO.getTagFollowerList());
                taggedFollowerNotificationDTO.setMessage(name+"has asked a question to "+taggedName);
                taggedFollowerNotificationDTO.setUserId(taggedFollowers);
            }

            followersId.addAll(askerResponseDTO.getAskerFollowerList());
            followerNotificationDTO.setMessage(name+" has asked a question");
            followerNotificationDTO.setUserId(followersId);

            categoryFollowers.addAll(askerResponseDTO.getCategoryFollowerList());
            categoryNotificationDTO.setUserId(categoryFollowers);
            categoryNotificationDTO.setMessage(name+" asked a question in "+category);

        }
    }

    @Override
    public void newAns(AnswerResponseDTO answerResponseDTO, AnswerDTO answerDTO) {
        String askerName = answerDTO.getQuestionAskerName();
        String answerUserName = answerDTO.getAnswerUserName();
        String category = answerDTO.getCategory();
        String taggedId = answerDTO.getTaggedProfileId();
        String taggedName = answerDTO.getTaggedProfileName();

        NotificationDTO notificationDTO = new NotificationDTO();
        NotificationDTO taggedFollowerNotificationDTO = new NotificationDTO();
        NotificationDTO askerFollowerNotificationDTO = new NotificationDTO();
        NotificationDTO categoryNotificationDTO = new NotificationDTO();
        NotificationDTO answerFollowerNotificationDTO = new NotificationDTO();
        ArrayList<String> userId = new ArrayList<>();
        ArrayList<String> answerFollowersId = new ArrayList<>();
        ArrayList<String> askerFollowresId = new ArrayList<>();
        ArrayList<String> categoryFollowers = new ArrayList<>();
        ArrayList<String> taggedFollowers = new ArrayList<>();

        if(answerResponseDTO.getModeratorList()!=null){
            userId.addAll(answerResponseDTO.getModeratorList());
            notificationDTO.setMessage(answerUserName+" has requested to post a answer in your channel");
            notificationDTO.setUserId(userId);
        }
        else {

            if (answerResponseDTO.getTagFollowerList()!=null){

                if (!(answerDTO.getAnswerUserId().equalsIgnoreCase(answerDTO.getTaggedProfileId()))) {
                    userId.add(taggedId);
                    notificationDTO.setMessage(answerUserName + " has answered a question you were asked");
                    notificationDTO.setUserId(userId);
                }

                taggedFollowers.addAll(answerResponseDTO.getTagFollowerList());
                taggedFollowerNotificationDTO.setMessage(answerUserName+"has answered a question asked to "+taggedName);
                taggedFollowerNotificationDTO.setUserId(taggedFollowers);
            }

            askerFollowresId.addAll(answerResponseDTO.getAskerFollowerList());
            askerFollowerNotificationDTO.setMessage(answerUserName+" has answered a question asked by "+askerName);
            askerFollowerNotificationDTO.setUserId(askerFollowresId);

            categoryFollowers.addAll(answerResponseDTO.getCategoryFollowerList());
            categoryNotificationDTO.setUserId(categoryFollowers);
            categoryNotificationDTO.setMessage(answerUserName+" has answered a question in "+category);

            answerFollowersId.addAll(answerResponseDTO.getAnswerFollowerList());
            answerFollowerNotificationDTO.setUserId(answerFollowersId);
            answerFollowerNotificationDTO.setMessage(answerUserName+" has answered a question");

        }
    }

    @Override
    public void questionApproved(AskerResponseDTO askerResponseDTO, QuestionDTO questionDTO) {
        String askerName = questionDTO.getAskerUserName();
        String taggedName = questionDTO.getTaggedProfileName();
        String category = questionDTO.getCategory();

        NotificationDTO askerNotification = new NotificationDTO();
        NotificationDTO followerNotification = new NotificationDTO();
        NotificationDTO categoryFollowerNotification = new NotificationDTO();
        NotificationDTO taggedFollowerNotification = new NotificationDTO();

        ArrayList<String> askerId = new ArrayList<>();
        ArrayList<String> askerFollowers = new ArrayList<>();
        ArrayList<String> categoryFollowers = new ArrayList<>();
        ArrayList<String> taggedProfileFollowers = new ArrayList<>();

        askerId.add(questionDTO.getAskerId());
        askerNotification.setUserId(askerId);
        askerNotification.setMessage("Your question has been approved by "+taggedName);

        askerFollowers.addAll(askerResponseDTO.getAskerFollowerList());
        followerNotification.setUserId(askerFollowers);
        followerNotification.setMessage(askerName+" has asked a question to "+taggedName);

        categoryFollowers.addAll(askerResponseDTO.getCategoryFollowerList());
        categoryFollowerNotification.setUserId(categoryFollowers);
        categoryFollowerNotification.setMessage(askerName+" has asked a question in "+category);

        taggedProfileFollowers.addAll(askerResponseDTO.getTagFollowerList());
        taggedFollowerNotification.setUserId(taggedProfileFollowers);
        taggedFollowerNotification.setMessage(askerName+" has asked a question in "+taggedName+" channel");
    }

    @Override
    public void questionRejected(QuestionDTO questionDTO) {
        String name = questionDTO.getTaggedProfileName();
        ArrayList<String> userId = new ArrayList<>();

        NotificationDTO notificationDTO = new NotificationDTO();
        userId.add(questionDTO.getAskerId());

        notificationDTO.setUserId(userId);
        notificationDTO.setMessage(name+" has rejected to post your question in their channel");
    }

    @Override
    public void answerApproved(AnswerResponseDTO answerResponseDTO, AnswerDTO answerDTO) {
        String askerName = answerDTO.getQuestionAskerName();
        String answerUserName = answerDTO.getAnswerUserName();
        String taggedProfile = answerDTO.getTaggedProfileName();
        String category = answerDTO.getCategory();

        NotificationDTO askerNotification = new NotificationDTO();
        NotificationDTO answerNotification = new NotificationDTO();
        NotificationDTO askerFollowerNotification = new NotificationDTO();
        NotificationDTO answerFollowerNotification = new NotificationDTO();
        NotificationDTO categoryFollowerNotification = new NotificationDTO();
        NotificationDTO taggedProfileFollowersNotification = new NotificationDTO();
        ArrayList<String> askerId = new ArrayList<>();
        ArrayList<String> answerId = new ArrayList<>();
        ArrayList<String> askerFollowers = new ArrayList<>();
        ArrayList<String> answerFollowers = new ArrayList<>();
        ArrayList<String> categoryFollowers = new ArrayList<>();
        ArrayList<String> taggedProfileFollowers = new ArrayList<>();

        answerId.add(answerDTO.getAnswerUserId());
        answerNotification.setUserId(answerId);
        answerNotification.setMessage("Your answer has been approved to be posted by "+taggedProfile);

        askerId.add(answerDTO.getQuestionAskerId());
        askerNotification.setUserId(askerId);
        askerNotification.setMessage("Your question has been answered by "+answerUserName);

        askerFollowers.addAll(answerResponseDTO.getAskerFollowerList());
        askerFollowerNotification.setUserId(askerFollowers);
        askerFollowerNotification.setMessage(answerUserName+" has answered a question asked by "+askerName);

        answerFollowers.addAll(answerResponseDTO.getAnswerFollowerList());
        answerFollowerNotification.setUserId(answerFollowers);
        answerFollowerNotification.setMessage(answerUserName+" has answered a question");

        categoryFollowers.addAll(answerResponseDTO.getCategoryFollowerList());
        categoryFollowerNotification.setUserId(categoryFollowers);
        categoryFollowerNotification.setMessage(answerUserName+" has answered a question asked in "+category);

        taggedProfileFollowers.addAll(answerResponseDTO.getTagFollowerList());
        taggedProfileFollowersNotification.setUserId(taggedProfileFollowers);
        taggedProfileFollowersNotification.setMessage(answerUserName+" answered a question asked in "+taggedProfile+" channel");
    }

    @Override
    public void answerRejected(AnswerDTO answerDTO) {
        String name = answerDTO.getTaggedProfileName();
        ArrayList<String> userId = new ArrayList<>();

        NotificationDTO notificationDTO = new NotificationDTO();
        userId.add(answerDTO.getAnswerUserId());

        notificationDTO.setUserId(userId);
        notificationDTO.setMessage(name+" has rejected your answer");
    }

    @Override
    public void newReaction(ReactionDTO reactionDTO) {

        NotificationDTO notificationDTO = new NotificationDTO();
        ArrayList<String> userId = new ArrayList<>();

        String reactions = reactionDTO.getReactionType();
        String post = reactionDTO.getPostType();
        String name = reactionDTO.getReactedUserName();

        userId.add(reactionDTO.getOnWhomReactedId());

        notificationDTO.setUserId(userId);

        if (reactions.equalsIgnoreCase("like"))
            notificationDTO.setMessage(name+"liked your "+post);
        else if (reactions.equalsIgnoreCase("dislike"))
            notificationDTO.setMessage(name+"disliked your "+post);
        else
            notificationDTO.setMessage(name+" reacted on your "+post);


    }

    @Override
    public void levelUp(LevelUpDTO levelUpDTO) {

        String name = levelUpDTO.getUserName();
        String level = levelUpDTO.getLevel();

        //user Himself
        NotificationDTO selfNotificationDTO = new NotificationDTO();
        ArrayList<String> userId = new ArrayList<>();

        userId.add(levelUpDTO.getUserId());

        selfNotificationDTO.setMessage("You levelled up to "+ level +" level");
        selfNotificationDTO.setUserId(userId);

        //userFollowers
        NotificationDTO followerNotificationDTO = new NotificationDTO();
        ArrayList<String> userFollowerId = new ArrayList<>();

        userFollowerId.addAll(levelUpDTO.getFollowerUserId());

        followerNotificationDTO.setMessage(name+" levelled up to "+ level+" level");
        followerNotificationDTO.setUserId(userFollowerId);

    }

    @Override
    public void followRequested(FollowDTO followDTO) {
        String name = followDTO.getFollowerName();
        String type = followDTO.getFollowedIdType();

        NotificationDTO notificationDTO = new NotificationDTO();
        ArrayList<String> userId = new ArrayList<>();
        //ArrayList<String> moderatorId = new ArrayList<>();

        if (type.equalsIgnoreCase("public")) {
            userId.add(followDTO.getFollowedUserId());
            notificationDTO.setUserId(userId);
            notificationDTO.setMessage(name+" started following you.");
        }

        else {
            userId.addAll(followDTO.getModeratorId());
            notificationDTO.setUserId(userId);
            notificationDTO.setMessage(name+" has requested to follow you");
        }
    }

    @Override
    public void followRequestAccepted(FollowDTO followDTO) {
        String name = followDTO.getFollowedName();

        NotificationDTO notificationDTO = new NotificationDTO();
        ArrayList<String> userId = new ArrayList<>();
        userId.add(followDTO.getFollowerUserId());

        notificationDTO.setUserId(userId);
        notificationDTO.setMessage(name+" accepted your follow request");
    }

}
