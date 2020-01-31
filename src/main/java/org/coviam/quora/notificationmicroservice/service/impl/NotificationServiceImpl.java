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
//        FinalNotifyDTO finalNotifyDTO = new FinalNotifyDTO();
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

        if(askerResponseDTO.getModeratorList()!=null){
            userId.addAll(askerResponseDTO.getModeratorList());
            notificationDTO.setMessage(name+" has requested to post a question in your channel");
            notificationDTO.setUidList(userId);

            list.add(notificationDTO);
//            finalNotifyDTO.setNotificationDTOList(list);
        }
        else {

            if (askerResponseDTO.getTagFollowerList()!=null){
                userId.add(taggedId);
                notificationDTO.setMessage(name+" has asked you a question");
                notificationDTO.setUidList(userId);

                taggedFollowers.addAll(askerResponseDTO.getTagFollowerList());
                taggedFollowerNotificationDTO.setMessage(name+"has asked a question to "+taggedName);
                taggedFollowerNotificationDTO.setUidList(taggedFollowers);

                list.add(notificationDTO);
                list.add(taggedFollowerNotificationDTO);
            }

            followersId.addAll(askerResponseDTO.getAskerFollowerList());
            followerNotificationDTO.setMessage(name+" has asked a question");
            followerNotificationDTO.setUidList(followersId);

            categoryFollowers.addAll(askerResponseDTO.getCategoryFollowerList());
            categoryNotificationDTO.setUidList(categoryFollowers);
            categoryNotificationDTO.setMessage(name+" asked a question in "+category);

            list.add(followerNotificationDTO);
            list.add(categoryNotificationDTO);

//            finalNotifyDTO.setNotificationDTOList(list);
        }

        for(NotificationDTO notifyList: list){
            ObjectMapper objectMapper = new ObjectMapper();

            kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(notifyList));
        }

//        finalNotifyDTO.setNotificationDTOList(list);
//        finalNotifyDTO.setChannel("quora");
//        finalNotifyDTO.setCategory(category);
//        finalNotifyDTO.setUidList(questionDTO.getAskerId());
//        finalNotifyDTO.setAction("askedQuestion");
    }

    @Override
    public void newAns(AnswerResponseDTO answerResponseDTO, AnswerDTO answerDTO) throws JsonProcessingException {

//        FinalNotifyDTO finalNotifyDTO = new FinalNotifyDTO();
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

        if(answerResponseDTO.getModeratorList()!=null){
            userId.addAll(answerResponseDTO.getModeratorList());
            notificationDTO.setMessage(answerUserName+" has requested to post a answer in your channel");
            notificationDTO.setUidList(userId);

            list.add(notificationDTO);
//            finalNotifyDTO.setNotificationDTOList(list);
        }
        else {

            if (answerResponseDTO.getTagFollowerList()!=null){

                if (!(answerDTO.getAnswerUserId().equalsIgnoreCase(answerDTO.getTaggedProfileId()))) {
                    userId.add(taggedId);
                    notificationDTO.setMessage(answerUserName + " has answered a question you were asked");
                    notificationDTO.setUidList(userId);

                    list.add(notificationDTO);
                }

                taggedFollowers.addAll(answerResponseDTO.getTagFollowerList());
                taggedFollowerNotificationDTO.setMessage(answerUserName+"has answered a question asked to "+taggedName);
                taggedFollowerNotificationDTO.setUidList(taggedFollowers);
                list.add(taggedFollowerNotificationDTO);
            }

            askerFollowresId.addAll(answerResponseDTO.getAskerFollowerList());
            askerFollowerNotificationDTO.setMessage(answerUserName+" has answered a question asked by "+askerName);
            askerFollowerNotificationDTO.setUidList(askerFollowresId);

            categoryFollowers.addAll(answerResponseDTO.getCategoryFollowerList());
            categoryNotificationDTO.setUidList(categoryFollowers);
            categoryNotificationDTO.setMessage(answerUserName+" has answered a question in "+category);

            answerFollowersId.addAll(answerResponseDTO.getAnswerFollowerList());
            answerFollowerNotificationDTO.setUidList(answerFollowersId);
            answerFollowerNotificationDTO.setMessage(answerUserName+" has answered a question");

            list.add(answerFollowerNotificationDTO);
            list.add(askerFollowerNotificationDTO);
            list.add(categoryNotificationDTO);

//            finalNotifyDTO.setNotificationDTOList(list);
        }

        for(NotificationDTO notifyList: list){
            ObjectMapper objectMapper = new ObjectMapper();

            kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(notifyList));
        }

