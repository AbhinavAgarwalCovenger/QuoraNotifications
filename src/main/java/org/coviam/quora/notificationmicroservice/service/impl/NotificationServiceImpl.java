package org.coviam.quora.notificationmicroservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.coviam.quora.notificationmicroservice.dto.*;
import org.coviam.quora.notificationmicroservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    KafkaTemplate<String,String > kafkaTemplate;

    final String TOPIC = "notif1";

    @Override
    public void newQues(AskerResponseDTO askerResponseDTO, QuestionDTO questionDTO) throws JsonProcessingException {
        List<NotificationDTO> list = new ArrayList<>();

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

        if(askerResponseDTO.getModeratorList().size()!=0){

            userId.addAll(askerResponseDTO.getModeratorList());
            notificationDTO.setMessage(name+" has requested to post a question in your channel");
            notificationDTO.setUidList(userId);

            list.add(notificationDTO);
        }
        else {

            if (taggedId!=null){
                userId.add(taggedId);
                notificationDTO.setMessage(name+" has asked you a question");
                notificationDTO.setUidList(userId);
                list.add(notificationDTO);

            }

            if (askerResponseDTO.getTagFollowerList().size()!=0){

                taggedFollowers.addAll(askerResponseDTO.getTagFollowerList());
                taggedFollowerNotificationDTO.setMessage(name+"has asked a question to "+taggedName);
                taggedFollowerNotificationDTO.setUidList(taggedFollowers);

                list.add(taggedFollowerNotificationDTO);
            }

            if (askerResponseDTO.getAskerFollowerList().size()!=0) {
                followersId.addAll(askerResponseDTO.getAskerFollowerList());
                followerNotificationDTO.setMessage(name + " has asked a question");
                followerNotificationDTO.setUidList(followersId);
                list.add(followerNotificationDTO);
            }

            if (askerResponseDTO.getCategoryFollowerList().size()!=0) {
                categoryFollowers.addAll(askerResponseDTO.getCategoryFollowerList());
                categoryNotificationDTO.setUidList(categoryFollowers);
                categoryNotificationDTO.setMessage(name + " asked a question in " + category);


                list.add(categoryNotificationDTO);
            }

        }

        for(NotificationDTO notifyList: list){
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println(notifyList.getUidList());
            kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(notifyList));
        }
    }

    @Override
    public void newAns(AnswerResponseDTO answerResponseDTO, AnswerDTO answerDTO) throws JsonProcessingException {

        List<NotificationDTO> list = new ArrayList<>();

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

        if(answerResponseDTO.getModeratorList().size()!=0){
            userId.addAll(answerResponseDTO.getModeratorList());
            notificationDTO.setMessage(answerUserName+" has requested to post a answer in your channel");
            notificationDTO.setUidList(userId);

            list.add(notificationDTO);

        }
        else {

            if (answerResponseDTO.getTagFollowerList().size()!=0){

                if (taggedId!=null) {
                    if (!(answerDTO.getAnswerUserId().equalsIgnoreCase(answerDTO.getTaggedProfileId()))) {
                        userId.add(taggedId);
                        notificationDTO.setMessage(answerUserName + " has answered a question you were asked");
                        notificationDTO.setUidList(userId);

                        list.add(notificationDTO);
                    }
                }

                taggedFollowers.addAll(answerResponseDTO.getTagFollowerList());
                taggedFollowerNotificationDTO.setMessage(answerUserName+"has answered a question asked to "+taggedName);
                taggedFollowerNotificationDTO.setUidList(taggedFollowers);
                list.add(taggedFollowerNotificationDTO);
            }

            if (answerResponseDTO.getAskerFollowerList().size()!=0) {
                askerFollowresId.addAll(answerResponseDTO.getAskerFollowerList());
                askerFollowerNotificationDTO.setMessage(answerUserName + " has answered a question asked by " + askerName);
                askerFollowerNotificationDTO.setUidList(askerFollowresId);
                list.add(askerFollowerNotificationDTO);

            }

            if (answerResponseDTO.getCategoryFollowerList().size()!=0) {
                categoryFollowers.addAll(answerResponseDTO.getCategoryFollowerList());
                categoryNotificationDTO.setUidList(categoryFollowers);
                categoryNotificationDTO.setMessage(answerUserName + " has answered a question in " + category);
                list.add(categoryNotificationDTO);

            }

            if (answerResponseDTO.getAnswerFollowerList().size()!=0) {
                answerFollowersId.addAll(answerResponseDTO.getAnswerFollowerList());
                answerFollowerNotificationDTO.setUidList(answerFollowersId);
                answerFollowerNotificationDTO.setMessage(answerUserName + " has answered a question");

                list.add(answerFollowerNotificationDTO);
            }
        }

        for(NotificationDTO notifyList: list){
            ObjectMapper objectMapper = new ObjectMapper();

            kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(notifyList));
        }


    }

    @Override
    public void questionApproved(AskerResponseDTO askerResponseDTO, QuestionDTO questionDTO) throws JsonProcessingException {

        List<NotificationDTO> list = new ArrayList<>();

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
        askerNotification.setUidList(askerId);
        askerNotification.setMessage("Your question has been approved by "+taggedName);
        list.add(askerNotification);

        if (askerResponseDTO.getAskerFollowerList().size()!=0) {
            askerFollowers.addAll(askerResponseDTO.getAskerFollowerList());
            followerNotification.setUidList(askerFollowers);
            followerNotification.setMessage(askerName + " has asked a question to " + taggedName);
            list.add(followerNotification);

        }

        if (askerResponseDTO.getCategoryFollowerList().size()!=0) {
            categoryFollowers.addAll(askerResponseDTO.getCategoryFollowerList());
            categoryFollowerNotification.setUidList(categoryFollowers);
            categoryFollowerNotification.setMessage(askerName + " has asked a question in " + category);
            list.add(categoryFollowerNotification);

        }

        if (askerResponseDTO.getTagFollowerList().size()!=0) {
            taggedProfileFollowers.addAll(askerResponseDTO.getTagFollowerList());
            taggedFollowerNotification.setUidList(taggedProfileFollowers);
            taggedFollowerNotification.setMessage(askerName + " has asked a question in " + taggedName + " channel");
            list.add(taggedFollowerNotification);

        }


        for(NotificationDTO notifyList: list){
            ObjectMapper objectMapper = new ObjectMapper();

            kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(notifyList));
        }

    }

    @Override
    public void questionRejected(QuestionDTO questionDTO) throws JsonProcessingException {

        List<NotificationDTO> list = new ArrayList<>();

        String name = questionDTO.getTaggedProfileName();
        ArrayList<String> userId = new ArrayList<>();

        NotificationDTO notificationDTO = new NotificationDTO();
        userId.add(questionDTO.getAskerId());

        notificationDTO.setUidList(userId);
        notificationDTO.setMessage(name+" has rejected to post your question in their channel");

        list.add(notificationDTO);

        for(NotificationDTO notifyList: list){
            ObjectMapper objectMapper = new ObjectMapper();

            kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(notifyList));
        }

    }

    @Override
    public void answerApproved(AnswerResponseDTO answerResponseDTO, AnswerDTO answerDTO) throws JsonProcessingException {

        List<NotificationDTO> list = new ArrayList<>();

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
        answerNotification.setUidList(answerId);
        answerNotification.setMessage("Your answer has been approved to be posted by "+taggedProfile);
        list.add(answerNotification);


        askerId.add(answerDTO.getQuestionAskerId());
        askerNotification.setUidList(askerId);
        askerNotification.setMessage("Your question has been answered by "+answerUserName);
        list.add(askerNotification);

        if (answerResponseDTO.getAskerFollowerList().size()!=0) {
            askerFollowers.addAll(answerResponseDTO.getAskerFollowerList());
            askerFollowerNotification.setUidList(askerFollowers);
            askerFollowerNotification.setMessage(answerUserName + " has answered a question asked by " + askerName);
            list.add(askerFollowerNotification);
        }

        if (answerResponseDTO.getAnswerFollowerList().size()!=0) {
            answerFollowers.addAll(answerResponseDTO.getAnswerFollowerList());
            answerFollowerNotification.setUidList(answerFollowers);
            answerFollowerNotification.setMessage(answerUserName + " has answered a question");
            list.add(answerFollowerNotification);

        }

        if (answerResponseDTO.getCategoryFollowerList().size()!=0) {
            categoryFollowers.addAll(answerResponseDTO.getCategoryFollowerList());
            categoryFollowerNotification.setUidList(categoryFollowers);
            categoryFollowerNotification.setMessage(answerUserName + " has answered a question asked in " + category);
            list.add(categoryFollowerNotification);

        }

        if (answerResponseDTO.getTagFollowerList().size()!=0) {
            taggedProfileFollowers.addAll(answerResponseDTO.getTagFollowerList());
            taggedProfileFollowersNotification.setUidList(taggedProfileFollowers);
            taggedProfileFollowersNotification.setMessage(answerUserName + " answered a question asked in " + taggedProfile + " channel");
            list.add(taggedProfileFollowersNotification);

        }

        for(NotificationDTO notifyList: list){
            ObjectMapper objectMapper = new ObjectMapper();

            kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(notifyList));
        }

    }

    @Override
    public void answerRejected(AnswerDTO answerDTO) throws JsonProcessingException {
        List<NotificationDTO> list = new ArrayList<>();

        String name = answerDTO.getTaggedProfileName();
        ArrayList<String> userId = new ArrayList<>();

        NotificationDTO notificationDTO = new NotificationDTO();
        userId.add(answerDTO.getAnswerUserId());

        notificationDTO.setUidList(userId);
        notificationDTO.setMessage(name+" has rejected your answer");

        list.add(notificationDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(list));

    }

    @Override
    public void newReaction(ReactionDTO reactionDTO) throws JsonProcessingException {

        List<NotificationDTO> list = new ArrayList<>();

        NotificationDTO notificationDTO = new NotificationDTO();
        ArrayList<String> userId = new ArrayList<>();

        String reactions = reactionDTO.getReactionType();
        String post = reactionDTO.getPostType();
        String name = reactionDTO.getReactedUserName();

        userId.add(reactionDTO.getOnWhomReactedId());

        notificationDTO.setUidList(userId);

        if (reactions.equalsIgnoreCase("like"))
            notificationDTO.setMessage(name+"liked your "+post);
        else if (reactions.equalsIgnoreCase("dislike"))
            notificationDTO.setMessage(name+"disliked your "+post);
        else
            notificationDTO.setMessage(name+" reacted on your "+post);

        list.add(notificationDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(list));

    }

    @Override
    public void levelUp(LevelUpDTO levelUpDTO) throws JsonProcessingException {

        List<NotificationDTO> list = new ArrayList<>();

        String name = levelUpDTO.getUserName();
        String level = levelUpDTO.getLevel();

        //user Himself
        NotificationDTO selfNotificationDTO = new NotificationDTO();
        ArrayList<String> userId = new ArrayList<>();

        userId.add(levelUpDTO.getUserId());

        selfNotificationDTO.setMessage("You levelled up to "+ level +" level");
        selfNotificationDTO.setUidList(userId);
        selfNotificationDTO.setPlatform("quora");
        selfNotificationDTO.setTitle("");

        System.out.println(selfNotificationDTO.getUidList());

        list.add(selfNotificationDTO);


        //userFollowers
        if (levelUpDTO.getFollowerUserId().size()!=0) {
            NotificationDTO followerNotificationDTO = new NotificationDTO();
            ArrayList<String> userFollowerId = new ArrayList<>();

            userFollowerId.addAll(levelUpDTO.getFollowerUserId());

            followerNotificationDTO.setMessage(name + " levelled up to " + level + " level");
            followerNotificationDTO.setUidList(userFollowerId);
            followerNotificationDTO.setTitle("");
            followerNotificationDTO.setPlatform("quora");

            list.add(followerNotificationDTO);
        }

        for(NotificationDTO notifyList: list){
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println(notifyList.getMessage());
            kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(notifyList));
        }

    }

    @Override
    public void followRequested(FollowDTO followDTO) throws JsonProcessingException {

        List<NotificationDTO> list = new ArrayList<>();

        String name = followDTO.getFollowerName();
        String type = followDTO.getFollowedIdType();

        NotificationDTO notificationDTO = new NotificationDTO();
        ArrayList<String> userId = new ArrayList<>();

        if (type.equalsIgnoreCase("public")) {
            userId.add(followDTO.getFollowedUserId());
            notificationDTO.setUidList(userId);
            notificationDTO.setMessage(name+" started following you.");
            list.add(notificationDTO);
        }

        else {
            userId.addAll(followDTO.getModeratorId());
            notificationDTO.setUidList(userId);
            notificationDTO.setMessage(name+" has requested to follow you");
            list.add(notificationDTO);
        }


        ObjectMapper objectMapper = new ObjectMapper();
        kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(list));

    }

    @Override
    public void followRequestAccepted(FollowDTO followDTO) throws JsonProcessingException {

        List<NotificationDTO> list = new ArrayList<>();

        String name = followDTO.getFollowedName();

        NotificationDTO notificationDTO = new NotificationDTO();
        ArrayList<String> userId = new ArrayList<>();
        userId.add(followDTO.getFollowerUserId());

        notificationDTO.setUidList(userId);
        notificationDTO.setMessage(name+" accepted your follow request");

        list.add(notificationDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(list));
    }

    @Override
    public void threadClosed(AnswerDTO answerDTO) throws JsonProcessingException {

        List<NotificationDTO> list = new ArrayList<>();

        String name = answerDTO.getQuestionAskerName();

        NotificationDTO notificationDTO = new NotificationDTO();
        ArrayList<String> userId = new ArrayList<>();

        userId.add(answerDTO.getAnswerUserId());

        notificationDTO.setUidList(userId);
        notificationDTO.setMessage(name+" chose your answer as the correct answer");

        list.add(notificationDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(list));
    }

    @Override
    public void newComment(CommentDTO commentDTO, String id, String type) throws JsonProcessingException {

        List<NotificationDTO> list = new ArrayList<>();

        NotificationDTO notificationDTO = new NotificationDTO();
        ArrayList<String> userId = new ArrayList<>();

        userId.add(id);
        notificationDTO.setUidList(userId);
        notificationDTO.setMessage("Someone commented on your "+type);

        list.add(notificationDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(list));

    }
}
