package org.coviam.quora.notificationmicroservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.coviam.quora.notificationmicroservice.dto.*;
import org.coviam.quora.notificationmicroservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/notify")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    //todo : no auto wiring and class variable is a disaster !!
    // one method should not change the value of this variable and expect the others to have it is not right
    //RestTemplate restTemplate;

    //todo : reading the list of users to notify and sending notifications list to kafka should be async
    //try making this as kafka consumer, instead of rest call.. depends on how the other service includes your method during the save
//    @GetMapping("/question")
    @KafkaListener(topics = "newQues", groupId = "group-id")
    public void newQuestion(String questionDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        QuestionDTO question = new QuestionDTO();
        question = objectMapper.readValue(questionDTO,QuestionDTO.class);

        final String uri="";
        RestTemplate restTemplate=new RestTemplate();
        AskerResponseDTO askerResponseDTO=restTemplate.getForObject(uri,AskerResponseDTO.class,question);
        notificationService.newQues(askerResponseDTO, question);

    }

    @KafkaListener(topics = "quesApproval", groupId = "group-id")
//    @GetMapping("/questionApproval")
    public void questionApproval(String questionDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        QuestionDTO question = new QuestionDTO();
        question = objectMapper.readValue(questionDTO,QuestionDTO.class);

        if(question.getIsApproved()) {
            final String uri = "";
            RestTemplate restTemplate = new RestTemplate();
            AskerResponseDTO askerResponseDTO = restTemplate.getForObject(uri, AskerResponseDTO.class, question);
            notificationService.questionApproved(askerResponseDTO,question);
        }
        else {
            notificationService.questionRejected(question);
        }
    }

    @KafkaListener(topics = "newAns", groupId = "group-id")
//    @GetMapping("/answer")
    public void newAnswer(String answerDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        AnswerDTO answer = new AnswerDTO();
        answer= objectMapper.readValue(answerDTO,AnswerDTO.class);

        final String uri = "";
        RestTemplate restTemplate = new RestTemplate();
        AnswerResponseDTO answerResponseDTO = restTemplate.getForObject(uri,AnswerResponseDTO.class,answer);
        notificationService.newAns(answerResponseDTO, answer);
    }

    @KafkaListener(topics = "ansApproval", groupId = "group-id")
//    @GetMapping("/answerApproval")
    public void answerApproval(String answerDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        AnswerDTO answer = new AnswerDTO();
        answer= objectMapper.readValue(answerDTO,AnswerDTO.class);
        
        if(answer.getIsApproved()) {
            final String uri = "";
            RestTemplate restTemplate = new RestTemplate();
            AnswerResponseDTO answerResponseDTO = restTemplate.getForObject(uri, AnswerResponseDTO.class, answer);
            notificationService.answerApproved(answerResponseDTO, answer);
        }
        else {
            notificationService.answerRejected(answer);
        }
    }

//    @PostMapping("/comment")
    @KafkaListener(topics = "comment", groupId = "group-id")
    public void newComment(String commentDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CommentDTO comment = new CommentDTO();
        comment = objectMapper.readValue(commentDTO,CommentDTO.class);

        String parentId = comment.getParentId();
        String userId;
        String type;
        if(parentId.startsWith("C_")){
            final String uri="";
            RestTemplate restTemplate=new RestTemplate();
            ResponseDTO<String> responseDTO=restTemplate.getForObject(uri,ResponseDTO.class,parentId);
            userId=responseDTO.getData();
            type="comment";
        }
        else if (parentId.startsWith("Q_")){
            final String uri="";
            RestTemplate restTemplate=new RestTemplate();
            userId = restTemplate.getForObject(uri,String.class,parentId);
            type="question";
        }
        else {
            final String uri="";
            RestTemplate restTemplate=new RestTemplate();
            userId = restTemplate.getForObject(uri,String.class,parentId);
            type="answer";
        }

        notificationService.newComment(comment,userId,type);
    }

    @KafkaListener(topics = "reaction", groupId = "group-id")
//    @GetMapping("/reaction")
    public void getReaction(String reactionDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ReactionDTO reaction = new ReactionDTO();
        reaction = objectMapper.readValue(reactionDTO,ReactionDTO.class);

        notificationService.newReaction(reaction);

    }

//    @GetMapping("/levelUp")
    @KafkaListener(topics = "levelUp", groupId = "group-id")
    public void leveledUp(String levelUpDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        LevelUpDTO level = new LevelUpDTO();
        level = objectMapper.readValue(levelUpDTO,LevelUpDTO.class);

        notificationService.levelUp(level);

    }

//    @GetMapping("/followRequest")
    @KafkaListener(topics = "followRequest", groupId = "group-id")
    public void followRequest(String followDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        FollowDTO follow = new FollowDTO();
        follow=objectMapper.readValue(followDTO,FollowDTO.class);

        notificationService.followRequested(follow);
    }

//    @GetMapping("/followApproval")
    @KafkaListener(topics = "followApproval", groupId = "group-id")
    public void followApproval(String followDTO) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        FollowDTO follow = new FollowDTO();
        follow=objectMapper.readValue(followDTO,FollowDTO.class);

        if (follow.getIsApproved()){
            notificationService.followRequestAccepted(follow);
        }

    }

//    @GetMapping("/threadClosed")
    @KafkaListener(topics = "threadClosed", groupId = "group-id")
    public void threadClosed(String answerDTO) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        AnswerDTO answer = new AnswerDTO();
        answer = objectMapper.readValue(answerDTO,AnswerDTO.class);

        notificationService.threadClosed(answer);
    }
}