//        finalNotifyDTO.setNotificationDTOList(list);
//        finalNotifyDTO.setChannel("quora");
//        finalNotifyDTO.setCategory(category);
//        finalNotifyDTO.setUidList(answerDTO.getAnswerUserId());
//        finalNotifyDTO.setAction("answeredQuestion");
    }

    @Override
    public void questionApproved(AskerResponseDTO askerResponseDTO, QuestionDTO questionDTO) throws JsonProcessingException {

//        FinalNotifyDTO finalNotifyDTO = new FinalNotifyDTO();
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

        askerFollowers.addAll(askerResponseDTO.getAskerFollowerList());
        followerNotification.setUidList(askerFollowers);
        followerNotification.setMessage(askerName+" has asked a question to "+taggedName);

        categoryFollowers.addAll(askerResponseDTO.getCategoryFollowerList());
        categoryFollowerNotification.setUidList(categoryFollowers);
        categoryFollowerNotification.setMessage(askerName+" has asked a question in "+category);

        taggedProfileFollowers.addAll(askerResponseDTO.getTagFollowerList());
        taggedFollowerNotification.setUidList(taggedProfileFollowers);
        taggedFollowerNotification.setMessage(askerName+" has asked a question in "+taggedName+" channel");

        list.add(askerNotification);
        list.add(followerNotification);
        list.add(categoryFollowerNotification);
        list.add(taggedFollowerNotification);

        for(NotificationDTO notifyList: list){
            ObjectMapper objectMapper = new ObjectMapper();

            kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(notifyList));
        }

//        finalNotifyDTO.setNotificationDTOList(list);
//        finalNotifyDTO.setChannel("quora");
//        finalNotifyDTO.setCategory(category);
//        finalNotifyDTO.setUidList(questionDTO.getTaggedProfileId());
//        finalNotifyDTO.setAction("approvedQuestion");

    }

    @Override
    public void questionRejected(QuestionDTO questionDTO) throws JsonProcessingException {
//        FinalNotifyDTO finalNotifyDTO = new FinalNotifyDTO();
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

//        finalNotifyDTO.setNotificationDTOList(list);
//        finalNotifyDTO.setChannel("quora");
//        finalNotifyDTO.setCategory(questionDTO.getCategory());
//        finalNotifyDTO.setUidList(questionDTO.getTaggedProfileId());
//        finalNotifyDTO.setAction("rejectedQuestion");

    }

    @Override
    public void answerApproved(AnswerResponseDTO answerResponseDTO, AnswerDTO answerDTO) throws JsonProcessingException {
//        FinalNotifyDTO finalNotifyDTO = new FinalNotifyDTO();
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

        askerId.add(answerDTO.getQuestionAskerId());
        askerNotification.setUidList(askerId);
        askerNotification.setMessage("Your question has been answered by "+answerUserName);

        askerFollowers.addAll(answerResponseDTO.getAskerFollowerList());
        askerFollowerNotification.setUidList(askerFollowers);
        askerFollowerNotification.setMessage(answerUserName+" has answered a question asked by "+askerName);

        answerFollowers.addAll(answerResponseDTO.getAnswerFollowerList());
        answerFollowerNotification.setUidList(answerFollowers);
        answerFollowerNotification.setMessage(answerUserName+" has answered a question");

        categoryFollowers.addAll(answerResponseDTO.getCategoryFollowerList());
        categoryFollowerNotification.setUidList(categoryFollowers);
        categoryFollowerNotification.setMessage(answerUserName+" has answered a question asked in "+category);

        taggedProfileFollowers.addAll(answerResponseDTO.getTagFollowerList());
        taggedProfileFollowersNotification.setUidList(taggedProfileFollowers);
        taggedProfileFollowersNotification.setMessage(answerUserName+" answered a question asked in "+taggedProfile+" channel");

        list.add(answerNotification);
        list.add(askerFollowerNotification);
        list.add(answerFollowerNotification);
        list.add(categoryFollowerNotification);
        list.add(taggedProfileFollowersNotification);

        for(NotificationDTO notifyList: list){
            ObjectMapper objectMapper = new ObjectMapper();

            kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(notifyList));
        }

//        finalNotifyDTO.setNotificationDTOList(list);
//        finalNotifyDTO.setChannel("quora");
//        finalNotifyDTO.setCategory(category);
//        finalNotifyDTO.setUidList(answerDTO.getTaggedProfileId());
//        finalNotifyDTO.setAction("approvedAnswer");

    }

    @Override
    public void answerRejected(AnswerDTO answerDTO) throws JsonProcessingException {
//        FinalNotifyDTO finalNotifyDTO = new FinalNotifyDTO();
        List<NotificationDTO> list = new ArrayList<>();

        String name = answerDTO.getTaggedProfileName();
        ArrayList<String> userId = new ArrayList<>();

        NotificationDTO notificationDTO = new NotificationDTO();
        userId.add(answerDTO.getAnswerUserId());

        notificationDTO.setUidList(userId);
        notificationDTO.setMessage(name+" has rejected your answer");

        list.add(notificationDTO);

        for(NotificationDTO notifyList: list){
            ObjectMapper objectMapper = new ObjectMapper();

            kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(notifyList));
        }

//        finalNotifyDTO.setNotificationDTOList(list);
//        finalNotifyDTO.setChannel("quora");
//        finalNotifyDTO.setCategory(answerDTO.getCategory());
//        finalNotifyDTO.setUidList(answerDTO.getTaggedProfileId());
//        finalNotifyDTO.setAction("rejectedAnswer");
    }

    @Override
    public void newReaction(ReactionDTO reactionDTO) throws JsonProcessingException {
//        FinalNotifyDTO finalNotifyDTO = new FinalNotifyDTO();
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

        for(NotificationDTO notifyList: list){
            ObjectMapper objectMapper = new ObjectMapper();

            kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(notifyList));
        }

//        finalNotifyDTO.setNotificationDTOList(list);
//        finalNotifyDTO.setChannel("quora");
//        finalNotifyDTO.setCategory(reactionDTO.getCategory());
//        finalNotifyDTO.setUidList(reactionDTO.getReactedUserId());
//        finalNotifyDTO.setAction("newReaction");
    }

    @Override
    public void levelUp(LevelUpDTO levelUpDTO) throws JsonProcessingException {
//        FinalNotifyDTO finalNotifyDTO = new FinalNotifyDTO();
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

        //userFollowers
        NotificationDTO followerNotificationDTO = new NotificationDTO();
        ArrayList<String> userFollowerId = new ArrayList<>();

        userFollowerId.addAll(levelUpDTO.getFollowerUserId());

        followerNotificationDTO.setMessage(name+" levelled up to "+ level+" level");
        followerNotificationDTO.setUidList(userFollowerId);
        followerNotificationDTO.setTitle("");
        followerNotificationDTO.setPlatform("quora");

        list.add(selfNotificationDTO);
        list.add(followerNotificationDTO);

        for(NotificationDTO notifyList: list){
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println(notifyList.getMessage());
            kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(notifyList));
        }

//        finalNotifyDTO.setNotificationDTOList(list);
//        finalNotifyDTO.setChannel("quora");
//        finalNotifyDTO.setCategory();
//        finalNotifyDTO.setUidList(levelUpDTO.getUidList());
//        finalNotifyDTO.setAction("levelledUp");

    }

    @Override
    public void followRequested(FollowDTO followDTO) throws JsonProcessingException {
//        FinalNotifyDTO finalNotifyDTO = new FinalNotifyDTO();
        List<NotificationDTO> list = new ArrayList<>();

        String name = followDTO.getFollowerName();
        String type = followDTO.getFollowedIdType();

        NotificationDTO notificationDTO = new NotificationDTO();
        ArrayList<String> userId = new ArrayList<>();
        //ArrayList<String> moderatorId = new ArrayList<>();

        if (type.equalsIgnoreCase("public")) {
            userId.add(followDTO.getFollowedUserId());
            notificationDTO.setUidList(userId);
            notificationDTO.setMessage(name+" started following you.");

        }

        else {
            userId.addAll(followDTO.getModeratorId());
            notificationDTO.setUidList(userId);
            notificationDTO.setMessage(name+" has requested to follow you");
        }

        list.add(notificationDTO);

        for(NotificationDTO notifyList: list){
            ObjectMapper objectMapper = new ObjectMapper();

            kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(notifyList));
        }

//        finalNotifyDTO.setNotificationDTOList(list);
//        finalNotifyDTO.setChannel("quora");
//        finalNotifyDTO.setCategory();
//        finalNotifyDTO.setUidList(followDTO.getFollowerUserId());
//        finalNotifyDTO.setAction("followRequested");

    }

    @Override
    public void followRequestAccepted(FollowDTO followDTO) throws JsonProcessingException {
//        FinalNotifyDTO finalNotifyDTO = new FinalNotifyDTO();
        List<NotificationDTO> list = new ArrayList<>();

        String name = followDTO.getFollowedName();

        NotificationDTO notificationDTO = new NotificationDTO();
        ArrayList<String> userId = new ArrayList<>();
        userId.add(followDTO.getFollowerUserId());

        notificationDTO.setUidList(userId);
        notificationDTO.setMessage(name+" accepted your follow request");

        list.add(notificationDTO);

        for(NotificationDTO notifyList: list){
            ObjectMapper objectMapper = new ObjectMapper();

            kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(notifyList));
        }

//        finalNotifyDTO.setNotificationDTOList(list);
//        finalNotifyDTO.setChannel("quora");
//        finalNotifyDTO.setCategory();
//        finalNotifyDTO.setUidList(followDTO.getFollowedUserId());
//        finalNotifyDTO.setAction("followRequestAccepted");

    }

    @Override
    public void threadClosed(AnswerDTO answerDTO) throws JsonProcessingException {
//        FinalNotifyDTO finalNotifyDTO = new FinalNotifyDTO();
        List<NotificationDTO> list = new ArrayList<>();

        String name = answerDTO.getQuestionAskerName();

        NotificationDTO notificationDTO = new NotificationDTO();
        ArrayList<String> userId = new ArrayList<>();

        userId.add(answerDTO.getAnswerUserId());

        notificationDTO.setUidList(userId);
        notificationDTO.setMessage(name+" chose your answer as the correct answer");

        list.add(notificationDTO);

        for(NotificationDTO notifyList: list){
            ObjectMapper objectMapper = new ObjectMapper();

            kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(notifyList));
        }

//        finalNotifyDTO.setNotificationDTOList(list);
//        finalNotifyDTO.setChannel("quora");
//        finalNotifyDTO.setCategory(answerDTO.getCategory());
//        finalNotifyDTO.setUidList(answerDTO.getQuestionAskerId());
//        finalNotifyDTO.setAction("threadClosed");

    }

    @Override
    public void newComment(CommentDTO commentDTO, String id, String type) throws JsonProcessingException {

//        FinalNotifyDTO finalNotifyDTO = new FinalNotifyDTO();
        List<NotificationDTO> list = new ArrayList<>();

        NotificationDTO notificationDTO = new NotificationDTO();
        ArrayList<String> userId = new ArrayList<>();

        userId.add(id);
        notificationDTO.setUidList(userId);
        notificationDTO.setMessage("Someone commented on your "+type);

        list.add(notificationDTO);

        for(NotificationDTO notifyList: list){
            ObjectMapper objectMapper = new ObjectMapper();

            kafkaTemplate.send(TOPIC,objectMapper.writeValueAsString(notifyList));
        }

//        finalNotifyDTO.setNotificationDTOList(list);
//        finalNotifyDTO.setChannel("quora");
//        finalNotifyDTO.setCategory();
//        finalNotifyDTO.setUidList(commentDTO.getUidList());
//        finalNotifyDTO.setAction("comment");
    }
}
